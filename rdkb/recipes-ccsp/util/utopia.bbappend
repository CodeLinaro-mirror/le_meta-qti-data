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
    file://00017-utopia-sip-alg-configuration.patch \
    file://00018-utopia-MTU-information.patch \
    file://00019-utopia-fix-pptp-rtsp-raw-table-initialization.patch \
    file://00020-set-eth-perf-invocation.patch \
    file://00021-qti-utopia-TCPMSS_clamping_rules_adding.patch \
    file://00022-qti-utopia-TCPMSS_clamping_rules_adding_extn_support.patch \
    file://00023-utopia-skip-wan-iface-in-bringup.patch \
    file://00024-utopia-6brd.patch \
    file://00029-qti-utopia-dhcpv6-stateless.patch \
    file://00025-utopia-service-igd-miniupnpd-echo-support.patch \
"
do_install:append() {
    install -m 0755 ${UNPACKDIR}/service_sshd_qti.sh \
        ${D}/etc/utopia/service.d/service_sshd_qti.sh
}
