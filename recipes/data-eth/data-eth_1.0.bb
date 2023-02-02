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

FILESPATH =+ "${WORKSPACE}:"
SRC_URI += "file://data-eth"
S = "${WORKDIR}/data-eth"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-data_eth"

do_install_append() {
	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -m 0644 ${WORKDIR}/emac_ioss.service \
		 -D ${D}${systemd_system_unitdir}/emac_ioss.service
}

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

FILES_${PN}+="${systemd_unitdir}/system/emac_ioss.service"

# vim: syntax=bitbake
