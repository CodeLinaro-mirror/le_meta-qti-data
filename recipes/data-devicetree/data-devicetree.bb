DESCRIPTION = "Qualcomm Technologies, Inc. Data Device Tree overlays (IPA, ETH)"
SUMMARY = "Data Subsystem DTBO Overlays"
LICENSE = "BSD-3-Clause"

# Use Yocto common license file for BSD-3-Clause
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r0"

COMPATIBLE_MACHINE = "echo"

inherit deploy kernel-arch

# Ensure kernel headers/build artifacts are available in sysroot
DEPENDS += "virtual/kernel dtc-native"
do_compile[depends] += "virtual/kernel:do_shared_workdir"

# Allow SRC_URI file://vendor/... to resolve under your workspace
FILESEXTRAPATHS:prepend := "${WORKSPACE}/:"

SRC_URI  = "file://vendor/qcom/opensource/data-devicetree"
SRC_URI += "file://Makefile"

S = "${WORKDIR}/vendor/qcom/opensource/data-devicetree"

# Use kernel build dtc and staged kernel headers
DTC = "${STAGING_BINDIR_NATIVE}/dtc"
KERNEL_INCLUDE = "${STAGING_KERNEL_DIR}/include"

do_configure:prepend() {
    # Override upstream Makefile with Yocto-specific Makefile
    install -m 0644 ${WORKDIR}/Makefile ${S}/Makefile
}

do_compile() {
    test -f ${S}/Makefile || bbfatal "Missing Makefile at ${S}/Makefile"
    # Pass vars explicitly so Makefile doesn't fall back to /include or /scripts
    oe_runmake -C ${S} dtbs \
        DTC='${DTC}' \
        KERNEL_INCLUDE='${KERNEL_INCLUDE}'
}

do_install() {
    :
}

do_deploy[dirs] = "${DEPLOYDIR}"

do_deploy() {
    # Create deployment directory for DTBOs
    install -d ${DEPLOYDIR}/tech_dtbs

    # Find and install DTBOs generated under ipa/ and eth only.
    # -type f   : regular files only
    # -name     : DTBO overlays
    find ${S}/ipa ${S}/eth -type f -name "*.dtbo" \
        -exec install -m 0644 {} ${DEPLOYDIR}/tech_dtbs \;
}

addtask do_deploy after do_install

ALLOW_EMPTY:${PN} = "1"
