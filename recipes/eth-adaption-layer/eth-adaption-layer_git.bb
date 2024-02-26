inherit module ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "Ethernet Adaptation Module"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"


FILES:${PN}     += "${sysconfdir}/init.d/*"
FILES:${PN}     += "${systemd_unitdir}/system/*"
FILES:${PN}     += "${sysconfdir}/initscripts/*"

PR = "r0"

FILESPATH =+ "${WORKSPACE}/data-kernel/drivers:"
SRC_URI = "file://eth-adaption-layer"
SRC_URI += "file://start_eth-adaption-layer_le"
SRC_URI += "${@bb.utils.contains_any('MACHINE', 'qtiquingvm-tele qtiquingvm-tele-lxc', 'file://eth-adaption-layer_lv.service', 'file://eth-adaption-layer.service',d)}"
SRC_URI += "${@bb.utils.contains_any('MACHINE', 'qtiquingvm-tele qtiquingvm-tele-lxc', 'file://emac_lv.service', 'file://emac.service',d)}"
SRC_URI += "file://config.ini"
SRC_URI += "file://emac.sh"
S = "${WORKDIR}/eth-adaption-layer/"

do_install() {
    module_do_install
	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/initscripts
        install -m 0755 ${WORKDIR}/start_eth-adaption-layer_le ${D}${sysconfdir}/initscripts
		install -m 0755 ${WORKDIR}/emac.sh ${D}${sysconfdir}/initscripts
		install -m 0755 ${WORKDIR}/config.ini ${D}${sysconfdir}/initscripts
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/start_eth-adaption-layer_le ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/emac.sh ${D}${sysconfdir}/init.d
		install -m 0755 ${WORKDIR}/config.ini ${D}${sysconfdir}/init.d
    fi
}
do_install:append() {
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/

if ${@bb.utils.contains_any('MACHINE', 'qtiquingvm-tele qtiquingvm-tele-lxc', 'true', 'false', d)}; then
   #Emac Service
   install -m 0644 ${WORKDIR}/emac_lv.service -D ${D}${systemd_unitdir}/system/emac_lv.service
   # enable the service for multi-user.target
   ln -sf ${systemd_unitdir}/system/emac_lv.service \
   ${D}${systemd_unitdir}/system/multi-user.target.wants/emac_lv.service

   install -m 0644 ${WORKDIR}/eth-adaption-layer_lv.service -D ${D}${systemd_unitdir}/system/eth-adaption-layer_lv.service
   # enable the service for multi-user.target
   ln -sf ${systemd_unitdir}/system/eth-adaption-layer_lv.service \
   ${D}${systemd_unitdir}/system/multi-user.target.wants/eth-adaption-layer_lv.service
else
   #Emac Service
   install -m 0644 ${WORKDIR}/emac.service -D ${D}${systemd_unitdir}/system/emac.service
   # enable the service for multi-user.target
   ln -sf ${systemd_unitdir}/system/emac.service \
   ${D}${systemd_unitdir}/system/multi-user.target.wants/emac.service

   install -m 0644 ${WORKDIR}/eth-adaption-layer.service -D ${D}${systemd_unitdir}/system/eth-adaption-layer.service
   # enable the service for multi-user.target
   ln -sf ${systemd_unitdir}/system/eth-adaption-layer.service \
   ${D}${systemd_unitdir}/system/multi-user.target.wants/eth-adaption-layer.service
fi
fi
}