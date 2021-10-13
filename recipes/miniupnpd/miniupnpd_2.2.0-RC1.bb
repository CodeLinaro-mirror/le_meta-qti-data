inherit autotools-brokensep gettext pkgconfig systemd
SUMMARY = "Lightweight implementation of a UPnP IGD daemon."
DESCRIPTION = "MiniUPnPd is a low memory daemon which acts as a\
UPnP device, enabling seamless detection of other UPnP devices/control points."
HOMEPAGE = "http://miniupnp.free.fr/"
BUGTRACKER = "http://miniupnp.tuxfamily.org/forum/viewforum.php?f=2"
LICENSE = "BSD"
PRIORITY = "optional"
DEPENDS = "conntrack-tools iptables net-tools util-linux libmnl libnetfilter-conntrack"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"

# Package Revision (update whenever recipe is changed) 
PR = "r9"

SRC_URI = "\
    https://codeaurora.org/mirrored_source/quic/le/miniupnpd-2.2.0-RC1.tar.gz \
    file://0001-miniupnpd_conf_security.patch \
    file://0001-security-fix.patch \
    file://0001-makefile_oss.patch \
    file://miniupnpd.service \
"

SRC_URI[md5sum] = "9b8144098729594240c545f76ef489b6"
SRC_URI[sha256sum] = "5e085aa5bde795520be99162504008c3b08597d6baff764e8482b35eb9eb3526"

IPV6 = "${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', '--ipv6', '', d)}"

do_configure[noexec] = "1"


# ${BASE_WORKDIR}/armv7at2hf-neon-oe-linux-gnueabi/iptables/1.6.2-r0/build


do_compile () {
    cd ${S}
        ./configure ${IPV6} --strict --vendorcfg --debug --igd2
        make LIBDIR=${STAGING_LIBDIR} INCDIR=${STAGING_INCDIR} STRIP=echo
}

do_install () {
    make DESTDIR=${D} LIBDIR=${STAGING_LIBDIR} INCDIR=${STAGING_INCDIR} STRIP=echo install
}

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        rm -rf ${D}${sysconfdir}/init.d/miniupnpd
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/miniupnpd.service -D ${D}${systemd_unitdir}/system/miniupnpd.service
    fi
}

FILES_${PN} += "${sysconfdir}/initscripts/*"
FILES_${PN} += "${systemd_unitdir}/system/*"
