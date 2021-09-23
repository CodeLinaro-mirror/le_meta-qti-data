SUMMARY = "QPS615"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

inherit systemd

FILES_${PN}     += "${systemd_unitdir}/system/*"

SRC_URI += "file://qps615.service"

SYSTEMD_SERVICE_${PN} = "qps615.service"

do_install() {
   install -m 0644 ${WORKDIR}/qps615.service -D ${D}${systemd_unitdir}/system/qps615.service
}

