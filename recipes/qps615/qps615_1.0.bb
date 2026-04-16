inherit linux-kernel-base deploy

inherit systemd

DESCRIPTION = "TOSHIBA QPS615 driver"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

RRECOMMENDS:${PN} += "qps615-firmware"

DEPENDS = "virtual/kernel linux-msm-headers ${@bb.utils.contains('DDK_BUILD', 'true', '', 'rsync-native', d)}"

SRC_URI += "file://qps615.service"

FILESPATH:prepend := "${WORKSPACE}:"
SRC_URI:append:kalama = " file://src/data-eth/drivers/qps615/src/"
SRC_URI:append:kera   = " file://data-eth/drivers/qps615/src/"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
S = "${WORKDIR}/src/data-eth/drivers/qps615/src"

do_compile:kalama() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../data-eth/drivers/qps615/src \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/src/data-eth-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:kalama() {
   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/src/data-eth-modules-out/tc956x_pcie_eth.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -d ${D}${systemd_unitdir}/system/
   install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
   install -m 0644 ${WORKDIR}/qps615.service -D ${D}${systemd_unitdir}/system/qps615.service
   ln -sf -r ${D}${systemd_unitdir}/system/qps615.service \
	${D}${systemd_unitdir}/system/multi-user.target.wants/qps615.service
}

do_compile:kera() {
    cd ${KERNEL_PLATFORM_PATH}
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    CONFIG_QPS615=y \
    EXT_MODULES=../../data-eth \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/../src/data-eth-modules-out \
    OUT_DIR=${KERNEL_OUT_PATH} \
    VARIANT=${KERNEL_DEFCONFIG_VARIANT} \
    TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
    ./build/build_module.sh
}
export LD_LIBRARY_PATH = "${KERNEL_OUT_PATH}dist"

do_install:kera() {
   # Strip debug symbols
   ${STRIP} --strip-debug ${WORKDIR}/../src/data-eth-modules-out/drivers/qps615/src/tc956x_pcie_eth.ko

   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/../src/data-eth-modules-out/drivers/qps615/src/tc956x_pcie_eth.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}/tc956x_pcie_eth.ko
   install -d ${D}${systemd_unitdir}/system
   install -d ${D}${systemd_unitdir}/system/multi-user.target.wants
   install -m 0644 ${WORKDIR}/qps615.service -D ${D}${systemd_unitdir}/system/qps615.service
   ln -sf -r ${D}${systemd_unitdir}/system/qps615.service \
	${D}${systemd_unitdir}/system/multi-user.target.wants/qps615.service
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules
    if [ -f ${WORKDIR}/src/data-eth-modules-out/tc956x_pcie_eth.ko ]; then
        cp -rp ${WORKDIR}/src/data-eth-modules-out/tc956x_pcie_eth.ko ${DEPLOYDIR}/kernel_modules
    fi

    if [ -f ${WORKDIR}/data-eth-modules-out/drivers/qps615/src/tc956x_pcie_eth.ko ]; then
        cp -rp ${WORKDIR}/data-eth-modules-out/drivers/qps615/src/tc956x_pcie_eth.ko ${DEPLOYDIR}/kernel_modules
    fi
}
addtask do_deploy after do_install before do_package

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN}+="${systemd_unitdir}/system/qps615.service"
FILES:${PN}+="${systemd_unitdir}/system/multi-user.target.wants/qps615.service"

