SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
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
