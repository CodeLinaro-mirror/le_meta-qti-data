inherit linux-kernel-base deploy

DESCRIPTION = "QCA NSS PHY driver"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/ISC;md5=f3b90e78ea0cffb20bf5cca7947a896d"

DEPENDS = "virtual/kernel linux-msm-headers rsync-native"

FILESPATH:prepend := "${WORKSPACE}:"
SRC_URI:append:kera = " file://data-eth/drivers/qca-nss-phy/linux_std/qca81xx/"

KERNEL_VERSION = "${@get_kernelversion_file('${STAGING_KERNEL_BUILDDIR}')}"
S = "${WORKDIR}/src/data-eth/drivers/qca-nss-phy/linux_std/qca81xx"

MODULE_OUT_DIR = "${WORKDIR}/../src/data-eth-modules-out"
KO_PATH = "${MODULE_OUT_DIR}/drivers/qca-nss-phy/linux_std/qca81xx/qca81xx-phy.ko"

KERNEL_MODULE_AUTOLOAD:${PN} += "qca81xx_phy"

do_compile:kera() {
    cd ${KERNEL_PLATFORM_PATH}

    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_QCA_NSS_PHY=m \
    EXT_MODULES=../../data-eth \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${MODULE_OUT_DIR} \
    OUT_DIR=${KERNEL_OUT_PATH} \
    TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
    ./build/build_module.sh

}

export LD_LIBRARY_PATH = "${KERNEL_OUT_PATH}dist"

do_install:kera() {
    ${STRIP} --strip-debug ${KO_PATH}

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 ${KO_PATH} ${D}${base_libdir}/modules/${KERNEL_VERSION}/qca81xx-phy.ko
    install -d ${D}${sysconfdir}/modules-load.d
    echo "qca81xx_phy" > ${D}${sysconfdir}/modules-load.d/qca81xx_phy.conf
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules

    if [ -f ${KO_PATH} ]; then
        cp -p ${KO_PATH} ${DEPLOYDIR}/kernel_modules/
    else
        bbfatal "qca81xx-phy.ko not found for deploy at ${KO_PATH}"
    fi
}

addtask do_deploy after do_install before do_package

FILES:${PN} += " \
    ${base_libdir}/modules/${KERNEL_VERSION}/* \
    ${sysconfdir}/modules-load.d/qca81xx_phy.conf \
"
