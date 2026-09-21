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
    file://0014-qti-rdk-cellularmanager-mm_factory_reset.patch \
    file://0015-qti-rdk-cellularmanager-mm_ipv6_prefix_delegation.patch \
    file://0016-qti-rdk-cellularmanager-mm_modem_SSR_handling.patch \
    file://0017-qti-rdk-cellularmanager-mm_roaming.patch \
    file://0018-qti-rdk-cellularmanager-mm_address_reconf.patch \
    file://0019-qti-rdk-cellularmanager-mm_modem_status.patch \
    file://0020-qti-rdk-cellularmanager-mm_SM_optimization.patch \
    file://0021-qti-rdk-cellularmanager-mm_add_error_stats.patch \
    file://0022-qti-rdk-cellularmanager-mm_data_quota_usage.patch \
    file://0023-qti-rdk-cellularmanager-mm_DataUsageDM.patch \
    file://0024-qti-rdk-cellularmanager-mm_Add-DNSSL-SM-triggers-and-RBUS-DM-registrations.patch \
    file://0025-qti-rdk-cellularmanager-mm_available_network.patch \
    file://0026-qti-rdk-cellularmanager-mm_USIM_port.patch \
    file://0027-qti-rdk-cellularmanager-mm_IP_continuity.patch \
    file://0028-qti-rdk-cellularmanager-mm_WAN_ipmode_support.patch \
    file://0029-qti-rdk-cellularmanager-mm_Interface_DM.patch \
    file://0030-qti-rdk-cellularmanager-mm_NRSA_Enabled.patch \
    file://0031-qti-rdk-cellularmanager-mm_IP_continuity_data_quota_usage.patch \
    file://0032-qti-rdk-cellularmanager-mm_rsrp_rsrq.patch \
    file://0033-qti-rdk-cellularmanager-mm_CellTower_DM.patch \
    file://0034-qti-rdk-cellularmanager-mm_carrier_aggregation.patch \
    file://0035-qti-rdk-cellularmanager-mm_factory_test_mode_fix.patch \
    file://0036-qti-rdk-cellularmanager-mm_KPI_marker.patch \
    file://0037-qti-rdk-cellularmanager-mm_refcount.patch \
    file://0038-qti-rdk-cellularmanager-mmDNSSL-Unregistartion-Fix.patch \
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

PACKAGECONFIG_append = " cm_extn_support cm_prop_support ip_continuity_enabled"
PACKAGECONFIG[cm_extn_support] = ""
CFLAGS_append  += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_extn_support', ' -DCM_EXTN_SUPPORTED', '', d)}"

LDFLAGS += " -lmm-glib -lpthread -lbsd -lrt"
CFLAGS_append += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_prop_support', ' -DCM_PROP_SUPPORTED', '', d)}"
CFLAGS_append += " ${@bb.utils.contains('PACKAGECONFIG', 'cm_prop_support', ' -I${STAGING_INCDIR}/cellular-hal-qmi/', '', d)}"
CFLAGS_append += " ${@bb.utils.contains('PACKAGECONFIG', 'ip_continuity_enabled', ' -DIP_CONTINUITY_ENABLED', '', d)}"
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
