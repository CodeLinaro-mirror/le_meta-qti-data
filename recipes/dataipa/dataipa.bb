inherit linux-kernel-base deploy
SUMMARY = "Building IPA Driver Modules"

DESCRIPTION = "Contains IPA Driver"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "virtual/kernel linux-msm-headers"

PR = "r0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI += "file://dataipa/ \
	    file://dataipa-load.conf"

# Needed for IOT upgrade
SRC_URI += " file://dataipa-load_le.conf"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
S = "${WORKDIR}/src/dataipa"

do_compile:kalama() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_KALAMA_IPA_LE=y \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:kalama() {
   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/dataipa-load.conf -D ${D}${sysconfdir}/modules-load.d/dataipa-load.conf
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
}

do_compile:qcm2290-mtp() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_SCUBA_IPA_LE=y \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:qcm2290-mtp() {
   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/dataipa-load.conf -D ${D}${sysconfdir}/modules-load.d/dataipa-load.conf
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
}

do_compile:qcm4325-mtp() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_SCUBA_IPA_LE=y \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:qcm4325-mtp() {
   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/dataipa-load.conf -D ${D}${sysconfdir}/modules-load.d/dataipa-load.conf
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
}

do_install:qcm4325-mtp-32() {
   install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/dataipa-load.conf -D ${D}${sysconfdir}/modules-load.d/dataipa-load.conf
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko -D ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
}

do_compile:kera() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_KERA_LE=y \
    EXT_MODULES=../../dataipa \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/dataipa-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:kera() {
   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/dataipa-load.conf -D ${D}${sysconfdir}/modules-load.d/dataipa-load_le.conf
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -d ${STAGING_DIR}/usr/include/linux
   cp ${WORKDIR}/dataipa/drivers/platform/msm/include/uapi/linux/msm_ipa.h ${STAGING_DIR}/usr/include/linux
   cp ${WORKDIR}/dataipa/drivers/platform/msm/include/uapi/linux/ipa_qmi_service_v01.h ${STAGING_DIR}/usr/include/linux
   cp ${WORKDIR}/dataipa/drivers/platform/msm/include/uapi/linux/rmnet_ipa_fd_ioctl.h ${STAGING_DIR}/usr/include/linux
   cp ${WORKDIR}/dataipa/drivers/platform/msm/include/linux/ipa.h ${STAGING_DIR}/usr/include/linux
   cp ${WORKDIR}/dataipa/drivers/platform/msm/include/linux/msm_gsi.h ${STAGING_DIR}/usr/include/linux
}

do_deploy:kera() {
    install -d ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko ${DEPLOYDIR}/kernel_modules
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/gsi/gsim.ko ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipam.ko ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipanetm.ko ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/dataipa-modules-out/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko ${DEPLOYDIR}/kernel_modules
}
addtask do_deploy after do_install before do_package

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN}:qcm4325-mtp-32 += "${sysconfdir}/*"
FILES:${PN}:qcm4325-mtp-32 += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/*"
