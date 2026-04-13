SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    ${MLPREFIX}packagegroup-qti-data \
    '

# The following packages are optional and can be added if needed:
# datarmnet-ext, aquantia, iproute2-tc, tcpdump, iproute2
# dnsmasq, bridge-utils, conntrack-tools, data-ipa-cfg-mgr

RDEPENDS:packagegroup-qti-data = ' \
    dataipa \
    datarmnet \
    datarmnet-ext \
    aquantia \
    iproute2-tc \
    tcpdump \
    iproute2 \
    iptables \
    dnsmasq \
    bridge-utils \
    conntrack-tools \
    eth-sdk-dlkm \
    data-ipa-cfg-mgr \
    '
RDEPENDS:packagegroup-qti-data:append:echo = " data-devicetree"
RDEPENDS:packagegroup-qti-data:remove:echo = "datarmnet-ext aquantia iproute2-tc tcpdump iproute2 dnsmasq bridge-utils conntrack-tools data-ipa-cfg-mgr"
