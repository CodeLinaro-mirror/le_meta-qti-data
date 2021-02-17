FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "\
            file://dhcpcd_iface_info.patch \
            file://dhcpcd@.service"

do_configure() {
        ./configure --includedir=${STAGING_INCDIR} --bindir=${prefix}/sbin \
        --sbindir=${exec_prefix}/sbin --disable-ipv6=yes
}

do_install_append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
fi
}

FILES_${PN} += "/usr/libexec/*"
FILES_${PN} += "/usr/etc*"
FILES_${PN} += "/data/*"
FILES_${PN} += "/libexec/*"
FILES_${PN} += "${systemd_unitdir}/system/*"
