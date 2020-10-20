inherit module qperf

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "Rmnet core driver"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

DEPENDS = "virtual/kernel"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://datarmnet/core/"

S = "${WORKDIR}/datarmnet/core"

FILES_${PN}+="/etc/initscripts/start_rmnetcore_le"
FILES_${PN}+= "${systemd_unitdir}/system/rmnetcore.service"
FILES_${PN}+= "${systemd_unitdir}/system/multi-user.target.wants/rmnetcore.service"

INITSCRIPT_NAME = "start_rmnetcore_le"
INITSCRIPT_PARAMS = "start 35 5 . stop 15 0 1 6 ."

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

EXTRA_OECONF += "--with-kernel-src=${STAGING_KERNEL_DIR} \
                 --with-kernel=${STAGING_KERNEL_BUILDDIR} \
                 --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"

#currently not working but for some reason this compiles but do_install_append doesnt
do_install_append_mdm() {
        install -d ${D}${sysconfdir}/initscripts
        install -m 0755 ${WORKDIR}/${MACHINE}/start_rmnet_core_le ${D}${sysconfdir}/initscripts
        install -m 0644 ${WORKDIR}/${MACHINE}/rmnet_core.service -D ${D}${systemd_unitdir}/system/rmnet_core.service
}
