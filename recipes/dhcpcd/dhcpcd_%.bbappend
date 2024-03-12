FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "\
file://dhcpcd_iface_info.patch"
SRC_URI += "file://dhcpcd@.service"

#observing compilation errors. Hence commented. will check later and uncomment if required
#do_configure:append() {
#        ./configure --includedir=${STAGING_INCDIR} --bindir=${prefix}/sbin \
#        --sbindir=${exec_prefix}/sbin
#}

do_install:append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dhcpcd@.service -D ${D}${systemd_unitdir}/system/dhcpcd@.service
   rm -f ${D}${sysconfdir}/systemd/dhcpcd.service
fi
}

FILES:${PN} += "/usr/libexec/*"
FILES:${PN} += "/usr/etc*"
FILES:${PN} += "/data/*"
FILES:${PN} += "/libexec/*"
FILES:${PN} += "${systemd_unitdir}/system/*"
SYSTEMD_SERVICE:${PN} = "dhcpcd@.service"
