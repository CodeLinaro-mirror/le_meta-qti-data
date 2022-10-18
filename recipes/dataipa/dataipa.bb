inherit module

SUMMARY = "IPA driver"

DESCRIPTION = "Contains IPA driver"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "virtual/kernel"

PR = "r0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://src/dataipa/"
SRC_URI += "file://start_dataipa_le"
SRC_URI += "file://dataipa.service"

S = "${WORKDIR}/src/dataipa"

do_compile() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}


do_install() {
   install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
   ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko
   ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko
   ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko
   ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko
   ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ecmipam.ko
   ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
   ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/gsim.ko
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ipam.ko
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ipanetm.ko
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/rndisipam.ko
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ecmipam.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ecmipam.ko
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ipa_clientsm.ko
   install -d ${D}${sysconfdir}/initscripts/
   install -m 0755 ${WORKDIR}/start_dataipa_le ${D}${sysconfdir}/initscripts/start_dataipa_le
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dataipa.service -D ${D}${systemd_unitdir}/system/dataipa.service
   install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
   ln -sf ${systemd_unitdir}/system/dataipa.service \
          ${D}${systemd_unitdir}/system/local-fs.target.wants/dataipa.service
}

FILES_${PN}+="${libdir}/modules/*"
FILES_${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES_${PN}+="${sysconfdir}/initscripts/start_dataipa_le"
FILES_${PN}+="${systemd_unitdir}/system/dataipa.service"
FILES_${PN}+="${systemd_unitdir}/system/local-fs.target.wants/dataipa.service"

