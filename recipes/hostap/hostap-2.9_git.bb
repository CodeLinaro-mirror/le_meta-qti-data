
SUMMARY = "Host AP and WPA Supplicant Services"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://README;beginline=28;endline=56;md5=55c476aa11882ce6de69df40e3a36000"

DEPENDS = "dbus libnl openssl virtual/kernel"

# HostAp tag=hostap_2_9
SRC_URI  = "git://source.codeaurora.org/quic/le/platform/external/hostap.git;protocol=http;branch=upstream/main"
SRCREV = "ca8c2bd28ad53f431d6ee60ef754e98cfdb4c17b"

# Use upstream as fallback
MIRRORS += "git://source.codeaurora.org/quic/le/platform/external/hostap.git;protocol=http;branch=upstream/main git://w1.fi/hostap.git;protocol=http;branch=main \n "

# Instead of maintaining the entire config file, keep only the options that
# need to be appended to the default defconfigs that come with the project.
SRC_URI += "file://defconfig-hostapd.append"
SRC_URI += "file://defconfig-wpa_supplicant.append"
SRC_URI += "file://0001-hostap-2.9-Driver-changes-to-set-PHY-offload.patch"



# Git based uris are unpacked into git/ directory
S = "${WORKDIR}/git"

inherit pkgconfig

do_configure() {
	cat ${S}/hostapd/defconfig ${WORKDIR}/defconfig-hostapd.append > ${S}/hostapd/.config
	cat ${S}/wpa_supplicant/defconfig ${WORKDIR}/defconfig-wpa_supplicant.append > ${S}/wpa_supplicant/.config
}

do_compile[depends] += "virtual/kernel:do_shared_workdir"
EXTRA_OEMAKE = " EXTRA_CFLAGS=-I${STAGING_KERNEL_BUILDDIR}/usr/include"

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
	install -m 0755 \
		${S}/hostapd/hostapd -D ${D}${sbindir}/hostapd-2.9
	install -m 0755 \
		${S}/wpa_supplicant/wpa_supplicant -D ${D}${sbindir}/wpa_supplicant-2.9
}

# vim: filetype=bitbake
