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
    aquantia \
    iproute2 \
    iptables \
    dnsmasq \
    dhcpcd \
    tcpdump \
    conntrack-tools \
    data-oss \
    dataipa \
    datarmnet \
    ethtool \
    qps615 \
    qps615-firmware \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qti-emac-dwc-eqos", "packagegroup-qti-ethernet-tools", "", d)} \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "qti-data-modem", "packagegroup-qti-data-modem", "", d)} \
    '

RDEPENDS:packagegroup-qti-ethernet-tools = " \
    ethtool \
    emac-dwc-eqos \
    "

RDEPENDS:packagegroup-qti-data-modem = " \
    data-oss \
    "

RDEPENDS:packagegroup-qti-data:remove:qcs610-odk-64 = " \
    dataipa \
    aquantia \
    qps615 \
    datarmnet \
    "
