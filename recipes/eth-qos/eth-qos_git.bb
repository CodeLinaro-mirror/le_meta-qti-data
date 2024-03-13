
SUMMARY = "Eth QOS CLI Program"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${WORKDIR}/eth-qos;beginline=2;endline=3;md5=2848b180796f0e294bf40e26e0ba34d8"

SRC_URI += "file://eth-qos"

# Git based uris are unpacked into git/ directory
S = "${WORKDIR}/git"

inherit pkgconfig useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--home /usr/sbin --no-create-home --shell /bin/false --user-group ethqos"

do_install() {
	install -m 0755 \
		${WORKDIR}/eth-qos -D ${D}${sbindir}/eth-qos
}

DEPENDS += "bash"
RDEPENDS:${PN} = "bash"

# vim: filetype=bitbake
