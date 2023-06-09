
SUMMARY = "Host AP and WPA Supplicant Services"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://README;beginline=28;endline=56;md5=55c476aa11882ce6de69df40e3a36000"

DEPENDS = "dbus libnl openssl virtual/kernel linux-msm-headers"

# HostAp tag=hostap_2_10
SRC_URI  = "git://git.codelinaro.org/clo/le/hostap.git;protocol=http;branch=hostap/main"
SRCREV = "cff80b4f7d3c0a47c052e8187d671710f48939e4"

# Use upstream as fallback
MIRRORS += "git://git.codelinaro.org/clo/le/hostap.git;protocol=http;branch=hostap/main git://w1.fi/hostap.git;protocol=http;branch=main \n "

# Instead of maintaining the entire config file, keep only the options that
# need to be appended to the default defconfigs that come with the project.
SRC_URI += "file://defconfig-hostapd.append"
SRC_URI += "file://defconfig-wpa_supplicant.append"
SRC_URI += "file://0001-hostap-2.10-Driver-changes-to-set-PHY-offload.patch"
SRC_URI += "file://0001-hostap-2.10-Driver-changes-to-set-iface-name.patch"

# MACSEC support
SRC_URI  += "file://mka-supplicant@.service"
SRC_URI  += "file://mka-authenticator@.service"

SRC_URI  += "file://mka_supplicant-eth0.conf"
SRC_URI  += "file://mka_authenticator-eth0.conf"

SRC_URI  += "file://mka_supplicant-eth1.conf"
SRC_URI  += "file://mka_authenticator-eth1.conf"

# Git based uris are unpacked into git/ directory
S = "${WORKDIR}/git"

inherit pkgconfig

do_configure() {
	cp -f ${WORKDIR}/defconfig-hostapd.append ${S}/hostapd/.config
	cp -f ${WORKDIR}/defconfig-wpa_supplicant.append ${S}/wpa_supplicant/.config
}

do_compile[depends] += "virtual/kernel:do_shared_workdir"
EXTRA_OEMAKE = " EXTRA_CFLAGS='-I${STAGING_KERNEL_BUILDDIR}/usr/include -I${STAGING_INCDIR}/linux-msm/usr/include'"

# hostapd and wpa_supplicant creates objects inside common src/ directory
# that are ABI incompatible. We need to build the software one after another
# after cleaning out any objects from previous builds.
do_compile() {
	oe_runmake -C hostapd clean
	oe_runmake -C hostapd

	oe_runmake -C wpa_supplicant clean
	oe_runmake -C wpa_supplicant
}

do_install() {
	if ${@bb.utils.contains('BASEMACHINE', 'sa525m', 'false', 'true', d)}; then
		install -m 0755 \
			${S}/hostapd/hostapd -D ${D}${sbindir}/mka_authenticator
	fi
	install -m 0755 \
		${S}/wpa_supplicant/wpa_supplicant -D ${D}${sbindir}/mka_supplicant
}

do_install_append() {
	install -d ${D}${sysconfdir}

	# MKA supplicant and authenticator configuration for eth0
	install -m 0644 ${WORKDIR}/mka_supplicant-eth0.conf -D ${D}${sysconfdir}/data/mka_supplicant-eth0.conf
	if ${@bb.utils.contains('BASEMACHINE', 'sa525m', 'false', 'true', d)}; then
		install -m 0644 ${WORKDIR}/mka_supplicant-eth1.conf -D ${D}${sysconfdir}/data/mka_supplicant-eth1.conf
		install -m 0644 ${WORKDIR}/mka_authenticator-eth0.conf -D ${D}${sysconfdir}/data/mka_authenticator-eth0.conf
		install -m 0644 ${WORKDIR}/mka_authenticator-eth1.conf -D ${D}${sysconfdir}/data/mka_authenticator-eth1.conf
	fi

	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
		install -d ${D}${systemd_system_unitdir}

		# MKA (MACSEC) Supplicant and Authenticator services
		install -m 0644 ${WORKDIR}/mka-supplicant@.service -D ${D}${systemd_system_unitdir}/mka-supplicant@.service
		if ${@bb.utils.contains('BASEMACHINE', 'sa525m', 'false', 'true', d)}; then
			install -m 0644 ${WORKDIR}/mka-authenticator@.service -D ${D}${systemd_system_unitdir}/mka-authenticator@.service
		fi
	fi
}

FILES_${PN} += "${sysconfdir}"
FILES_${PN} += "${systemd_system_unitdir}"

# vim: filetype=bitbake
