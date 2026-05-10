require ccsp_common.inc
inherit coverity
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0002-qti-rdk-cellularmanager-mm_23q1_consolidation.patch \
    file://0003-qti-rdk-cellularmanager-mm_wan_bringup.patch \
    file://0004-qti-rdk-cellularmanager-mm_dm_walk_crash_fix.patch \
"

DEPENDS += " modemmanager libbsd libqmi "
DEPENDS += " ${@bb.utils.contains('DISTRO_FEATURES', 'cellular_libqmi_support', ' libqrtr-glib', '', d)}"
DEPENDS += " ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_cellular_manager', ' libqrtr-glib', '', d)}"

CFLAGS += "-I${STAGING_INCDIR}/libmm-glib/"
CFLAGS += "-I${STAGING_INCDIR}/ModemManager/"
CFLAGS += "-I${STAGING_INCDIR}/libqmi-glib/"
CFLAGS += "-I${STAGING_INCDIR}"
CFLAGS += "-DMM_SUPPORT"
CFLAGS += "-DWITH_QMI"
CFLAGS += "-I${STAGING_INCDIR}/libqrtr-glib/"
CFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'cellular_libqmi_support', '-I${STAGING_INCDIR}/libqmi-glib', '', d)}"
CFLAGS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_cellular_manager', ' -I${STAGING_INCDIR}/libqmi-glib', '', d)}"
CFLAGS += " -Wformat-security -Wunused-result -Wno-error=format-security -Wno-error=implicit-function-declaration -Wno-incompatible-pointer-types "
CFLAGS += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "
CFLAGS_remove = "-DINCLUDE_BREAKPAD"
CFLAGS += "-DRBUS_BUILD_FLAG_ENABLE"
CFLAGS += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_extn_support', ' -DCM_EXTN_SUPPORTED', '', d)}"
CFLAGS += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_acs_support', ' -DCM_ACS_SUPPORTED', '', d)}"
CFLAGS += " ${@bb.utils.contains('PACKAGECONFIG', 'ip_continuity_enabled', ' -DIP_CONTINUITY_ENABLED', '', d)}"
CFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'dhcp_manager', '-DFEATURE_RDKB_DHCP_MANAGER', '', d)}"
CFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_configurable_wan_interface', '-DFEATURE_RDKB_CONFIGURABLE_WAN_INTERFACE', '', d)}"

PACKAGECONFIG_append = " cm_extn_support cm_acs_support ip_continuity_enabled"
PACKAGECONFIG[cm_extn_support] = ""
PACKAGECONFIG[cm_acs_support] = ""
PACKAGECONFIG[ip_continuity_enabled] = ""

LDFLAGS += " -lmm-glib -lpthread -lbsd -lrt"
LDFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'cellular_libqmi_support', '-lqmi-glib -lqrtr-glib', '', d)}"
LDFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_cellular_manager', '-lqmi-glib -lqrtr-glib', '', d)}"

EXTRA_OECONF_append += " ${@bb.utils.contains('TDK_ENABLED', 'true', '--enable-tdk', '', d)}"

do_configure:prepend() {
    if ${@bb.utils.contains_any('DISTRO_FEATURES', 'WanFailOverSupportEnable rdkb_cellular_manager', 'true', 'false', d)}; then
        (${PYTHON} ${STAGING_BINDIR_NATIVE}/dm_pack_code_gen.py ${S}/config/RdkCellularManager.xml ${S}/source/CellularManager/dm_pack_datamodel.c)
    fi
}

do_install:append () {
    install -d ${D}${systemd_unitdir}/system
    #For creating cellular hal library for TDK
    install -D -d ${D}/usr/include/ccsp
    install -m 644 ${S}/source/CellularManager/*.h ${D}/usr/include/ccsp
}

#For creating cellular hal library for TDK
FILES_${PN} += " \
   ${libdir}/* \
"
