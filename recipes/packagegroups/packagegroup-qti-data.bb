SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    packagegroup-qti-data-extra \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qti-emac-dwc-eqos", "packagegroup-qti-ethernet-tools", "", d)} \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qti-data-modem", "packagegroup-qti-data-modem", "", d)} \
    '

RDEPENDS:packagegroup-qti-data = ' \
    iproute2 \
    iptables \
    dnsmasq \
    dhcpcd \
    tcpdump \
    conntrack-tools \
    data-oss \
    dataipa \
    datarmnet \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qti-emac-dwc-eqos", "packagegroup-qti-ethernet-tools", "", d)} \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qti-data-modem", "packagegroup-qti-data-modem", "", d)} \
    '

RDEPENDS:packagegroup-qti-ethernet-tools = " \
    ethtool \
    emac-dwc-eqos \
    "
RDEPENDS:packagegroup-qti-data-modem:qcm2290-mtp = " \
    data-oss \
    "
RDEPENDS:packagegroup-qti-data-modem:qcm4325-mtp = " \
    data-oss \
    "
RDEPENDS:packagegroup-qti-data-modem:kera = " \
    data-oss \
    data-devicetree \
    "
RDEPENDS:packagegroup-qti-data:append:kalama = "aquantia ethtool qps615 qps615-firmware"
RDEPENDS:packagegroup-qti-data:remove:kalama = "datarmnet"

RDEPENDS:packagegroup-qti-data:remove:qcs610-odk-64 = " \
    dataipa \
    datarmnet \
    "
