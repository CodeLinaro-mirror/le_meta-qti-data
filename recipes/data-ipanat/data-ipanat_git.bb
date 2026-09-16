inherit autotools-brokensep pkgconfig

DESCRIPTION = "Qualcomm Technologies, Inc. IPA NAT shared library"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r1"

DEPENDS  = "glib-2.0 virtual/kernel"
DEPENDS += "${@bb.utils.contains('MACHINE_FEATURES', 'commonlog', 'commonlog-lib', '', d)}"

# CLOG unified logging (FR205597). ipanat only links CLOG when this MACHINE
# feature is enabled; otherwise it keeps relaying logs to its caller (or
# falling back to stdout) exactly as it does today.
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'commonlog', 'clog', '', d)}"
PACKAGECONFIG[clog] = "--with-clog,,commonlog-lib"

EXTRA_OECONF = "--with-sanitized-headers=${KERNEL_OUT_PATH}/msm-kernel/usr/include/ \
                --with-glib"

FILESPATH =+ "${WORKSPACE}/dataipa:"
SRC_URI = "file://ipanat"

S = "${WORKDIR}/ipanat/src"

