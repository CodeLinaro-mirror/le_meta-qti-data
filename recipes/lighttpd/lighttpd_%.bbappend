FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "\
    file://index.html.lighttpd \
    file://lighttpd \
    file://lighttpd.conf \
    file://lighttpd.user \
    file://openssl.cnf \
    file://lighttpd.service \
"

DEPENDS += " openssl"
RDEPENDS:${PN} += " \
               lighttpd-module-alias \
               lighttpd-module-compress \
               lighttpd-module-cgi \
               lighttpd-module-auth \
               lighttpd-module-redirect \
               lighttpd-module-evasive \
               lighttpd-module-authn-file \
               lighttpd-module-openssl \
"
EXTRA_OECONF += " \
             --with-openssl \
             --with-openssl-libs=${STAGING_LIBDIR} \
"
do_install:append() {
   install -d ${D}${userfsdatadir}
   install -d ${D}${userfsdatadir}/www
   install -m 0755 ${WORKDIR}/openssl.cnf ${D}${userfsdatadir}
   install -m 0770 ${WORKDIR}/lighttpd.user ${D}${userfsdatadir}/www/lighttpd.user
   rm -rf ${D}${sysconfdir}/lighttpd.conf
   install -m 0755 ${WORKDIR}/lighttpd.conf ${D}${userfsdatadir}
   rm -rf ${D}/www/logs ${D}/www/var
   if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
       rm -rf ${D}${sysconfdir}/init.d/lighttpd
       install -d ${D}${sysconfdir}/initscripts/
       install -m 0755 ${WORKDIR}/lighttpd -D ${D}${sysconfdir}/initscripts/lighttpd
   fi

}
FILES:${PN} += "${userfsdatadir}/lighttpd.conf"
FILES:${PN} += "${userfsdatadir}/openssl.cnf"
FILES:${PN} += "${userfsdatadir}/www/*"
FILES:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${sysconfdir}/initscripts/lighttpd', '', d)}"
