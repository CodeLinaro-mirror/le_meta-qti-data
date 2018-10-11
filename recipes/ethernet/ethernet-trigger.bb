inherit autotools  systemd

DESCRIPTION = "Ethernet Trigger"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r0"
PV = "1.0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://ethernet-trigger.service"
SRC_URI += "file://ethernet-trigger.sh"

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -m 755 ${WORKDIR}/ethernet-trigger.sh -D ${D}/${sysconfdir}/ethernet-trigger.sh
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/ethernet-trigger.service -D ${D}${systemd_unitdir}/system/ethernet-trigger.service
        install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
        ln -sf ${systemd_unitdir}/system/ethernet-trigger.service \
           ${D}${systemd_unitdir}/system/multi-user.target.wants/ethernet-trigger.service
    else
        install -m 755 ${WORKDIR}/ethernet-trigger.sh -D ${D}/${sysconfdir}/init.d/ethernet-trigger.sh
    fi
}

FILES_${PN} += "${systemd_unitdir}/system/"
