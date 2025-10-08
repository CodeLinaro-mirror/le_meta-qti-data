SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    ${MLPREFIX}packagegroup-qti-data \
    '

RDEPENDS:packagegroup-qti-data = ' \
    dataipa \
    datarmnet \
    datarmnet-ext \
    aquantia \
    iproute2-tc \
    tcpdump \
'
