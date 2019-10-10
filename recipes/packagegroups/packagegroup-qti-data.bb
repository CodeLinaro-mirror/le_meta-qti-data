SUMMARY = "DATA open source package groups"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    ${@bb.utils.contains_any("COMBINED_FEATURES", "qti-wifi ethernet", "packagegroup-qti-network-tools", "", d)} \
    '

RDEPENDS_packagegroup-qti-data = ' \
    ${@bb.utils.contains_any("COMBINED_FEATURES", "qti-wifi ethernet", "packagegroup-qti-network-tools", "", d)} \
    '

RDEPENDS_packagegroup-qti-network-tools = " \
    iproute2 \
    iptables \
    iperf \
    dnsmasq \
    ebtables \
    ethtool \
    dhcpcd \
    tcpdump \
    conntrack-tools \
    "
