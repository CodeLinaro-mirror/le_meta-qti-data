DESCRIPTION = "Dnsmasq is a lightweight, easy to configure DNS forwarder and DHCP server."
HOMEPAGE = "http://www.thekelleys.org.uk/dnsmasq/doc.html"
SECTION = "console/network"
LICENSE = "GPL"
LICENSE_${PN} = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

PR = "r5"

#at least versions 2.15 and prior are moved to the archive folder on the server
SRC_URI = "http://www.codeaurora.org/mirrored_source/quic/le/dnsmasq-${PV}.tar.gz;name=dnsmasq-${PV} \
           file://init \
           file://dnsmasq.conf \
           file://dnsmasq_script.sh\
           file://dnsmasq.service\
	   file://qcmap_dhcp_release.service\
	   file://0001-dhcp_release_fix_for_ippt.patch"

EXTRA_OEMAKE = "CC='${CC}' \
                CFLAGS='${TARGET_CFLAGS}' \
                LDFLAGS='${TARGET_LDFLAGS}'"

do_compile_append() {
      # build dhcp_release
      cd ${S}/contrib/lease-tools
      oe_runmake
}

do_install () {
        oe_runmake "PREFIX=${D}${prefix}" \
                   "BINDIR=${D}${bindir}" \
                   "MANDIR=${D}${mandir}" \
                   install
        install -m 0755 ${S}/contrib/lease-tools/dhcp_release ${D}${bindir}
        install -d ${D}${sysconfdir}/ ${D}${sysconfdir}/init.d ${D}${sysconfdir}/dnsmasq.d
        install -d ${D}${sysconfdir}/data/
        install -m 664 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}/data
        chown -R root:1001 ${D}${sysconfdir}/data/dnsmasq.conf
        # symlink dnsmasq.conf under /etc
        ln -sf ../${sysconfdir}/data/dnsmasq.conf ${D}${sysconfdir}/dnsmasq.conf

        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
         install -d ${D}/etc/initscripts
         install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/initscripts/dnsmasq
         install -d ${D}/etc/systemd/system/
         install -m 0644 ${WORKDIR}/dnsmasq.service -D ${D}/etc/systemd/system/dnsmasq.service
	 install -m 0644 ${WORKDIR}/qcmap_dhcp_release.service -D ${D}${systemd_unitdir}/system/qcmap_dhcp_release.service
         install -d ${D}/etc/systemd/system/multi-user.target.wants/
        else
         install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/dnsmasq
        fi
        install -d ${D}${base_bindir}
        install -m 0755 ${WORKDIR}/dnsmasq_script.sh ${D}${base_bindir}
        chown -h 65534:65534 ${D}${base_bindir}/dnsmasq_script.sh
}

CONFFILES_${PN} = "${sysconfdir}/data/dnsmasq.conf"
FILES_${PN} += "${sysconfdir}/data/dnsmasq.conf"
