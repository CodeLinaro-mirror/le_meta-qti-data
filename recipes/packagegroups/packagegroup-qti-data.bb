SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause-Clear"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS_packagegroup-qti-data = ' \
    conntrack-tools \
    data-oss \
    datarmnet \
    dhcpcd \
    ebtables \
    iproute2 \
    iptables \
    dnsmasq \
    iputils \
    rmnetbam \
    sfe \
    tcpdump \
    iperf \
    '

