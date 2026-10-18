FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
require ccsp_common.inc
DEPENDS_append = " breakpad-wrapper"

EXTRA_OEMAKE_append = " LIBS='-lbreakpadwrapper'"
SRC_URI += " \
             file://00001-qti_DHCPManager_perf_build_fix.patch \
"
