SUMMARY = "R8125"

export R8125_OBJDIR = "${WORKDIR}/kobj"
export R8125_SRCDIR = "${WORKSPACE}/data-kernel/drivers/r8125/src"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${R8125_SRCDIR}/r8125_n.c;\
beginline=9;endline=17;md5=9826a2c77338d02bc1877c9cee0242e9"

inherit module
inherit qperf
inherit systemd

# Files from meta-qti-data
SRC_URI += "file://kobj/Makefile"
SRC_URI += "file://r8125.service"

S = "${R8125_OBJDIR}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-r8125"

SYSTEMD_SERVICE_${PN} = "r8125.service"

do_install_append() {
	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -m 0644 ${WORKDIR}/r8125.service \
		-D ${D}${systemd_system_unitdir}/r8125.service
}

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

# vim: syntax=bitbake
