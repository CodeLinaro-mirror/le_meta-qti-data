inherit linux-kernel-base deploy logging

inherit systemd

DESCRIPTION = "TOSHIBA QPS615 driver"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

RRECOMMENDS:${PN} += "qps615-firmware"
DEPENDS = "virtual/kernel linux-msm-headers"

FILESPATH   =+ "${WORKSPACE}:"
SRC_URI     =  "file://src/data-eth/drivers/qps615/src/"
SRC_URI += "file://qps615.service"

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

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/src/data-eth-modules-out/tc956x_pcie_eth.ko ${DEPLOYDIR}/kernel_modules
}
addtask do_deploy after do_install before do_package

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN}+="${systemd_unitdir}/system/qps615.service"
FILES:${PN}+="${systemd_unitdir}/system/multi-user.target.wants/qps615.service"

