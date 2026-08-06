DESCRIPTION = "6brd"
SUMMARY = "Bridge NDP between equal-prefix IPv6 network segments"
SECTION = "networking"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${S}/README;md5=c548d85e96046e189509ac1ae9596f41"

inherit pkgconfig

DEPENDS += "libnl glib-2.0 pkgconfig-native"

SRC_URI = "git://github.com/CasperVector/6brd.git;protocol=https;branch=master \
           file://00001-6brd-IPv6.patch \
           file://00002-6brd-sed-command-elimination.patch \
          "

SRCREV = "7d73a89a055d8f470e81ddd9554d7f4c523b5b70"

S = "${WORKDIR}/git"
export STAGING_INCDIR
export RECIPE_SYSROOT

do_compile() {
    oe_runmake CC="${CC}" -C "${S}"
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/git/6brd ${D}${sbindir}
}

FILES:${PN} = "${sbindir}/6brd"
