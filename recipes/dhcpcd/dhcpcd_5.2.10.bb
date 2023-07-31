SECTION = "console/network"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

DESCRIPTION = "dhcpcd is an RFC2131-, RFC2132-, and \
RFC1541-compliant DHCP client daemon. It gets an IP address \
and other information from the DHCP server, automatically \
configures the network interface, and tries to renew the \
lease time according to RFC2131 or RFC1541."

PR = "r4"

inherit autotools-brokensep

SRC_URI = "https://github.com/NetworkConfiguration/dhcpcd/archive/refs/tags/v${PV}.tar.gz"

SRC_URI += "file://dhcpcd@.service"

do_configure() {
        ./configure --includedir=${STAGING_INCDIR} --bindir=${prefix}/sbin \
        --sbindir=${exec_prefix}/sbin
}


FILES:${PN} +="/usr/libexec/*"
FILES:${PN} += "/usr/etc*"
FILES:${PN} += "/data/*"

INSANE_SKIP:${PN} += "installed-vs-shipped"
INSANE_SKIP:${PN} += "empty-dirs"

SRC_URI[md5sum] = "cdfe89c40683163859ffa2a555b57863"
SRC_URI[sha256sum] = "f36dc1673962d71adf94c460450eaa4a5e7b23aa7dc52fea195f645d86b035d5"

do_install:append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
fi
}
FILES:${PN}     += "${systemd_unitdir}/system/*"
