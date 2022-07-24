SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS_packagegroup-qti-data = ' \
    dhcp-client \
    dhcp-server \
    dhcp-relay \
    radvd \
    dnsmasq \
    iperf \
    tcpdump \
    ethtool \
    iputils \
    conntrack-tools \
    strace \
    ebtables \
    iproute2 \
    iptables \
    '

