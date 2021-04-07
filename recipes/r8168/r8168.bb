SUMMARY = "R8168"

export R8168_OBJDIR = "${WORKDIR}/kobj"
export R8168_SRCDIR = "${WORKSPACE}/data-kernel/drivers/r8168/src"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${R8168_SRCDIR}/r8168_n.c;\
beginline=9;endline=17;md5=9826a2c77338d02bc1877c9cee0242e9"

inherit module
inherit qperf
inherit systemd

# Files from meta-qti-data
SRC_URI += "file://kobj/Makefile"
SRC_URI += "file://r8168.service"

S = "${R8168_OBJDIR}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-r8168"

SYSTEMD_SERVICE_${PN} = "r8168.service"

do_install_append() {
	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -m 0644 ${WORKDIR}/r8168.service \
		-D ${D}${systemd_system_unitdir}/r8168.service
}

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

# vim: syntax=bitbake
