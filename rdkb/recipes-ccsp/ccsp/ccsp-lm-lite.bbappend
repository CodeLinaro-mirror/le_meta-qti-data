FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
require ccsp_common.inc

DEPENDS_append = " utopia-headers glib-2.0 "
CFLAGS_append += " -Wno-error=stringop-truncation -Wno-error=stringop-overflow -Wno-error=implicit-function-declaration "
CFLAGS_append += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "
LDFLAGS_append += " -L${STAGING_LIBDIR} -lglib-2.0 "
SRC_URI += " \
              file://00001-IPPT-changes-for-lm-lite.patch \
              file://00002-lm-lite-crash-fix.patch \
"
