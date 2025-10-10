DESCRIPTION = "Datarmnet drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit pkgconfig autotools deploy module linux-kernel-base

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
#inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

EXT_MODULES = "${@os.path.relpath("${S}", "${KERNEL_PLATFORM_PATH}")}"
KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends]   += "virtual/kernel:do_shared_workdir"

DEPENDS = "virtual/kernel linux-kernel-qcom-headers dataipa"

PR = "r0"

SRC_URI = "file://datarmnet/core/"
SRC_URI += "file://sa535m/"

S = "${WORKDIR}/datarmnet/core"

FILESPATH =+ "${WORKSPACE}:"

#Include directories for ipa header files that we need to pass explicitly
EXTRA_CFLAGS+= "-I${WORKDIR}/recipe-sysroot/usr/include/ipa -I${WORKDIR}/recipe-sysroot/usr/include/ipa/uapi"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

# This is exactly the same as module.bbclass with the addition of EXTRA_CFLAGS
do_compile() {
    cd ${S}
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules \
               KERNEL_PATH=${STAGING_KERNEL_DIR} \
               KERNEL_VERSION=${KERNEL_VERSION} \
               CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
               AR="${KERNEL_AR}" OBJCOPY="${KERNEL_OBJCOPY}" \
               STRIP="${KERNEL_STRIP}" \
               O=${STAGING_KERNEL_BUILDDIR} \
               KBUILD_EXTRA_SYMBOLS="${KBUILD_EXTRA_SYMBOLS}" \
               EXTRA_CFLAGS="${EXTRA_CFLAGS}" ${EXTRA_OEMAKE} \
               ${MAKE_TARGETS}
}

do_install() {

    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
    install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/datarmnet

    # Copy the modules that contain debug symbols to the deploy directory
    cp ${WORKDIR}/datarmnet/core/rmnet_core.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/datarmnet

    # Strip debug symbols
    ${STRIP} --strip-debug \
    ${WORKDIR}/datarmnet/core/rmnet_core.ko

    #Signing and installing the datarmnet module
    LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/datarmnet/core/rmnet_core.ko
    install -m 0755 ${WORKDIR}/datarmnet/core/rmnet_core.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rmnet_core.ko

    #Install startup scripts
    install -d ${D}${sysconfdir}/initscripts/
    install -m 0555 ${WORKDIR}/sa535m/start_rmnetcore_le ${D}${sysconfdir}/initscripts/start_rmnetcore_le
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/sa535m/rmnetcore.service -D ${D}${systemd_unitdir}/system/rmnetcore.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/rmnetcore.service \
           ${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service
}

FILES:${PN}+="${libdir}/modules/*"
FILES:${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="/etc/initscripts/start_rmnetcore_le"
FILES:${PN}+= "${systemd_unitdir}/system/rmnetcore.service"
FILES:${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service"

RPROVIDES:${PN} += "kernel-module-*-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-rmnet-core-${KERNEL_VERSION}"
