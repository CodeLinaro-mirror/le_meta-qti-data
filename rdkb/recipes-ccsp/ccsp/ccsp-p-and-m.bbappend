FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
require ccsp_common.inc

DEPENDS_append = " utopia-headers utopia ccsp-common-library "
RDEPENDS_${PN}-ccsp_remove_dunfell = " bash "
RDEPENDS_${PN}_append = " bash "
DEPENDS += " glib-2.0 "

CFLAGS_append += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'syslog-ng', ' -D_SYSLOG_NG ', '', d)}"
SRC_URI += " \
              file://00001-CcspPandM-23Q1_Port.patch \
              file://00002-CcspPandM-vendor.patch \
              file://00003-fix-iana-addr-null-deref.patch \
"
SECURITY_CFLAGS_remove += " -Werror=format-security -Werror=format "
SECURITY_CFLAGS_append += " -Wno-error=format-security -Wno-error=format "

LDFLAGS_append = " -L${STAGING_LIBDIR} -lglib-2.0 "

CFLAGS_append += " -Wno-stringop-truncation -Wno-stringop-overflow -Wno-error=format-truncation= -Wno-error=unused-parameter -Wno-error=return-type -Wno-error=sign-compare -Wno-error=implicit-function-declaration -Wno-error=int-conversion -Wno-error=parentheses -Wno-error=unused-variable -Wno-error=int-to-pointer-cast -Wno-error=pointer-to-int-cast "
CFLAGS_append += " \
    -I=${includedir}/utctx \
    -I=${includedir}/utapi \
    -I=${includedir}/ccsp \
"

do_install_append() {
    # Config files and scripts
    install -m 644 ${S}/config-arm/CcspDmLib.cfg ${D}/usr/ccsp/pam/CcspDmLib.cfg
    install -m 644 ${S}/config-arm/CcspPam.cfg -t ${D}/usr/ccsp/pam
    install -m 644 ${S}/config-arm/TR181-USGv2.XML -t ${D}/usr/ccsp/pam
    install -m 777 ${D}/usr/bin/CcspPandMSsp -t ${D}/usr/ccsp/pam/
}
FILES_${PN} += " \
    ${prefix}/ccsp/pam/CcspPandMSsp \
"
FILES_${PN}_append = " \
    /usr/ccsp/pam/CcspDmLib.cfg \
    /usr/ccsp/pam/CcspPam.cfg \
    /usr/ccsp/pam/TR181-USGv2.XML \
    /usr/ccsp/pam/launch_tr69.sh \
"
