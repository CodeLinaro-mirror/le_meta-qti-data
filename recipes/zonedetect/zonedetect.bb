DESCRIPTION = "C library to find timezone for given location using a database file"
HOMEPAGE = "https://github.com/BertoldVdb/ZoneDetect"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=37b8770dd59d13a95289923c4f0f4f20"
SECTION = "utils"
PR = "r1"

SRC_URI = "${CLO_LE_GIT}/ZoneDetect.git;protocol=https;branch=caf_migration/BertoldVdb/master \
           file://Makefile"
SRCREV = "${AUTOREV}"

# Git based uris are unpacked into git/ directory
S = "${WORKDIR}/git"

do_configure() {
  cp ${S}/library/zonedetect.c ${S}
  cp ${S}/library/zonedetect.h ${S}
  cp ${WORKDIR}/Makefile ${S}
  sed -i '1s/^/#include<stddef.h>\n /' ${S}/zonedetect.h
  sed -i '1s/^/#include<math.h>\n /' ${S}/zonedetect.h
}

do_compile() {
  oe_runmake clean
  oe_runmake
}

do_install () {
  install -d ${D}${base_bindir}
  install -m 775 ${S}/tzdetect ${D}${base_bindir}
}

