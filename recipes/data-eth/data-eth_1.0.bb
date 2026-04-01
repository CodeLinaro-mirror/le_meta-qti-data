SUMMARY = "Data Ethernet Drivers"
DESCRIPTION = "Helper recipe to build Data Ethernet drivers out-of-tree or in devshell"

export ETH_SRCDIR = "${WORKSPACE}/data-eth"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

RM_WORK_EXCLUDE += "${PN}"

inherit pkgconfig autotools deploy module linux-kernel-base systemd


KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends]   += "virtual/kernel:do_shared_workdir"

DEPENDS = "virtual/kernel linux-kernel-qcom-headers dataipa"

RDEPENDS_${PN} += "kernel-module-ipam"

# Files from meta-qti-data
FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://data-eth"
SRC_URI += "file://emac_ioss.service"
SRC_URI += "file://setup_qos_eth0.service"
SRC_URI += "file://setup_qos_eth1.service"
SRC_URI += "file://config.ini"
SRC_URI += "file://config_qos.sh"
S = "${WORKDIR}/data-eth"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-data_eth"

EXTRA_OECONF = "--with-lib-path=${STAGING_LIBDIR} \
                --with-common-includes=${STAGING_INCDIR} \
			    --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include \
                --with-sanitized-headers=${STAGING_INCDIR}/linux-kernel-qcom/usr/include \
                --with-glib"

#RPROVIDES:${PN} += "kernel-module-data_eth-${KERNEL_VERSION}"
EXTRA_CFLAGS+= "-I${WORKDIR}/recipe-sysroot/usr/include/ipa/linux -I${WORKDIR}/recipe-sysroot/usr/include/ipa -I${WORKDIR}/recipe-sysroot/usr/include/ipa/uapi"
EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"
#EXTRA_SYMBOLS_PATH = "${STAGING_DIR}/kernel-module/Module.symvers"
KBUILD_EXTRA_SYMBOLS+= "${WORKDIR}/recipe-sysroot/usr/include/ipa/Module.symvers"

do_compile() {
	cd ${S}
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules \
               KERNEL_PATH=${STAGING_KERNEL_DIR} \
               KERNEL_VERSION=${KERNEL_VERSION} \
               CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
               AR="${KERNEL_AR}" OBJCOPY="${KERNEL_OBJCOPY}" \
               STRIP="${KERNEL_STRIP}" \
               O=${STAGING_KERNEL_BUILDDIR} \
               KBUILD_EXTRA_SYMBOLS="${WORKDIR}/recipe-sysroot/usr/include/ipa/Module.symvers" \
               EXTRA_CFLAGS="${EXTRA_CFLAGS}" ${EXTRA_OEMAKE} \
               ${MAKE_TARGETS}
}

do_install() {
	oe_runmake -C ${S} \
	KERNEL_SRC=${STAGING_KERNEL_DIR} \
	modules_install \
	INSTALL_MOD_PATH=${D}/usr

	install -d ${D}${sysconfdir}/initscripts
	install -m 0755 ${WORKDIR}/config_qos.sh ${D}${sysconfdir}/initscripts
	install -m 0755 ${WORKDIR}/config.ini ${D}${sysconfdir}/initscripts

	install -d ${D}${systemd_unitdir}/system/
	install -m 0644 ${WORKDIR}/setup_qos_eth0.service ${D}${systemd_unitdir}/system/
	install -m 0644 ${WORKDIR}/setup_qos_eth1.service ${D}${systemd_unitdir}/system/

	install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/drivers/ioss/
	install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/data-eth/drivers/ioss/

	# Copy the modules that contain debug symbols to the deploy directory
	cp ${WORKDIR}/data-eth/drivers/ioss/ioss.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/data-eth/drivers/ioss/

	# Strip debug symbols
	${STRIP} --strip-debug \
	${WORKDIR}/data-eth/drivers/ioss/ioss.ko

	#Signing and installing the ioss module
	LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
	${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
	${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${WORKDIR}/data-eth/drivers/ioss/ioss.ko

	# Install the stripped modules to the rootfs
	install -m 644 ${WORKDIR}/data-eth/drivers/ioss/ioss.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/drivers/ioss/ioss.ko

	# Create a symlink for the module
	#ln -sf /usr/lib/modules/${KERNEL_VERSION}/extra/ioss.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/ioss.ko
	#ln -sf /usr/lib/modules/${KERNEL_VERSION}/extra/iemac_ioss.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/iemac_ioss.ko

	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -d ${D}${systemd_unitdir}/system/
}

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

SYSTEMD_SERVICE:${PN} = "\
       setup_qos_eth0.service \
       setup_qos_eth1.service \
"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN}+="${libdir}/modules/*"
FILES:${PN}+="/usr/lib/modules/${KERNEL_VERSION}/extra/drivers/ioss/ioss.ko"
FILES:${PN}+="${systemd_unitdir}/system/setup_qos_eth0.service"
FILES:${PN}+="${systemd_unitdir}/system/setup_qos_eth1.service"
FILES:${PN}+="${sysconfdir}/initscripts/config_qos.sh"
FILES:${PN}+="${sysconfdir}/initscripts/config.ini"

RPROVIDES:${PN} += "kernel-module-*-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ioss-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-data-eth-${KERNEL_VERSION}"
do_remove[noexec] = "1"
# vim: syntax=bitbake
