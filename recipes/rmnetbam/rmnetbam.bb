inherit module qperf

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "rmnetbam driver"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

DEPENDS = "virtual/kernel"

FILESPATH =+ "${WORKSPACE}/data-kernel/drivers:"
SRC_URI = "file://msm/"
SRC_URI += "file://rmnetbam.service"

S = "${WORKDIR}/msm"

FILES_${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/"
FILES_${PN}+= "${systemd_unitdir}/system/rmnetbam.service"
FILES_${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetbam.service"

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
        install -m 0644 rmnet_bam.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rmnet_bam.ko
	${STAGING_DIR_NATIVE}/usr/libexec/arm-oe-linux-gnueabi/gcc/arm-oe-linux-gnueabi/9.3.0/strip --strip-debug ${D}/lib/modules/${KERNEL_VERSION}/extra/rmnet_bam.ko
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/rmnetbam.service -D ${D}${systemd_unitdir}/system/rmnetbam.service
        install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
        ln -sf ${systemd_unitdir}/system/rmnetbam.service \
                ${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetbam.service
}
