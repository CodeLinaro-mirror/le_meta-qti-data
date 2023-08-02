inherit autotools-brokensep module deploy
DESCRIPTION = "Generic Software Bridge Driver"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

FILESPATH =+ "${WORKSPACE}/data-kernel/drivers:"
SRC_URI = "file://generic-sw-bridge"
S = "${WORKDIR}/generic-sw-bridge/"

do_install() {
    # Copy unsigned kernel modules into image specific deploy directory.
    install -d ${DEPLOYDIR}/kernel_modules/${PN}
    install -m 0644 ${S}/gsb.ko ${DEPLOYDIR}/kernel_modules/${PN}/gsb.ko

    # Strip debug symbols
    ${STAGING_DIR_NATIVE}/usr/libexec/aarch64-oe-linux/gcc/aarch64-oe-linux/9.3.0/strip --strip-debug \
    ${S}/gsb.ko

    module_do_install
}
