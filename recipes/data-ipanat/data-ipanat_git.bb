inherit autotools-brokensep pkgconfig

DESCRIPTION = "Qualcomm Technologies, Inc. IPA NAT shared library"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

PR = "r1"

DEPENDS  = "glib-2.0"
DEPENDS += "virtual/kernel"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include \
                --with-glib"

FILESPATH = "${WORKSPACE}/kernel/msm-${PREFERRED_VERSION_linux-msm}/techpack/dataipa:"
SRC_URI = "file://ipanat"

S = "${WORKDIR}/ipanat/src"

