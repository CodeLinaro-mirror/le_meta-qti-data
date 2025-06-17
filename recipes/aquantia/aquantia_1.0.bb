inherit linux-kernel-base deploy logging

inherit systemd

DESCRIPTION = "Aquantia Phy Driver"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "virtual/kernel linux-msm-headers"

FILESPATH   =+ "${WORKSPACE}:"

SRC_URI = "git://git.codelinaro.org/clo/le/external/github.com/aquantia/linux-aqr-phy-only;protocol=git;branch=aquantia/main"
SRCREV = "1a4467eb0faa2a9fae96d1a68c79f9ea77342436"

SRC_URI += "file://0001-net-phy-aquantia-Support-out-of-tree-module-compilat.patch"
SRC_URI += "file://0002-net-phy-aquantia-Fix-WOL-check-precedence-bug.patch"
SRC_URI += "file://0003-phy-aquantia-Ensure-correct-system-interface-status-.patch"
SRC_URI += "file://0004-phy-aquantia-Add-eee-support-to-AQR-github-driver.patch"
SRC_URI += "file://aquantia.service"

S = "${WORKDIR}/git"
S2 = "${WORKDIR}/git/aquantia"

KP_PATH = "${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform"
REL_PATH = "${@os.path.relpath(d.getVar('S2'), d.getVar('KP_PATH'))}"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"

do_compile:kalama() {
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=${REL_PATH} \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/git/aquantia \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:kalama() {
   install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -m 0644 ${WORKDIR}/git/aquantia/aquantia.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
   install -d ${D}${systemd_unitdir}/system/
   install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
   install -m 0644 ${WORKDIR}/aquantia.service -D ${D}${systemd_unitdir}/system/aquantia.service
   ln -sf -r ${D}${systemd_unitdir}/system/aquantia.service \
	${D}${systemd_unitdir}/system/multi-user.target.wants/aquantia.service
}

do_deploy() {
    install -d ${DEPLOYDIR}/kernel_modules
    cp -rp ${WORKDIR}/git/aquantia/aquantia.ko ${DEPLOYDIR}/kernel_modules
}
addtask do_deploy after do_install before do_package

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN}+="${systemd_unitdir}/system/aquantia.service"
FILES:${PN}+="${systemd_unitdir}/system/multi-user.target.wants/aquantia.service"

