SUMMARY = "Display or change ethernet card settings"
DESCRIPTION = "A small utility for examining and tuning the settings of your ethernet-based network interfaces."
HOMEPAGE = "https://www.kernel.org/pub/software/network/ethtool/"
SECTION = "console/network"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://ethtool.c;beginline=4;endline=17;md5=c19b30548c582577fc6b443626fc1216"

SRC_URI = "${KERNELORG_MIRROR}/software/network/ethtool/ethtool-${PV}.tar.gz"

SRC_URI[sha256sum] = "1a17b13dba2aef3897bb66c6140d88dc19e807b7948de3773d37320daae5266c"

UPSTREAM_CHECK_URI = "https://www.kernel.org/pub/software/network/ethtool/"

inherit autotools pkgconfig bash-completion

PACKAGECONFIG ?= "netlink"
PACKAGECONFIG[netlink] = "--enable-netlink,--disable-netlink,libmnl,"

FILES:${PN} += "/usr/share/metainfo"
