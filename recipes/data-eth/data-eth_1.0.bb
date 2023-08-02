SUMMARY = "Data Ethernet Drivers"
DESCRIPTION = "Helper recipe to build Data Ethernet drivers out-of-tree or in devshell"

export ETH_SRCDIR = "${WORKSPACE}/data-eth"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${ETH_SRCDIR}/data-eth.c;\
beginline=1;endline=4;md5=35144d93ffd061a7458db62d36405265"

RM_WORK_EXCLUDE += "${PN}"

inherit module
inherit qperf
inherit systemd

# Files from meta-qti-data
SRC_URI += "file://emac_ioss.service"
SRC_URI += "file://emac_shim.service"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI += "file://data-eth"
S = "${WORKDIR}/data-eth"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'qti-vm-guest', '--with-svm', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('MACHINE_FEATURES', 'qti-vm-tele',  '--with-televm', '', d)}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-data_eth"

do_install_append() {
	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -d ${D}${systemd_unitdir}/system/
	install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/

	if ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-vm-guest', 'true', 'false', d)}; then
		if ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-vm-tele', 'true', 'false', d)}; then
			install -m 0644 ${WORKDIR}/emac_shim.service \
				-D ${D}${systemd_unitdir}/system/emac_shim.service
			ln -sf -r ${D}${systemd_unitdir}/system/emac_shim.service \
				${D}${systemd_unitdir}/system/multi-user.target.wants/emac_shim.service
		fi
	else
	install -m 0644 ${WORKDIR}/emac_ioss.service \
		-D ${D}${systemd_unitdir}/system/emac_ioss.service
	ln -sf -r ${D}${systemd_unitdir}/system/emac_ioss.service \
		${D}${systemd_unitdir}/system/multi-user.target.wants/emac_ioss.service
	fi
}

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

do_strip_module() {
    # Strip debug symbols
    strip_tool="${STAGING_DIR_NATIVE}/usr/libexec/aarch64-oe-linux/gcc/aarch64-oe-linux/9.3.0/strip"

    if [ -f ${PKGDEST}/kernel-module-iemac-ioss-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/emac_ioss/iemac_ioss.ko ]; then
        ${strip_tool} --strip-debug \
        ${PKGDEST}/kernel-module-iemac-ioss-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/emac_ioss/iemac_ioss.ko
    fi

    if [ -f ${PKGDEST}/kernel-module-ioss-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/ioss/ioss.ko ]; then
        ${strip_tool} --strip-debug \
        ${PKGDEST}/kernel-module-ioss-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/ioss/ioss.ko
    fi

    if [ -f ${PKGDEST}/kernel-module-r8125-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/r8125/src/r8125.ko ]; then
        ${strip_tool} --strip-debug \
        ${PKGDEST}/kernel-module-r8125-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/r8125/src/r8125.ko
    fi

    if [ -f ${PKGDEST}/kernel-module-r8125-ioss-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/r8125_ioss/r8125_ioss.ko ]; then
        ${strip_tool} --strip-debug \
        ${PKGDEST}/kernel-module-r8125-ioss-${KERNEL_VERSION}/lib/modules/${KERNEL_VERSION}/extra/drivers/r8125_ioss/r8125_ioss.ko
    fi
}

addtask strip_module after do_package before do_package_write_ipk

FILES_${PN}+="${systemd_unitdir}/system/emac_ioss.service"
FILES_${PN}+="${systemd_unitdir}/system/multi-user.target.wants/emac_ioss.service"
FILES_${PN}+="${systemd_unitdir}/system/emac_shim.service"
FILES_${PN}+="${systemd_unitdir}/system/multi-user.target.wants/emac_shim.service"

# vim: syntax=bitbake
