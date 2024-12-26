
LICENSE_dnsmasq = "GPL-2.0-only | GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;beginline=1;endline=340;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI += "file://dnsmasq_service@.service"
SRC_URI += "file://qcmap_start_dnsmasq.sh"
SRC_URI += "file://qcmap_stop_dnsmasq.sh"

do_install_append(){
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dnsmasq_service@.service -D ${D}${systemd_unitdir}/system/dnsmasq_service@.service
   install -m 0755 ${WORKDIR}/qcmap_start_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_start_dnsmasq.sh
   install -m 0755 ${WORKDIR}/qcmap_stop_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
   chown 1001:1001 ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
fi
}
FILES_${PN} += "${systemd_unitdir}/system/*"
