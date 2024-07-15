DESCRIPTION = "Datarmnet drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"


# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DEPENDS = "virtual/kernel"

PR = "r0"

SRC_URI = "file://datarmnet/core/"
SRC_URI += "file://start_rmnetcore_le"
SRC_URI += "file://rmnetcore.service"

S = "${WORKDIR}/datarmnet/core"

inherit pkgconfig module

FILESPATH =+ "${WORKSPACE}:"

do_compile() {
	cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
	BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
	EXT_MODULES=../../datarmnet/core \
	ROOTDIR=${WORKSPACE}/ \
	MODULE_OUT=${WORKDIR}/datarmnet/core-out \
	OUT_DIR=${KERNEL_OUT_PATH}/ \
	./build/build_module.sh
}

do_install() {
    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
    install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/datarmnet

    # Copy the modules that contain debug symbols to the deploy directory
    cp ${WORKDIR}/datarmnet/core-out/rmnet_core.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/datarmnet

    # Strip debug symbols
    ${STRIP} --strip-debug \
    ${WORKDIR}/datarmnet/core-out/rmnet_core.ko

    #Signing and installing the datarmnet module
    LD_LIBRARY_PATH=${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko
    install -m 0755 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rmnet_core.ko

    #Install startup scripts
    install -d ${D}${sysconfdir}/initscripts/
    install -m 0555 ${WORKDIR}/start_rmnetcore_le ${D}${sysconfdir}/initscripts/start_rmnetcore_le
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/rmnetcore.service -D ${D}${systemd_unitdir}/system/rmnetcore.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/rmnetcore.service \
           ${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service
}

FILES:${PN}+="${libdir}/modules/*"
FILES:${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="/etc/initscripts/start_rmnetcore_le"
FILES:${PN}+= "${systemd_unitdir}/system/rmnetcore.service"
FILES:${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service"
RPROVIDES:${PN} += "datarmnet"

