SUMMARY = "DATA open source package groups"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

LICENSE =  "GPL-2.0-or-later & GPL-2.0-only &  BSD"

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS:packagegroup-qti-data = ' \
    iproute2 \
    iptables \
    dnsmasq \
    ethtool \
    dhcpcd \
    tcpdump \
    conntrack-tools \
    '
