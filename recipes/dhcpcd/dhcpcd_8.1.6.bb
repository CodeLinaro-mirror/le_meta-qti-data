SECTION = "console/network"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"

DESCRIPTION = "dhcpcd is an RFC2131-, RFC2132-, and \
RFC1541-compliant DHCP client daemon. It gets an IP address \
and other information from the DHCP server, automatically \
configures the network interface, and tries to renew the \
lease time according to RFC2131 or RFC1541."

PR = "r4"

inherit autotools-brokensep

SRC_URI = "http://roy.marples.name/downloads/${BPN}/${BPN}-${PV}.tar.xz"
SRC_URI += "file://dhcpcd@.service"

do_configure() {
        ./configure --includedir=${STAGING_INCDIR} --bindir=${prefix}/sbin \
        --sbindir=${exec_prefix}/sbin
}

FILES_${PN} +="/usr/libexec/*"
FILES_${PN} += "/usr/etc*"
FILES_${PN} += "/data/*"
FILES_${PN} += "/libexec/*"
FILES_${PN} += "/libexec/dhcpcd-hooks/*"

SRC_URI[md5sum] = "e1eea03388d12c9ad21ecd7c135fdf8b"
SRC_URI[sha256sum] = "6c2934a3e1e67a5cfd5bb15b1efa71f65c00314ac1ccb5c50da8eae3a0b8147f"

do_install_append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
fi
}
FILES_${PN}     += "${systemd_unitdir}/system/*"
