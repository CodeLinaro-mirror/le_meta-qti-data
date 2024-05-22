inherit module qperf

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "rmnetbam driver"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

DEPENDS = "virtual/kernel"

FILESPATH =+ "${WORKSPACE}/data-kernel/drivers:"
SRC_URI = "file://msm/"
SRC_URI += "file://rmnetbam.service"

S = "${WORKDIR}/msm"

FILES:${PN}+="${base_libdir}/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+= "${systemd_unitdir}/system/rmnetbam.service"
FILES:${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetbam.service"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

EXTRA_OECONF += "--with-kernel-src=${STAGING_KERNEL_DIR} \
                 --with-kernel=${STAGING_KERNEL_BUILDDIR} \
                 --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

EXT_MODULES = "${@os.path.relpath("${S}","${KERNEL_PLATFORM_PATH}")}"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_compile() {
    cd ${KERNEL_PLATFORM_PATH}
    ENABLE_DDK_BUILD=${ENABLE_DDK_BUILD} \
    KBUILD_OPTIONS+="TARGET_SUPPORT=${BASEMACHINE}" \
    TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=${EXT_MODULES} \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/msm \
    KERNEL_KIT=${KERNEL_PREBUILT_PATH} \
    OUT_DIR=${KERNEL_OUT_PATH} \
    ./build/build_module.sh
}

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
        install -m 0644 ${S}/rmnet_bam.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/rmnet_bam.ko
        install -d ${D}${systemd_unitdir}/system/
        install -m 0644 ${WORKDIR}/rmnetbam.service -D ${D}${systemd_unitdir}/system/rmnetbam.service
        install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
        ln -sf ${systemd_unitdir}/system/rmnetbam.service \
                ${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetbam.service
}

# The inherit of module.bbclass will automatically name module packages with
# kernel-module-" prefix as required by the oe-core build environment. Also it
# replaces '_' with '-' in the module name.

RPROVIDES:${PN} += "${@'kernel-module-rmnet-bam-${KERNEL_VERSION}'.replace('_', '-')}"

