inherit autotools-brokensep module
DESCRIPTION = "Code Aurora TCP Splice"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

FILESEXTRAPATHS:prepend := "${WORKSPACE}/:"
SRC_URI = "file://tcp-splice/"

S = "${WORKDIR}/tcp-splice/"

do_install() {
    module_signer="${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
                   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509"
    ${module_signer} ${S}tcp_splice.ko
    install -d ${D}${nonarch_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
    install -m 0644 ${S}tcp_splice.ko ${D}${nonarch_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net
}

FILES:${PN} = "${nonarch_libdir}/modules/*"
