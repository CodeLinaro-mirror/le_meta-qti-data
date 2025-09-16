SUMMARY = "AQC IPA Offload"

export AQO_OBJDIR = "${WORKDIR}/kobj"
export AQO_SRCDIR = "${WORKSPACE}/data-kernel/drivers/aqc-ipa-offload"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${AQO_SRCDIR}/aqo_main.c;\
beginline=3;endline=10;md5=28fe1ff28187fe4efdc6414eeb8185e3"

inherit module
inherit qperf
inherit systemd

# Files from meta-qti-data
SRC_URI += "file://kobj/Makefile"
SRC_URI += "file://aqc-ipa-offload.service"

# Script for monitoring AQC GSI debugfs stats provided by IPA driver
SRC_URI += "file://aqc_gsi_stats"

S = "${AQO_OBJDIR}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-aqc_ipa_offload"

SYSTEMD_SERVICE_${PN} = "aqc-ipa-offload.service"

do_install:append() {
	install -m 0755 \
		${WORKDIR}/aqc_gsi_stats -D ${D}${bindir}/aqc_gsi_stats

	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -m 0644 ${WORKDIR}/aqc-ipa-offload.service \
		-D ${D}${systemd_system_unitdir}/aqc-ipa-offload.service
}

FILES_${PN} += "${bindir}"

# qperf class adds do_copy_kernel_module() after do_module_signing().
# Since we do not yet support module signing, explicitly add the task to
# execute between compile and package stages.
addtask copy_kernel_module after do_compile before do_package

# vim: syntax=bitbake
