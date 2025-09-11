SUMMARY = "Data Ethernet Drivers"
DESCRIPTION = "Helper recipe to build Data Ethernet drivers out-of-tree or in devshell"
PACKAGE_ARCH = "${MACHINE_ARCH}"
export ETH_SRCDIR = "${WORKSPACE}/data-eth"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${ETH_SRCDIR}/data-eth.c;\
beginline=1;endline=4;md5=35144d93ffd061a7458db62d36405265"

inherit module
inherit qperf
inherit systemd

DEPENDS += "dataipa"

# Files from meta-qti-data
SRC_URI += "file://emac_ioss.service"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI += "file://data-eth"
S = "${WORKDIR}/data-eth"
TARGET_VARIANT= "${@bb.utils.contains('KERNEL_VARIANT', 'perf_', 'perf_defconfig', 'debug_defconfig', d)}"

# Default to sa510m; override per MACHINE or in local.conf as needed.
TARGET_BOARD_PLATFORM ?= "sa510m"

# If your MACHINE is named 'sa510m-1g', this maps the platform string to 'sa510m.1g'
TARGET_BOARD_PLATFORM:sa510m-1g = "sa510m.1g"

# Ensure artifacts are machine-specific (kernel modules depend on kernel/machine)
# (Optional) Restrict this recipe to the intended machines only
COMPATIBLE_MACHINE = "(sa510m|sa510m-1g)"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-data_eth"

do_compile() {
	cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
	BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
	EXT_MODULES=../../data-eth \
	ROOTDIR=${WORKSPACE}/ \
	MODULE_OUT=${WORKDIR}/data-eth/ \
	OUT_DIR=${KERNEL_OUT_PATH}/ \
	ENABLE_DDK_BUILD=true \
	VARIANT=${TARGET_VARIANT} \
	TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
	./build/build_module.sh
}

export LD_LIBRARY_PATH = "${KERNEL_OUT_PATH}dist"
do_install() {
    # Strip debug symbols
    ${STRIP} --strip-debug \
    ${WORKDIR}/data-eth/iemac_ioss.ko
    ${STRIP} --strip-debug \
    ${WORKDIR}/data-eth/ioss.ko

    #Signing and installing the data-eth module
    ${KERNEL_OUT_PATH}/dist/sign-file sha1 ${KERNEL_OUT_PATH}/dist/signing_key.pem \
    ${KERNEL_OUT_PATH}/dist/signing_key.x509 ${WORKDIR}/data-eth/iemac_ioss.ko
    install -m 0644 ${WORKDIR}/data-eth/iemac_ioss.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/emac_ioss/iemac_ioss.ko

    ${KERNEL_OUT_PATH}/dist/sign-file sha1 ${KERNEL_OUT_PATH}/dist/signing_key.pem \
    ${KERNEL_OUT_PATH}/dist/signing_key.x509 ${WORKDIR}/data-eth/ioss.ko
    install -m 0644 ${WORKDIR}/data-eth/ioss.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ioss/ioss.ko

    #Install startup scripts
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/emac_ioss.service -D ${D}${systemd_unitdir}/system/emac_ioss.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/emac_ioss.service \
           ${D}${systemd_unitdir}/system/local-fs.target.wants/emac_ioss.service
}

FILES:${PN}+="${systemd_unitdir}/system/emac_ioss.service"
FILES:${PN}+="${systemd_unitdir}/system/local-fs.target.wants/emac_ioss.service"

INSANE_SKIP:${PN} += "installed-vs-shipped"

# vim: syntax=bitbake
