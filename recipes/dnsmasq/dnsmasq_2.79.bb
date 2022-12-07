require dnsmasq.inc

SRC_URI[dnsmasq-2.79.md5sum] = "5d7120a46d0c16a334f46757d7e2ba55"
SRC_URI[dnsmasq-2.79.sha256sum] = "77512dd6f31ffd96718e8dcbbf54f02c083f051d4cca709bd32540aea269f789"

SRC_URI += "file://dnsmasq_service@.service"
SRC_URI += "file://qcmap_start_dnsmasq.sh"
SRC_URI += "file://qcmap_stop_dnsmasq.sh"

do_install_append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dnsmasq_service@.service -D ${D}${systemd_unitdir}/system/dnsmasq_service@.service
   install -m 0755 ${WORKDIR}/qcmap_start_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_start_dnsmasq.sh
   install -m 0755 ${WORKDIR}/qcmap_stop_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
   chown radio:radio ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
fi
}
FILES_${PN} += "${systemd_unitdir}/system/*"
