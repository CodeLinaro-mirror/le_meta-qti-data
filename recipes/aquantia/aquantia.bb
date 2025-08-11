SUMMARY = "Aquantia PHY driver"
DESCRIPTION = "PHY driver for Aquantia AQR113"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"
PACKAGE_ARCH = "${MACHINE_ARCH}"
SRCREV = "1a4467eb0faa2a9fae96d1a68c79f9ea77342436"
SRC_URI = "git://git.codelinaro.org/clo/le/external/github.com/aquantia/linux-aqr-phy-only.git;protocol=https;branch=aquantia/main \
           file://kobj/Makefile \
           file://aquantia.service"


inherit pkgconfig autotools deploy module linux-kernel-base
S = "${WORKDIR}/git/aquantia"
EXT_MODULES = "${@os.path.relpath("${S}", "${KERNEL_PLATFORM_PATH}")}"
KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends]   += "virtual/kernel:do_shared_workdir"
DEPENDS = "virtual/kernel linux-kernel-qcom-headers"
PROVIDES += "aquantia"
RPROVIDES_${PN} += "aquantia"
SYSTEMD_SERVICE_${PN} = "aquantia.service"
MAKE_TARGETS = "modules"
MODULES_INSTALL_TARGET = "modules_install"
RM_WORK_EXCLUDE += "${PN}"
# FILES:${PN} = "${nonarch_base_libdir}/modules/*"
FILES:${PN}+="${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="${nonarch_base_libdir}/"
RPROVIDES:${PN} += "aquantia"
RPROVIDES:${PN} += "kernel-module-aquantia-${KERNEL_VERSION}"
FILES:${PN} += "${systemd_unitdir}/system/aquantia. "
FILES:${PN} +="${systemd_unitdir}/system/local-fs.target.wants/aquantia.service"
FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/aquantia.ko"
IMAGE_INSTALL:append = " packagegroup-qti-telematics"
do_configure() {
	cp ${WORKDIR}/kobj/Makefile ${S}/
}

do_compile() {
	cd ${S}
	oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
}

do_install() {
	install -m 0644 ${S}/aquantia.ko \
		-D ${D}/usr/lib/modules/${KERNEL_VERSION}/aquantia.ko

        # Strip debug symbols
		${STRIP} --strip-debug ${D}/usr/lib/modules/${KERNEL_VERSION}/aquantia.ko
		#Signing and installing the dataipa module
		LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
		${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
		${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${D}/usr/lib/modules/${KERNEL_VERSION}/aquantia.ko
}

do_install:append() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/aquantia.service -D ${D}${systemd_unitdir}/system/aquantia.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/aquantia.service \
          ${D}${systemd_unitdir}/system/local-fs.target.wants/aquantia.service
}
