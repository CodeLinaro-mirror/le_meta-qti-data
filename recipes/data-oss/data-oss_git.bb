inherit autotools-brokensep pkgconfig

DESCRIPTION = "Data Services Open Source"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r4"

DEPENDS += "virtual/kernel glib-2.0"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include --with-glib"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://data-oss"
S = "${WORKDIR}/data-oss"
