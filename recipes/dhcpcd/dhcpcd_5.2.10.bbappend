FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "\
file://dhcpcd_iface_info.patch"
SRC_URI += "file://dhcpcd@.service"

do_install_append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
fi
}
FILES_${PN}     += "${systemd_unitdir}/system/*"
