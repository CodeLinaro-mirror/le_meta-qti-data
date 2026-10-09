require rdkb/recipes-ccsp/ccsp/ccsp_common.inc
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://service_sshd_qti.sh \
    file://system_defaults \
    file://00001-qti-utopia_brlan0_bring_up.patch \
    file://00002-ethlan_utopia.patch \
    file://00003-Utopia-DNSProxy-bringup.patch \
    file://00004-firewall-macro.patch \
    file://00005-qti-utopia-Embedded_Downlink_fix.patch \
    file://00006-qti-utopia_zebra_fix.patch \
    file://00007-qti-utopia-eth1_ipv6_enable.patch\
    file://00008-ipv6_downlink_fix.patch \
    file://00009-utopia-usb_interface_bringup.patch \
    file://00010-utopia-ssh-gateway-IPv4.patch \
    file://00011-utopia-Fix-for-SSH-access-Failure-post-reboot.patch \
    file://00012-utopia-IPPT-bringup.patch \
    file://00013-utopia-factory-reset-changes.patch \
    file://00014-qti-utopia-Legacy-IPv6-PD-prefix-client-management.patch \
    file://00015-utopia-lan-vlan-changes.patch \
    file://00016-utopia-sip-alg-configuration.patch \
    file://00017-utopia-MTU-information.patch \
    file://00018-utopia-fix-pptp-rtsp-raw-table-initialization.patch \
    file://00019-set-eth-perf-invocation.patch \
    file://00020-qti-utopia-TCPMSS_clamping_rules_adding.patch \
    file://00021-qti-utopia-TCPMSS_clamping_rules_adding_extn_support.patch \
    file://00022-utopia-skip-wan-iface-in-bringup.patch \
    file://00023-utopia-6brd.patch \
    file://00024-qti-utopia-dhcpv6-stateless.patch \
    file://00025-utopia-service-igd-miniupnpd-echo-support.patch \
    file://00026-qti-utopia-packet-stats-changes.patch \
    file://00027-qti-utopia-site-filter-fix.patch \
    file://00028-qti-site-filter-bug-with-url-change-of-same-table.patch \
    file://00029-qti-utopia-site-filter-fix-fopen-append-fix.patch \
    file://00030-qti-utopia-DNS_proxy_enable.patch \
    file://00031-qti-utopia-firewall-white-black-list.patch \
    file://00032-qti-utopia-DDNS.patch \
    file://00033-qti-utopia-fix-for-ping-not-working-from-IPPT-client-to-brlan0.patch \
    file://00034-utopia-ssh-filter-accept-rule-for-platform-sdx.patch \
    file://00035-qti-utopia-fix-for-stale-conntrack-entries.patch \
    file://00036-qti-utopia-WLAN-client-IPPT-support.patch \
    file://00037-qti-utopia-IPPT-tunnel-mode-support.patch \
    file://00038-qti-utopia-Changes-to-update-dns-search-list-to-zebra.conf.patch \
    file://00039-qti-utopia-logupload.patch \
    file://00040-qti-utopia-fix-vpn-passthrough-default-to-false-when-syscfg-unset.patch \
    file://00041-qti-utopia-lan_IP_DM_fix.patch \
    file://00042-qti-utopia-timestamp_Fix.patch \
    file://00044-qti-utopia-connmark_entry_empty_tcp_udp_fix.patch \
    file://00045-qti-utopia-managed-devices-udp-block-mangle-fix.patch \
    file://00046-qti-utopia-firewall_whitelist_Uplink-embedded-traffic-support.patch \
    file://00047-qti-utopia-RelayMode_support.patch \
    file://00048-qti-utopia-zebra_BH_Toggle_Fix.patch \
    file://00049-qti-utopia-UDP_conntrack_deletion_timing_issue.patch \
    file://00050-qti-utopia-dnsmasq-log-facility-fix.patch \
    file://00051-qti-utopia-fix-sitefilter-hw-accel-bypass.patch \
    file://00047-qti-utopia-WLAN-IPPT-toggle.patch \
"
do_install:append() {
    install -m 0755 ${UNPACKDIR}/service_sshd_qti.sh \
        ${D}/etc/utopia/service.d/service_sshd_qti.sh
}
