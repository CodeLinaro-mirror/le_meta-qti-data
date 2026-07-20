FILESPATH =+ "${WORKSPACE}/rdkb/devices:"
SRC_URI += "file://RdkCellularManager-MM/"
require ccsp_common.inc
inherit coverity
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-qti-rdk-cellularmanager-mm-fix-wanmanager-qmapmux-interface.patch \
    file://0002-qti-rdk-cellularmanager-mm-fix-wan-ip-source.patch \
    file://0003-qti-rdk-cellularmanager-mm_dm_walk_crash_fix.patch \
    file://0004-qti-rdk-cellularmanager-mm_autoconnect_connectbackhaul.patch \
    file://0005-qti-rdk-cellularmanager-mm_ipv6.patch \
    file://0006-qti-rdk-cellularmanager-mm_preferred_access_technologies.patch \
    file://0007-qti-rdk-cellularmanager-mm_XQCOM_rename.patch \
    file://0008-qti-rdk-cellularmanager-mm_RoamingEnablement.patch \
    file://0009-qti-rdk-cellularmanager-mm_MultiAPN.patch \
    file://0010-qti-rdk-cellularmanager-mm_stats.patch \
    file://0011-qti-rdk-cellularmanager-mm_fix-PRAT-switch.patch \
    file://0012-qti-rdk-cellularmanager-mm_loopback_mode.patch \
    file://0013-qti-rdk-cellularmanager-mm_prop_HAL_integration.patch \
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
CFLAGS_append += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "
CFLAGS_remove = "-DINCLUDE_BREAKPAD"
CFLAGS += "-DRBUS_BUILD_FLAG_ENABLE"

PACKAGECONFIG_append = " cm_extn_support cm_prop_support"
PACKAGECONFIG[cm_extn_support] = ""
CFLAGS_append  += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_extn_support', ' -DCM_EXTN_SUPPORTED', '', d)}"

LDFLAGS += " -lmm-glib -lpthread -lbsd -lrt"
CFLAGS_append += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_prop_support', ' -DCM_PROP_SUPPORTED', '', d)}"
CFLAGS_append += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_prop_support', ' -I${STAGING_INCDIR}/cellular-hal-qmi/', '', d)}"
LDFLAGS_append += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_prop_support', ' -lcellular_hal_qmi', '', d)}"
DEPENDS += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_prop_support', ' hal-cellular-qmi', '', d)}"
LDFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'cellular_libqmi_support', '-lqmi-glib -lqrtr-glib', '', d)}"
LDFLAGS += " ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_cellular_manager', '-lqmi-glib -lqrtr-glib', '', d)}"

EXTRA_OECONF_append += " ${@bb.utils.contains('TDK_ENABLED', 'true', '--enable-tdk', '', d)}"

do_configure:prepend() {

    cp ${UNPACKDIR}/RdkCellularManager-MM/CellularManager_extn/CellularManager/* ${S}/source/CellularManager/
    cp ${UNPACKDIR}/RdkCellularManager-MM/CellularManager_extn/TR-181/middle_layer_src/* ${S}/source/TR-181/middle_layer_src/
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
