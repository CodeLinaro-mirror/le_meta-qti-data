
SUMMARY = "Eth QOS CLI Program"

export ETH_SRCDIR = "${WORKSPACE}/data-eth"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${ETH_SRCDIR}/cli/eth-qos;beginline=2;endline=3;md5=2848b180796f0e294bf40e26e0ba34d8"

SRC_URI += "file://${ETH_SRCDIR}/cli/eth-qos"

# Git based uris are unpacked into git/ directory
S = "${ETH_SRCDIR}/git"

inherit pkgconfig useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--home /usr/sbin --no-create-home --shell /bin/false --user-group ethqos"

do_install() {
	install -m 0755 \
		${ETH_SRCDIR}/cli/eth-qos -D ${D}${sbindir}/eth-qos
}

DEPENDS += "bash"
RDEPENDS:${PN} = "bash"

# vim: filetype=bitbake
