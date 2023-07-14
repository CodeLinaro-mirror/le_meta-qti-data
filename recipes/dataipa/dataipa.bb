inherit module useradd

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
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install() {
   install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
   install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/ipa

   strip_tool="${STAGING_DIR_NATIVE}/usr/libexec/aarch64-oe-linux/gcc/aarch64-oe-linux/9.3.0/strip"
   module_path="${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm"
   module_signer="${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
                  ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509"

   module_list="gsi/gsim.ko ipa/ipam.ko ipa/ipanetm.ko ipa/ipa_clients/rndisipam.ko ipa/ipa_clients/ecmipam.ko ipa/ipa_clients/ipa_clientsm.ko"
   for module in ${module_list}; do
     # Copy the modules that contain debug symbols to the deploy directory
     cp ${module_path}/${module} ${DEPLOY_DIR_IMAGE}/kernel_modules/ipa

     # Strip the debug symbols and sign the modules for target
     ${strip_tool} --strip-debug ${module_path}/${module}
     ${module_signer} ${module_path}/${module}
     install -m 0644  ${module_path}/${module} ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/
   done

   install -d ${D}${sysconfdir}/initscripts/
   install -m 0755 ${WORKDIR}/start_dataipa_le ${D}${sysconfdir}/initscripts/start_dataipa_le
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dataipa.service -D ${D}${systemd_unitdir}/system/dataipa.service
   install -d ${D}${sysconfdir}/udev/rules.d/
   install -m 0444 ${WORKDIR}/dataipa.rules ${D}${sysconfdir}/udev/rules.d/dataipa.rules
   install -d ${D}${sysconfdir}/udev/scripts/
   install -m 0755 ${WORKDIR}/dataipa_udev.sh ${D}${sysconfdir}/udev/scripts/dataipa_udev.sh
   install -d ${D}${sysconfdir}/data/
   install -m 0755 -o 1001 -g 1001 ${WORKDIR}/ipa_config.txt -D ${D}${sysconfdir}/data/ipa_config.txt
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

