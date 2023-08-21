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
SRC_URI += "file://dataipa.rules"
SRC_URI += "file://dataipa_udev.sh"
SRC_URI += "file://ipa_config.txt"

S = "${WORKDIR}/src/dataipa"

do_compile() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_PREBUILT_PATH}/ \
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
   install -d ${D}${sysconfdir}/udev/rules.d/
   install -m 0777 ${WORKDIR}/dataipa.rules ${D}${sysconfdir}/udev/rules.d/dataipa.rules
   install -d ${D}${sysconfdir}/udev/scripts/
   install -m 0777 ${WORKDIR}/dataipa_udev.sh ${D}${sysconfdir}/udev/scripts/dataipa_udev.sh
   install -d ${D}${sysconfdir}/data/
   install -m 0755 ${WORKDIR}/ipa_config.txt -D ${D}${sysconfdir}/data/ipa_config.txt
   install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
   ln -sf ${systemd_unitdir}/system/dataipa.service \
          ${D}${systemd_unitdir}/system/local-fs.target.wants/dataipa.service
}

FILES_${PN}+="${libdir}/modules/*"
FILES_${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES_${PN}+="${sysconfdir}/initscripts/start_dataipa_le"
FILES_${PN}+="${systemd_unitdir}/system/dataipa.service"
FILES_${PN}+="${sysconfdir}/udev/rules.d/dataipa.rules"
FILES_${PN}+="${sysconfdir}/udev/scripts/dataipa_udev.sh"
FILES_${PN}+="${sysconfdir}/data/ipa_config.txt"
FILES_${PN}+="${systemd_unitdir}/system/local-fs.target.wants/dataipa.service"

