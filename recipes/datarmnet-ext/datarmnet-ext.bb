DESCRIPTION = "Datarmnet-ext drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"


# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
#inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

EXT_MODULES = "${@os.path.relpath("${S}", "${KERNEL_PLATFORM_PATH}")}"
KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends]   += "virtual/kernel:do_shared_workdir"

DEPENDS = "virtual/kernel linux-kernel-qcom-headers"

inherit pkgconfig autotools deploy module linux-kernel-base

PR = "r0"

SRC_URI = "file://datarmnet-ext/sch/"
SRC_URI += "file://sa535m/"

S = "${UNPACKDIR}/datarmnet-ext/sch"

FILESPATH =+ "${WORKSPACE}:"

do_compile() {
    cd ${S}
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
}

do_install() {
    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
    install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/datarmnet-ext

    # Copy the modules that contain debug symbols to the deploy directory
    cp ${UNPACKDIR}/datarmnet-ext/sch/rmnet_sch.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/datarmnet-ext

    # Strip debug symbols
    ${STRIP} --strip-debug \
    ${UNPACKDIR}/datarmnet-ext/sch/rmnet_sch.ko

    #Signing and installing the datarmnet-ext module
    LD_LIBRARY_PATH=${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${UNPACKDIR}/datarmnet-ext/sch/rmnet_sch.ko
    install -m 0755 ${UNPACKDIR}/datarmnet-ext/sch/rmnet_sch.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rmnet_sch.ko

    #Install startup scripts
    install -d ${D}${sysconfdir}/initscripts/
    install -m 0755 ${UNPACKDIR}/sa535m/start_rmnetsch_le ${D}${sysconfdir}/initscripts/start_rmnetsch_le
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${UNPACKDIR}/sa535m/rmnetsch.service -D ${D}${systemd_unitdir}/system/rmnetsch.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/rmnetsch.service \
           ${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetsch.service
}

FILES:${PN}+="${libdir}/modules/*"
FILES:${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="/etc/initscripts/start_rmnetsch_le"
FILES:${PN}+= "${systemd_unitdir}/system/rmnetsch.service"
FILES:${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetsch.service"

RPROVIDES:${PN} += "kernel-module-*-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-rmnet-sch-${KERNEL_VERSION}"

do_configure[noexec] = "1"

# Yocto 6.0 QA: this external DLKM embeds kernel build TMPDIR paths in
# rmnet_sch.ko debug/build metadata even after strip, so skip buildpaths QA.
INSANE_SKIP:${PN} += "buildpaths"
