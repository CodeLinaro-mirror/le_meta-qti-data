inherit module

SUMMARY = "IPA driver"

DESCRIPTION = "Contains IPA driver"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "virtual/kernel"
DEPENDS += "data-devicetree"

PR = "r0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://dataipa/"
SRC_URI += "file://start_dataipa_le"
SRC_URI += "file://dataipa.service"
SRC_URI += "file://ipa_config.txt"

S = "${WORKDIR}/src/dataipa"

do_compile() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ENABLE_DDK_BUILD=true \
    TARGET_BOARD_PLATFORM=sa510m \
    ./build/build_module.sh
}

export LD_LIBRARY_PATH = "${KERNEL_OUT_PATH}dist/openssl/lib64/"
do_install() {
   install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
   install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/ipa

   strip_tool="${STRIP}"
   module_path="${WORKDIR}/dataipa"
   module_signer="${KERNEL_OUT_PATH}/dist/sign-file sha1 ${KERNEL_OUT_PATH}/dist/signing_key.pem \
                  ${KERNEL_OUT_PATH}/dist/signing_key.x509"

   module_list="gsim.ko ipam.ko ipanetm.ko"
   for module in ${module_list}; do
     # Copy the modules that contain debug symbols to the deploy directory
     cp ${WORKDIR}/src/dataipa-modules-out/${module} ${DEPLOY_DIR_IMAGE}/kernel_modules/ipa

     # Strip the debug symbols and sign the modules for target
     ${strip_tool} --strip-debug ${WORKDIR}/src/dataipa-modules-out/${module}
     ${module_signer} ${WORKDIR}/src/dataipa-modules-out/${module}
     install -m 0644  ${WORKDIR}/src/dataipa-modules-out/${module} ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/
   done

   install -d ${D}${sysconfdir}/initscripts/
   install -m 0555 ${WORKDIR}/start_dataipa_le ${D}${sysconfdir}/initscripts/start_dataipa_le
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dataipa.service -D ${D}${systemd_unitdir}/system/dataipa.service
   install -d ${D}${sysconfdir}/data/
   install -m 0644 ${WORKDIR}/ipa_config.txt -D ${D}${sysconfdir}/data/ipa_config.txt
   install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
   install -d ${STAGING_DIR}/usr/include/linux
   cp ${module_path}/drivers/platform/msm/include/uapi/linux/msm_ipa.h ${STAGING_DIR}/usr/include/linux
   cp ${module_path}/drivers/platform/msm/include/uapi/linux/ipa_qmi_service_v01.h ${STAGING_DIR}/usr/include/linux
   cp ${module_path}/drivers/platform/msm/include/uapi/linux/rmnet_ipa_fd_ioctl.h ${STAGING_DIR}/usr/include/linux
   ln -sf ${systemd_unitdir}/system/dataipa.service \
          ${D}${systemd_unitdir}/system/local-fs.target.wants/dataipa.service
}

pkg_postinst:${PN}(){
    chown -Rh 1001:1001 $D${sysconfdir}/data/ipa_config.txt
}

FILES:${PN}+="${libdir}/modules/*"
FILES:${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="${sysconfdir}/initscripts/start_dataipa_le"
FILES:${PN}+="${systemd_unitdir}/system/dataipa.service"
FILES:${PN}+="${sysconfdir}/data/ipa_config.txt"
FILES:${PN}+="${systemd_unitdir}/system/local-fs.target.wants/dataipa.service"

RPROVIDES:${PN} += "kernel-module-gsim-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ipam-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ipanetm-${KERNEL_VERSION}"
