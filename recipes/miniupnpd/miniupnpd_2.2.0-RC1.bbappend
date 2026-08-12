SRC_URI:remove = "https://codeaurora.org/mirrored_source/quic/le/miniupnpd-2.2.0-RC1.tar.gz"
SRC_URI:prepend = "https://codelinaro.jfrog.io/artifactory/codelinaro-le/miniupnpd-2.2.0-RC1.tar.gz "

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

DEPENDS += "data"

do_compile() {
    cd ${S}
    sed -i 's|libqcmap_client\.so\.1|libqcmap_client\.so|g' ${S}/Makefile.linux
    ./configure ${IPV6} --strict --vendorcfg --debug --igd2
    make LIBDIR=${STAGING_LIBDIR} INCDIR=${STAGING_INCDIR} STRIP=echo LDFLAGS="${LDFLAGS}"
}

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:echo = " file://0001-miniupnpd-echo-interfaces.patch"
