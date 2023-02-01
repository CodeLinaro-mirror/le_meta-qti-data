require dnsmasq.inc


SRC_URI[dnsmasq-2.83.md5sum] = "c87d5af020d12984d2ab9fbf04e2dcca"
SRC_URI[dnsmasq-2.83.sha256sum] = "6b67955873acc931bfff61a0a1e0dc239f8b52e31df50e9164d3a4537571342f"

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
