inherit autotools-brokensep pkgconfig

DESCRIPTION = "Data Services Open Source"
LICENSE = "BSD-Source-Code"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=fe8b41221d7524c70688f7d059ff6d87"

PR = "r4"

DEPENDS += "linux-msm-headers virtual/kernel glib-2.0"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include --with-glib"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://data-oss"
S = "${WORKDIR}/data-oss"
