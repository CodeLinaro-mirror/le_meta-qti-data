inherit module qperf
#inherit ${@bb.utils.contains_any('MACHINE', 'sa515m', 'module qperf', '', d)}

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
#inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "Datarmnet drivers"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

DEPENDS = "virtual/kernel"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://datarmnet/core/"
SRC_URI += "file://start_rmnetcore_le"
SRC_URI += "file://rmnetcore.service"

S = "${WORKDIR}/datarmnet/core"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

EXTRA_OECONF += "--with-kernel-src=${STAGING_KERNEL_DIR} \
                 --with-kernel=${STAGING_KERNEL_BUILDDIR} \
                 --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

#currently not working but for some reason this compiles but do_install_append doesnt
do_install_append_sa515m() {
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

#compilation steps for SA410  with kernel v5.15
do_compile_sa410m() {
   cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../datarmnet/core \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/datarmnet/core-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install_sa410m() {
    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
    #Signing and installing the datarmnet module
    LD_LIBRARY_PATH=${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko
    install -m 0755 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rmnet_core.ko
    install -d ${D}${sysconfdir}/initscripts/
    install -m 0755 ${WORKDIR}/start_rmnetcore_le ${D}${sysconfdir}/initscripts/start_rmnetcore_le
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/rmnetcore.service -D ${D}${systemd_unitdir}/system/rmnetcore.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/rmnetcore.service \
	${D}${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service
}

FILES_${PN}+="${libdir}/modules/*"
FILES_${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES_${PN}+="/etc/initscripts/start_rmnetcore_le"
FILES_${PN}+= "${systemd_unitdir}/system/rmnetcore.service"
FILES_${PN}+= "${systemd_unitdir}/system/local-fs.target.wants/rmnetcore.service"
RPROVIDES_${PN} += "datarmnet"
