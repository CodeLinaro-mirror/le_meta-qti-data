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
    qca-nss-ecm \
    '
RDEPENDS:packagegroup-qti-data:append:echo = "data-devicetree qca-nss-sfe"
RDEPENDS:packagegroup-qti-data:remove:echo = "datarmnet-ext aquantia iproute2-tc tcpdump iproute2 dnsmasq bridge-utils conntrack-tools data-ipa-cfg-mgr"
# sa535m enables ECM, so data-ipa-cfg-mgr is not required
RDEPENDS:packagegroup-qti-data:remove:sa535m = "data-ipa-cfg-mgr"