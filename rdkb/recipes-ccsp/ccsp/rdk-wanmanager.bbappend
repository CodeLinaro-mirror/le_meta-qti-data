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
"

LDFLAGS_append = " -L${STAGING_LIBDIR} -lglib-2.0 "
LDFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'RbusBuildFlagEnable', '-lrbus', '', d)}"

do_configure_prepend() {

        if ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_wan_manager', 'false', 'true', d)}; then

        (python ${STAGING_BINDIR_NATIVE}/dm_pack_code_gen.py ${S}/config/RdkWanManager.xml ${S}/source/WanManager/dm_pack_datamodel.c)

        fi

}

