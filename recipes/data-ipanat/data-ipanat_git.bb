inherit autotools-brokensep pkgconfig

DESCRIPTION = "Qualcomm Technologies, Inc. IPA NAT shared library"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r1"

DEPENDS  = "glib-2.0 virtual/kernel dataipa"

EXTRA_OECONF = "--with-sanitized-headers=${KERNEL_OUT_PATH}/msm-kernel/usr/include/ \
                --with-glib"

EXTRA_OEMAKE += 'DATAIPA_STAGING_INCDIR=${STAGING_DIR}/usr/include'

FILESPATH =+ "${WORKSPACE}/dataipa:"
SRC_URI = "file://ipanat"

S = "${WORKDIR}/ipanat/src"

