SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS_packagegroup-qti-data = ' \
    radvd \
    dhcpcd \
    dhcp-libs \
    hostap-2.9 \
    dhcp-client \
    dhcp-server \
    dhcp-relay \
    dnsmasq \
    iperf \
    tcpdump \
    ethtool \
    iputils \
    strace \
    iproute2 \
    techpack-ecpri \
    strongswan \
    bind-utils \
    '

