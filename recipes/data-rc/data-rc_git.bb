
SUMMARY = "Data Services RC scripts and configs"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/hostapd-eth0.conf;beginline=1;endline=26;md5=d433114c90f1b34fc4b93e3ebd6965c5"

# MACSEC support
SRC_URI   = "file://mka-supplicant@.service"
SRC_URI  += "file://mka-authenticator@.service"
SRC_URI  += "file://wpa_supplicant-eth0.conf"
SRC_URI  += "file://hostapd-eth0.conf"
SRC_URI  += "file://wpa_supplicant-eth1.conf"
SRC_URI  += "file://hostapd-eth1.conf"

do_install_append() {
	install -d ${D}${sysconfdir}

	# MKA supplicant and authenticator configuration for eth0
	install -m 0644 ${WORKDIR}/wpa_supplicant-eth0.conf -D ${D}${sysconfdir}/data/wpa_supplicant-eth0.conf
	install -m 0644 ${WORKDIR}/wpa_supplicant-eth1.conf -D ${D}${sysconfdir}/data/wpa_supplicant-eth1.conf
	install -m 0644 ${WORKDIR}/hostapd-eth0.conf -D ${D}${sysconfdir}/data/hostapd-eth0.conf
	install -m 0644 ${WORKDIR}/hostapd-eth1.conf -D ${D}${sysconfdir}/data/hostapd-eth1.conf

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${systemd_system_unitdir}

		# MKA (MACSEC) Supplicant and Authenticator services
		install -m 0644 ${WORKDIR}/mka-supplicant@.service -D ${D}${systemd_system_unitdir}/mka-supplicant@.service
		install -m 0644 ${WORKDIR}/mka-authenticator@.service -D ${D}${systemd_system_unitdir}/mka-authenticator@.service
	fi
}

FILES_${PN} += "${sysconfdir}"
FILES_${PN} += "${systemd_system_unitdir}"

# vim: filetype=bitbake
