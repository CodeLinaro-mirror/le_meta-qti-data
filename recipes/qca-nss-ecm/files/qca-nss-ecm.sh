#!/bin/sh
#
# Copyright (c) 2014, 2019-2021 The Linux Foundation. All rights reserved.
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
#
# Permission to use, copy, modify, and/or distribute this software for any
# purpose with or without fee is hereby granted, provided that the above
# copyright notice and this permission notice appear in all copies.
#
# THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
# WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
# ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
# WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
# ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
# OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

# ECM_FRONT_END_SELECTION can be: auto(0), sfe(2), ipa(7), ipa-sfe(8), sfe-ipa(9)
get_front_end_mode() {
	# Try to get front_end from uci first
	local front_end=""
	if command -v uci >/dev/null; then
		front_end=$(uci -q get ecm.global.acceleration_engine)
	fi

	# Fallback to config file if uci is not present or returns empty
	if [ -z "$front_end" ]; then
		ECM_FRONT_END_SELECTION="ipa"
		[ -f /etc/qca-nss-ecm/config ] && . /etc/qca-nss-ecm/config
		front_end=$ECM_FRONT_END_SELECTION
	fi

	# Default value is set to 'ipa'
	[ -n "$front_end" ] || front_end="ipa"

	case $front_end in
	auto)
		echo '0'
		;;
	sfe)
		echo '2'
		;;
	ipa)
		echo '7'
		;;
	ipa-sfe)
		echo '8'
		;;
	sfe-ipa)
		echo '9'
		;;
	*)
		echo 'config_option_acceleration_engine is invalid'
	esac
}

load_sfe() {
	# SFE module is optional - skip silently if not present
	local sfe_ko="/usr/lib/modules/$(uname -r)/extra/qca-nss-sfe.ko"
	[ -e "$sfe_ko" ] && {
		[ -d /sys/module/qca_nss_sfe ] || insmod "$sfe_ko"
	}
}

unload_sfe() {
	[ -d /sys/module/qca_nss_sfe ] && rmmod qca-nss-sfe
}

load_ecm() {
	local ecm_ko="/usr/lib/modules/$(uname -r)/extra/ecm.ko"
	if [ ! -e "$ecm_ko" ]; then
		echo "qca-nss-ecm: ECM module not found at $ecm_ko, aborting"
		return 1
	fi
	[ -d /sys/module/ecm ] || {
		#ecm_dependency
		insmod "$ecm_ko" front_end_selection=$(get_front_end_mode)
	}
}

unload_ecm() {

	sysctl -w net.bridge.bridge-nf-call-ip6tables=0
	sysctl -w net.bridge.bridge-nf-call-iptables=0

	if [ -d /sys/module/ecm ]; then
		#
		# Stop ECM frontends
		#
		echo 1 > /sys/kernel/debug/ecm/front_end_ipv4_stop
		echo 1 > /sys/kernel/debug/ecm/front_end_ipv6_stop

		#
		# Defunct the connections
		#
		echo 1 > /sys/kernel/debug/ecm/ecm_db/defunct_all
		sleep 5

		rmmod ecm
		sleep 1
	fi

}

unload_ecm_sdx_pcc() {

	if [ -d /sys/module/ecm_sdx_pcc ]; then
		echo 1 > /proc/sys/net/ecm/ecm_sdx_pcc_unregister
		rmmod ecm_sdx_pcc
	fi

}

