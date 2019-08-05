SUMMARY = "AQC IPA Offload"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://aqc-ipa-offload/aqo_main.c;\
beginline=3;endline=10;md5=28fe1ff28187fe4efdc6414eeb8185e3"

inherit module

FILESPATH =+ "${WORKSPACE}:${WORKSPACE}/data-kernel/drivers/:"

SRC_URI  = "file://aqc-ipa-offload"
SRC_URI += "file://Makefile"
SRC_URI += "file://aqc-ipa-offload.service"

MODULES_MODULE_SYMVERS_LOCATION = "aqc-ipa-offload"

S = "${WORKDIR}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-aqc-ipa-offload"

FILES_${PN} += "${systemd_system_unitdir}"

do_install_append() {
	# Install systemd service for loading/unloading the driver and
	# enable the service for multi-user target.
	install -d ${D}${systemd_system_unitdir}/multi-user.target.wants
	install -m 0600 ${WORKDIR}/aqc-ipa-offload.service \
		-D ${D}${systemd_system_unitdir}/aqc-ipa-offload.service
	ln -sfT ${systemd_system_unitdir}/aqc-ipa-offload.service \
		${D}${systemd_system_unitdir}/multi-user.target.wants/aqc-ipa-offload.service
}

# vim: syntax=bitbake
