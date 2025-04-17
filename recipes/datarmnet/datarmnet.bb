inherit module qperf

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "Datarmnet drivers"
LICENSE = "GPL-2.0-only "
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

DEPENDS = "virtual/kernel"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://datarmnet/core"
SRC_URI += "file://start_rmnetcore_le"
SRC_URI += "file://rmnetcore.service"

S = "${WORKDIR}/datarmnet/core"

FILES_${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/"
FILES_${PN}+="/etc/initscripts/start_rmnetcore_le"
FILES_${PN}+= "${systemd_unitdir}/system/rmnetcore.service"
FILES_${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service"

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
do_install_append() {
        install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
        install -m 0644 rmnet_core.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rmnet_core.ko
        install -d ${D}${sysconfdir}/initscripts/
        install -m 0755 ${WORKDIR}/start_rmnetcore_le ${D}${sysconfdir}/initscripts/start_rmnetcore_le
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/rmnetcore.service -D ${D}${systemd_unitdir}/system/rmnetcore.service
        install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
        ln -sf ${systemd_unitdir}/system/rmnetcore.service \
                ${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service
}