pre_start() {
	#Pre-Start Commands

	sysctl -w net.bridge.bridge-nf-call-ip6tables=1
	sysctl -w net.bridge.bridge-nf-call-iptables=1

	#Disable ipa hw offload for eth
	#[ -f  /sys/class/net/eth0/suspend_ipa_offload ] && echo 1 > /sys/class/net/eth0/suspend_ipa_offload
	#[ -f  /sys/class/net/eth1/suspend_ipa_offload ] && echo 1 > /sys/class/net/eth1/suspend_ipa_offload

	#Disable ipa offload for wifi hmt
	#sed -i 's/^gIPAConfig=.*/gIPAConfig=0/' /etc/misc/wifi/WCNSS_qcom_cfg.ini
	#sed -i 's/^gIPADescSize=.*/gIPADescSize=0/' /etc/misc/wifi/WCNSS_qcom_cfg.ini

	#Disable ipa offload for wifi wkk
	#if ! grep -q "gIPAConfig=0" /etc/misc/ipq/ini/internal/global_i.ini; then
	#	echo "gIPAConfig=0" >> /etc/misc/ipq/ini/internal/global_i.ini
	#fi
	#if ! grep -q "gIPADescSize=0" /etc/misc/ipq/ini/internal/global_i.ini; then
	#	echo "gIPADescSize=0" >> /etc/misc/ipq/ini/internal/global_i.ini
	#fi

	#Disable rx-vlan-offload to make sure NIC doesn't strip vlan tag.
	#ethtool -K eth0 rx-vlan-offload off
	#ethtool -K eth1 rx-vlan-offload off

	# Set conntrack event mode to 1 for 6.1 kernel to get the conntrack events from ECM
	echo 1 > /proc/sys/net/netfilter/nf_conntrack_events

	echo "pre_start"

}

post_start() {
	#Post-Start Commands

	# Delete original configuration before appending new
	#sed '/net.bridge.bridge-nf-call-ip6tables/d' -i /etc/sysctl.d/qca-nss-ecm.conf
	#sed '/net.bridge.bridge-nf-call-iptables/d' -i /etc/sysctl.d/qca-nss-ecm.conf

	#echo 'net.bridge.bridge-nf-call-ip6tables=1' >> /etc/sysctl.d/qca-nss-ecm.conf
	#echo 'net.bridge.bridge-nf-call-iptables=1' >> /etc/sysctl.d/qca-nss-ecm.conf

	# Disable route table events
	echo 0 > /proc/sys/net/ecm/ecm_db/ipv4_route_handle
	echo 0 > /proc/sys/net/ecm/ecm_db/ipv6_route_handle

	echo "post_start"

}

start() {

	#Pre-Start commands
	pre_start

	#Load SFE (optional - skipped if module not present)
	load_sfe

	#Load ECM
	load_ecm || { echo "qca-nss-ecm: Failed to load ECM"; exit 1; }

	#Post-Start commands
	post_start

}

pre_stop() {
	#Pre-Stop Commands

	#sed '/net.bridge.bridge-nf-call-ip6tables=1/d' -i /etc/sysctl.d/qca-nss-ecm.conf
	#sed '/net.bridge.bridge-nf-call-iptables=1/d' -i /etc/sysctl.d/qca-nss-ecm.conf

	#Unload ECM_SDX_PCC
	unload_ecm_sdx_pcc

	echo "pre_stop"

}

post_stop() {
	#Post-Stop Commands

	#Enable ipa hw offload for eth
	#[ -f  /sys/class/net/eth0/suspend_ipa_offload ] && echo 0 > /sys/class/net/eth0/suspend_ipa_offload
	#[ -f  /sys/class/net/eth1/suspend_ipa_offload ] && echo 0 > /sys/class/net/eth1/suspend_ipa_offload

	#Enable ipa offload for wifi hmt
	#sed -i 's/^gIPAConfig=.*/gIPAConfig=0x46d/' /etc/misc/wifi/WCNSS_qcom_cfg.ini
	#sed -i 's/^gIPADescSize=.*/gIPADescSize=800/' /etc/misc/wifi/WCNSS_qcom_cfg.ini

	#Enable ipa offload for wifi wkk
	#sed -i '/^gIPAConfig=0$/d' /etc/misc/ipq/ini/internal/global_i.ini
	#sed -i '/^gIPADescSize=0$/d' /etc/misc/ipq/ini/internal/global_i.ini

	#Enable rx-vlan-offload back as ECM is disabled.
	#ethtool -K eth0 rx-vlan-offload on
	#ethtool -K eth1 rx-vlan-offload on

	echo "post_stop"

}

stop() {

	# If ECM is already not loaded, just return.
	if [ ! -d /sys/module/ecm ]; then
		return
	fi

	#Pre-Stop commands
	pre_stop

	#Unload ECM
	unload_ecm

	#Unload SFE (only if loaded)
	unload_sfe

	#Post-Stop commands
	post_stop
}

case "$1" in
	start)
		start
		;;
	stop)
		stop
		;;
	*)
		echo "Usage: $0 {start|stop}"
		exit 1
		;;
esac
