FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "\
file://dhcpcd_iface_info.patch"
SRC_URI += "file://dhcpcd@.service"

# disabling ipv6 for DHCPCD as SLAAC will take care of v6 functionality
do_configure() {
        ./configure --includedir=${STAGING_INCDIR} --bindir=${prefix}/sbin \
        --sbindir=${exec_prefix}/sbin --disable-ipv6
}

do_install:append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
fi
}

FILES:${PN} += "/usr/libexec/*"
FILES:${PN} += "/usr/etc*"
FILES:${PN} += "/data/*"
FILES:${PN} += "/libexec/*"
FILES:${PN}     += "${systemd_unitdir}/system/*"
