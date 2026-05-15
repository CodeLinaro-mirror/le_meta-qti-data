FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append += "file://0001-ethagent-23Q1-Port-modified.patch \
                   file://0002-Device_Host_fix.patch \
                   file://0003-ethagent-devicemode.patch \
                   file://0004-Device_Host_WiFiClient_Fix.patch \
                   file://0005-ethagent-fix-leak.patch \
                   file://0006-wan-failover.patch \
                   "

require ccsp_common.inc

#SRC_URI_remove = "${CMF_GIT_ROOT}/rdkb/components/opensource/ccsp/CcspEthAgent;protocol=${CMF_GIT_PROTOCOL};branch=${CMF_GIT_BRANCH};name=CcspEthAgent"
#SRC_URI += "${CLO_RDKB_GIT}/rdkb/components/opensource/ccsp/CcspEthAgent;protocol=${CMF_GIT_PROTOCOL};branch=${CLO_RDKB_BRANCH};name=CcspEthAgent"

DEPENDS_append = " utopia-headers "

CFLAGS_append += " -Wno-error=stringop-truncation -Wno-error=format-security -Wno-error=stringop-overflow -Wno-error=unused-parameter -Wno-error=return-type -Wno-error -Dstrlcpy=g_strlcpy -DETH_2_PORTS "
CFLAGS_aarch64_append = " -Werror=format-truncation=1 "
DEPENDS += " glib-2.0 "
LDFLAGS_append = " -L${STAGING_LIBDIR} -lglib-2.0 "
DEPENDS += " rdkb-halif-platform "
#CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'guest_wifi', ' -DGUEST_WIFI ', '', d)}"

DISTRO_FEATURES:remove = "ethstats"


CFLAGS_append += " -Og "

CFLAGS:remove = "-DFEATURE_SUPPORT_ONBOARD_LOGGING"
CXXFLAGS:remove = "-DFEATURE_SUPPORT_ONBOARD_LOGGING"

