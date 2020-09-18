require dnsmasq.inc

SRC_URI[dnsmasq-2.81.md5sum] = "e43808177a773014b5892ccba238f7a8"
SRC_URI[dnsmasq-2.81.sha256sum] = "3c28c68c6c2967c3a96e9b432c0c046a5df17a426d3a43cffe9e693cf05804d0"

SRC_URI += "file://dnsmasq_service@.service"
SRC_URI += "file://qcmap_start_dnsmasq.sh"

do_install_append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dnsmasq_service@.service -D ${D}${systemd_unitdir}/system/dnsmasq_service@.service
   install -m 0755 ${WORKDIR}/qcmap_start_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_start_dnsmasq.sh
fi
}
FILES_${PN} += "${systemd_unitdir}/system/*"
