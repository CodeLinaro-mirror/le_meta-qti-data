SUMMARY = "Data Ethernet Drivers"
DESCRIPTION = "Generic recipe to build out-of-tree kernel modules from \
src/data-eth/drivers. Module selection is controlled by \
src/data-eth/drivers/Kbuild (obj-m entries). Service files are \
sourced from src/data-eth/files/${BASEMACHINE}-${DISTRO}/."

DISTRO = "yocto"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

RM_WORK_EXCLUDE += "${PN}"

inherit module linux-kernel-base systemd

KERNEL_SPLIT_MODULES = "0"

SYSTEMD_SERVICE:${PN} = "eth-sdk-dlkm.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

DEPENDS = "virtual/kernel linux-kernel-qcom-headers dataipa"
RDEPENDS:${PN} += "kernel-module-ipam-${KERNEL_VERSION}"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI  = "file://data-eth/drivers"
SRC_URI += "file://data-eth/files/${BASEMACHINE}-${DISTRO}"
SRC_URI += "file://data-eth/cli"

S = "${UNPACKDIR}/data-eth/drivers"
FILES_DIR = "${UNPACKDIR}/data-eth/files/${BASEMACHINE}-${DISTRO}"
ETH_CLI_DIR    = "${UNPACKDIR}/data-eth/cli"

IPA_SYSROOT = "${RECIPE_SYSROOT}/usr/include/ipa"
IPA_CFLAGS  = "-I${IPA_SYSROOT} -I${IPA_SYSROOT}/linux -I${IPA_SYSROOT}/uapi"
IPA_SYMVERS = "${RECIPE_SYSROOT}/usr/include/ipa/Module.symvers"

# -----------------------------------------------------------------------
# do_configure – clean via the kernel build system (Kbuild has no targets)
# -----------------------------------------------------------------------
do_configure() {
	oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} \
		O=${STAGING_KERNEL_BUILDDIR} \
		clean
}

# -----------------------------------------------------------------------
# do_compile – invoke the kernel build system directly.
# -----------------------------------------------------------------------
do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules \
		KERNEL_PATH=${STAGING_KERNEL_DIR} \
		KERNEL_VERSION=${KERNEL_VERSION} \
		CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
		AR="${KERNEL_AR}" OBJCOPY="${KERNEL_OBJCOPY}" \
		STRIP="${KERNEL_STRIP}" \
		O=${STAGING_KERNEL_BUILDDIR} \
		KBUILD_EXTRA_SYMBOLS="${IPA_SYMVERS}" \
		BOARD="${BASEMACHINE}"  \
		IPA_CFLAGS="${IPA_CFLAGS}"
}

# -----------------------------------------------------------------------
# do_install – generic install for every .ko and every .service found
# -----------------------------------------------------------------------
do_install() {
	# Install all modules via the kernel modules_install target so that
	# modules.dep / modules.alias are generated correctly.
	oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} \
		INSTALL_MOD_PATH=${D}/usr \
		modules_install

	# Walk every .ko produced under ${S}, strip, sign and install it.
	for ko in $(find ${S} -name "*.ko" -not -path "*/.tmp_versions/*"); do
		rel_dir=$(dirname ${ko} | sed "s|${S}/||")
		ko_name=$(basename ${ko})
		deploy_dir="${DEPLOY_DIR_IMAGE}/kernel_modules/data-eth/drivers/${rel_dir}"
		install_dir="${D}/usr/lib/modules/${KERNEL_VERSION}/extra/drivers/${rel_dir}"

		install -d ${deploy_dir}
		install -d ${install_dir}

		# Keep an unstripped copy in the deploy directory for debugging.
		cp ${ko} ${deploy_dir}/

		# Strip debug symbols in-place.
		${STRIP} --strip-debug ${ko}

		# Sign the stripped module.
		LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
		${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 \
			${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
			${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 \
			${ko}

		# Install the stripped+signed module to the rootfs.
		install -m 644 ${ko} -D ${install_dir}/${ko_name}
	done

	# Install eth-sdk-dlkm.service
	if [ -f ${FILES_DIR}/eth-sdk-dlkm.service ]; then
		install -d ${D}${systemd_unitdir}/system/
		install -m 0644 ${FILES_DIR}/eth-sdk-dlkm.service \
			-D ${D}${systemd_unitdir}/system/eth-sdk-dlkm.service
	fi

	# Install eth-qos CLI tool
	install -d ${D}${sbindir}
	install -m 0755 ${ETH_CLI_DIR}/eth-qos ${D}${sbindir}/eth-qos
}

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

# Capture all installed modules and service files with wildcards
FILES:${PN} += "${libdir}/modules/*"
FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/extra/drivers/"
FILES:${PN} += "${systemd_unitdir}/system/*.service"
#file created by 'inherit module' bbclass
FILES:${PN} += "${sysconfdir}/modules-load.d"
FILES:${PN} += "${sbindir}/eth-qos"

do_remove[noexec] = "1"
# vim: syntax=bitbake
