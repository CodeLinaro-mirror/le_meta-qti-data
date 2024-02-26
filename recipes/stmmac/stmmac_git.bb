inherit module ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "Stmmac"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

FILES:${PN}     += "${sysconfdir}/init.d/*"
FILES:${PN}     += "${systemd_unitdir}/system/*"
FILES:${PN}     += "${sysconfdir}/initscripts/*"

do_unpack[deptask] = "do_populate_sysroot"
PR = "r0"

SRC_URI = "file://setup_avtp_routing_le"
SRC_URI += "file://setup_avtp_routing_le.service"

do_compile() {
}

do_install() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/initscripts
        install -m 0755 ${WORKDIR}/setup_avtp_routing_le ${D}${sysconfdir}/initscripts
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/setup_avtp_routing_le ${D}${sysconfdir}/init.d
    fi
}

do_install:append() {
if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
   install -d ${D}${systemd_unitdir}/system/
   install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/

   #setup_avtp_routing_le script
   install -m 0644 ${WORKDIR}/setup_avtp_routing_le.service -D ${D}${systemd_unitdir}/system/setup_avtp_routing_le.service
   # enable the service for multi-user.target
   ln -sf ${systemd_unitdir}/system/setup_avtp_routing_le.service \
   ${D}${systemd_unitdir}/system/multi-user.target.wants/setup_avtp_routing_le.service
fi
}

pkg_postinst:${PN} () {
   if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'false', 'true', d)}; then
    [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        update-rc.d $OPT -f setup_avtp_routing_le remove
        update-rc.d $OPT setup_avtp_routing_le start 91 S . stop 9 0 1 6 .
    fi
}