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

SRC_URI[md5sum] = "b3a18768b3a53e62052e03c8815eaff8"
SRC_URI[sha256sum] = "86a2b2f16d3e107b9ea5043ef9c458306b94d38f6203d58a196dc9b9dc6351a6"

inherit lib_package cmake

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON"
