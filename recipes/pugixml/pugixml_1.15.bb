inherit autotools gettext
SUMMARY = "XML Parser library "
HOMEPAGE = "https://github.com/zeux/pugixml"
LICENSE = "MIT"
PRIORITY = "optional"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Package Revision (update whenever recipe is changed)
PR = "r0"

TARGET_CC_ARCH += "${LDFLAGS}"

SRC_URI = "  https://github.com/zeux/pugixml/archive/v${PV}.zip \
    file://0001-pugixml-Modified-include-dir-and-lib-dir.patch \
"

SRC_URI[md5sum] = "6c2a10fdb858d1c069a46f648cb8d4fa"
SRC_URI[sha256sum] = "0dd4dc16e38bbc1c889d3a59660072450127bac7d19415f3cf5d5d6b25c58b35"

inherit lib_package cmake

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON"
