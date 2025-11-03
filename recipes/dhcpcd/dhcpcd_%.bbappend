FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "\
            file://dhcpcdv6_reset_RA.patch \
            file://dhcpcd_iface_info.patch \
            file://dhcpcd@.service"

do_configure() {
        ./configure --includedir=${STAGING_INCDIR} --bindir=${prefix}/sbin \
        --sbindir=${exec_prefix}/sbin  --disable-ipv6
}

DBDIR = "${localstatedir}/run/db/${BPN}"

do_install:append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
   rm -rf ${D}${localstatedir}/run
fi
}

FILES:${PN} += "/usr/libexec/*"
FILES:${PN} += "/usr/etc*"
FILES:${PN} += "/data/*"
FILES:${PN} += "/libexec/*"
FILES:${PN} += "${systemd_unitdir}/system/*"
FILES:${PN} += "/lib/*"
