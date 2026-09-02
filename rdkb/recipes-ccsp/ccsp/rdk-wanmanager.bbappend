require ccsp_common.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
CFLAGS_remove = " -DFEATURE_RDKB_INTER_DEVICE_MANAGER"
CFLAGS_append = " -D_PLATFORM_SDX_ "
DEPENDS += " glib-2.0 "
CFLAGS_append += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "

DEPENDS += "systemd"
LDFLAGS_append = " -lsystemd"

CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'syslog-ng', ' -D_SYSLOG_NG ', '', d)}"

SRC_URI += " \
    file://00001-qti-wanmanager_bringup.patch \
    file://00002-qti-WanManager_ethwan_v4_bringup.patch \
    file://00003-qti-WanManager_DNSProxy_bringup.patch \
    file://00004-qti-WanManager_v6_bringup_support.patch \
    file://00005-qti-WanManager_sysevent_get_wanifname.patch \
    file://00006-qti-WanManager_cellular_v6address_stabilize.patch \
    file://00007-qti-wanmanager_automated_usb_tethering.patch \
    file://00008-IPPT-changes-for-wan-manager.patch \
    file://00009-qti-WanManager-remove-ippt-ipa-ippass-calls.patch \
    file://00010-qti-Wanmanager-AddressReconf-brlan0-old-ipv6-not-deleting.patch \
    file://00011-qti-Wanmanager_handle_IPv6nei.patch \
    file://00012-qti-WanManager_address_reconf.patch \
    file://00013-qti-WanManager_cellular_mtu_fix.patch \
    file://00014-qti-WanManager_v6address_eth1_wan_accept_ra_pinfo.patch \
    file://00015-qti-Wanmanager-kill-6brd.patch \
    file://00016-qti-Wanmanager-eth1-auto-bringup-on-bind.patch \
    file://00017-qti-Wanmanager-RTSP-module-check.patch \
    file://00018-qti-WanManager-IPPT-Default-Route-Fix.patch \
    file://00019-qti-packet-stats-changes-for-wan-manager.patch \
    file://00020-qti-wanmanager_ECM-PerIP-Stats-Integration.patch \
    file://00021-qti-Wanmanager-DeviceMode-support.patch \
    file://00022-qti-Wanmanager-DNS_proxy_enable.patch \
    file://00023-qti-Wanmanager-eth1-bringup-delay.patch \
"

LDFLAGS_append = " -L${STAGING_LIBDIR} -lglib-2.0 "
LDFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'RbusBuildFlagEnable', '-lrbus', '', d)}"

do_configure_prepend() {

        if ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_wan_manager', 'false', 'true', d)}; then

        (python ${STAGING_BINDIR_NATIVE}/dm_pack_code_gen.py ${S}/config/RdkWanManager.xml ${S}/source/WanManager/dm_pack_datamodel.c)

        fi

}

