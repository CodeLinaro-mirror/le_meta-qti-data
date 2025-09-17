inherit module

SUMMARY = "IPA driver"

DESCRIPTION = "Contains IPA driver"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

# DEPENDS = "virtual/kernel"
inherit pkgconfig autotools deploy module linux-kernel-base

PR = "r0"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://dataipa"
SRC_URI += "file://start_dataipa_le"
SRC_URI += "file://dataipa.service"
SRC_URI += "file://ipa_config.txt"

S = "${WORKDIR}/dataipa"
EXT_MODULES = "${@os.path.relpath("${S}", "${KERNEL_PLATFORM_PATH}")}"
KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends]   += "virtual/kernel:do_shared_workdir"
DEPENDS = "virtual/kernel linux-kernel-qcom-headers"
DATAIPADRVTOP = "${WORKDIR}/dataipa/drivers/platform/msm"
FILES:${PN}+="${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="${nonarch_base_libdir}/"
RPROVIDES:${PN} += "dataipa"
RPROVIDES:${PN} += "kernel-module-*-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ipanetm-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-gsim-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ipam-${KERNEL_VERSION}"

EXTRA_OEMAKE += "DATAIPADRVTOP=${DATAIPADRVTOP}"
EXTRA_OEMAKE += "KERNEL_SRC=${STAGING_KERNEL_DIR}"

#TARGET_VARIANT= "${@bb.utils.contains('KERNEL_VARIANT', 'perf_', 'perf_defconfig', 'debug_defconfig', d)}"
#do_configure() {
#    cp ${WORKDIR}/kobj/Makefile ${S}/
#}

do_compile() {
	cd ${S}
	oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
}
do_install() {
    oe_runmake -C ${S}/drivers/platform/msm \
        KERNEL_SRC=${STAGING_KERNEL_DIR} \
        modules_install \
        INSTALL_MOD_PATH=${D}/usr
   install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/ipa
	install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra

   module_path="${WORKDIR}/dataipa/drivers/platform/msm"

   module_list="gsi/gsim.ko ipa/ipam.ko ipa/ipanetm.ko"
	 for module in ${module_list}; do
     cp ${module_path}/${module} ${DEPLOY_DIR_IMAGE}/kernel_modules/ipa
		# Strip debug symbols
		${STRIP} --strip-debug ${module_path}/${module}
		#Signing and installing the dataipa module
		LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
		${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
		${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${module_path}/${module}
     install -m 0644  ${module_path}/${module} ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/
   done

   install -d ${D}${sysconfdir}/initscripts/
   install -m 0555 ${WORKDIR}/start_dataipa_le ${D}${sysconfdir}/initscripts/start_dataipa_le
   install -d ${D}${systemd_unitdir}/system/
   install -m 0644 ${WORKDIR}/dataipa.service -D ${D}${systemd_unitdir}/system/dataipa.service
   install -d ${D}${sysconfdir}/data/
   install -m 0644 ${WORKDIR}/ipa_config.txt -D ${D}${sysconfdir}/data/ipa_config.txt
   install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
   ln -sf ${systemd_unitdir}/system/dataipa.service \
          ${D}${systemd_unitdir}/system/local-fs.target.wants/dataipa.service
}

pkg_postinst:${PN}(){
    chown -Rh 1001:1001 $D${sysconfdir}/data/ipa_config.txt
}

#FILES:${PN}+="${libdir}/modules/*"
#FILES:${PN} += "/lib/modules/*/updates/"
FILES:${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="${sysconfdir}/initscripts/start_dataipa_le"
FILES:${PN}+="${systemd_unitdir}/system/dataipa.service"
FILES:${PN}+="${sysconfdir}/data/ipa_config.txt"
FILES:${PN}+="${systemd_unitdir}/system/local-fs.target.wants/dataipa.service"

FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/extra/gsi/gsim.ko"
FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/extra/ipa/ipam.ko"
FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/extra/ipa/ipanetm.ko"
FILES:${PN}+="${nonarch_base_libdir}/usr/lib/modules/${KERNEL_VERSION}/*"
FILES:${PN}+="${nonarch_base_libdir}/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN}+="${nonarch_base_libdir}/"
FILES:${PN}+="${nonarch_base_libdir}/modules/*"
RPROVIDES:${PN} += "dataipa"
MAKE_TARGETS = "all"