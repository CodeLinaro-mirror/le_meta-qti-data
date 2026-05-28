inherit autotools-brokensep pkgconfig

DESCRIPTION = "Qualcomm Technologies, Inc. IPA NAT shared library"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r1"

DEPENDS  = "glib-2.0"
DEPENDS += "virtual/kernel"
DEPENDS += "dataipa"

EXTRA_OECONF = "--with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include \
                --with-sanitized-headers=${STAGING_INCDIR}/linux-kernel-qcom/usr/include \
                --with-glib"

EXTRA_OEMAKE += 'DATAIPA_STAGING_INCDIR=${STAGING_DIR}/usr/include'

FILESPATH =+ "${WORKSPACE}/dataipa:"
SRC_URI = "file://ipanat"

S = "${UNPACKDIR}/ipanat/src"

