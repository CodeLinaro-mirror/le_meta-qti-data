DESCRIPTION = "Data devicetree"
LICENSE = "BSD-3-Clause & GPL-2.0-only | BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

inherit linux-kernel-base deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

FILESEXTRAPATHS:prepend := "${WORKSPACE}:"
SRC_URI     =  "file://data-devicetree/"

S = "${WORKDIR}/data-devicetree"
EXT_MODULES = "${@os.path.relpath("${S}","${KERNEL_PLATFORM_PATH}")}"

do_configure[depends] = "virtual/kernel:do_shared_workdir"

KERNEL_VERSION = "${@get_kernelversion_headers('${STAGING_KERNEL_BUILDDIR}')}"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"

do_configure () {
        :
}

do_compile() {
    cd ${KERNEL_PLATFORM_PATH}
    BUILD_CONFIG=msm-kernel/${KERNEL_CONFIG} \
    OUT_DIR=${WORKDIR}/out/${KERNEL_DEFCONFIG} \
    EXT_MODULES=${EXT_MODULES} \
    MODULE_OUT=${S} \
    INPLACE_COMPILE=y \
    KERNEL_KIT=${KERNEL_PREBUILT_PATH} \
    ./build/build_module.sh
}

do_deploy() {
        install -d ${DEPLOYDIR}/tech_dtbs
        install -m 0644 \
        ${WORKDIR}/data-devicetree/ipa/*.dtbo \
        ${DEPLOYDIR}/tech_dtbs/
}

addtask do_deploy after do_install

FILES:${PN} += "${sysconfdir}/*"
ALLOW_EMPTY:${PN} = "1"
