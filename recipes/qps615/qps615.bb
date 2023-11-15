SUMMARY = "QPS615"

LICENSE = "BSD-3-Clause & BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
LIC_FILES_CHKSUM += "file://${COREBASE}/meta-qti-bsp/files/common-licenses/\
BSD-3-Clause-Clear;md5=3771d4920bd6cdb8cbdf1e8344489ee0"

inherit systemd

FILES_${PN}     += "${systemd_unitdir}/system/*"

SRC_URI += "file://qps615.service"
SRC_URI += "file://qps615.conf"

SYSTEMD_SERVICE_${PN} = "qps615.service"

do_install() {
   install -m 0644 ${WORKDIR}/qps615.service -D ${D}${systemd_unitdir}/system/qps615.service
   install -m 0644 ${WORKDIR}/qps615.conf -D ${D}${sysconfdir}/modprobe.d/qps615.conf
}

