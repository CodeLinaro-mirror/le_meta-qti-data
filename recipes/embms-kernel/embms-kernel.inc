inherit autotools-brokensep module

DESCRIPTION = "Embms Kernel Module"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r1"

FILESPATH =+ "${WORKSPACE}:"

SRC_URI = "file://kernel/msm-${PV}/net/embms_kernel/ \
           file://start_embms_le \
           file://embmsd.service"

FILES_${PN}="/etc/initscripts/start_embms_le"
FILES_${PN}+= "${systemd_unitdir}/system/embmsd.service"
FILES_${PN}+= "${systemd_unitdir}/system/multi-user.target.wants/embmsd.service"

do_install() {
        module_do_install
        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
              install -d ${D}${systemd_unitdir}/system/
              #embms-kernel service
              install -d ${D}${sysconfdir}/initscripts
              install -m 0755 ${WORKDIR}/start_embms_le ${D}${sysconfdir}/initscripts
              install -m 0644 ${WORKDIR}/embmsd.service -D ${D}${systemd_unitdir}/system/embmsd.service
              install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
              # enable the service for multi-user.target
              ln -sf ${systemd_unitdir}/system/embmsd.service \
              ${D}${systemd_unitdir}/system/multi-user.target.wants/embmsd.service
        fi
}
