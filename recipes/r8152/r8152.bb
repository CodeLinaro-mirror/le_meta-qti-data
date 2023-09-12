SUMMARY = "R8152"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

inherit systemd

FILES_${PN}     += "${systemd_unitdir}/system/*"

# Files from meta-qti-data
SRC_URI += "file://r8152.service"
SRC_URI += "file://50-usb-realtek-net.rules"

SYSTEMD_SERVICE_${PN} = "r8152.service"

do_install() {
	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -m 0644 ${WORKDIR}/r8152.service \
		-D ${D}${systemd_unitdir}/system/r8152.service

	# Install the udev rules
	install -d ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 ${WORKDIR}/50-usb-realtek-net.rules \
		-D ${D}${sysconfdir}/udev/rules.d/
}

# vim: syntax=bitbake
