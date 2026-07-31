inherit linux-kernel-base deploy

DESCRIPTION = "QCA NSS PHY driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/ISC;md5=f3b90e78ea0cffb20bf5cca7947a896d"

DEPENDS = "virtual/kernel linux-msm-headers rsync-native"

FILESPATH:prepend := "${WORKSPACE}:"
SRC_URI:append:kera = " file://data-eth/drivers/qca-nss-phy/linux_std/qca81xx/"

KERNEL_VERSION = "${@get_kernelversion_file('${STAGING_KERNEL_BUILDDIR}')}"
S = "${WORKDIR}/data-eth/drivers/qca-nss-phy/linux_std/qca81xx"
MODULE_OUT_DIR = "${WORKDIR}/data-eth-nss-phy-out"
KO_NAME = "qca81xx-phy.ko"

do_configure[noexec] = "1"
do_compile[lockfiles] = "${TMPDIR}/kernel-ext-module.lock"

do_compile:kera() {
    mkdir -p ${MODULE_OUT_DIR}

    cd ${KERNEL_PLATFORM_PATH}

    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_QCA_NSS_PHY=m \
    EXT_MODULES=../../data-eth \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${MODULE_OUT_DIR} \
    OUT_DIR=${KERNEL_OUT_PATH} \
    VARIANT=${KERNEL_DEFCONFIG_VARIANT} \
    TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
    ./build/build_module.sh
}

export LD_LIBRARY_PATH="${KERNEL_OUT_PATH}/dist"

do_install:kera() {
    REAL_KO=$(find ${MODULE_OUT_DIR} -name "${KO_NAME}" 2>/dev/null | head -n 1)

    [ -z "$REAL_KO" ] && bbfatal "${KO_NAME} not found in ${MODULE_OUT_DIR}"

    ${STRIP} --strip-debug "$REAL_KO"

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 "$REAL_KO" ${D}${base_libdir}/modules/${KERNEL_VERSION}/${KO_NAME}

    install -d ${D}${sysconfdir}/modules-load.d
    echo "qca81xx_phy" > ${D}${sysconfdir}/modules-load.d/qca81xx_phy.conf
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules

    REAL_KO=$(find ${MODULE_OUT_DIR} -name "${KO_NAME}" 2>/dev/null | head -n 1)
    [ -z "$REAL_KO" ] && bbfatal "${KO_NAME} not found for deploy"

    cp -p "$REAL_KO" ${DEPLOYDIR}/kernel_modules/
}

addtask do_deploy after do_install before do_package

FILES:${PN} += " \
    ${base_libdir}/modules/${KERNEL_VERSION}/* \
    ${sysconfdir}/modules-load.d/qca81xx_phy.conf \
    "

COMPATIBLE_MACHINE = "kera"
