SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS_packagegroup-qti-data = ' \
	conntrack-tools \
	data-ipa-cfg-mgr \
	data-oss \
	datarmnet \
	dhcpcd \
	ethtool \
	iperf \
	iproute2 \
	iptables \
	dnsmasq \
	ebtables \
	sfe \
	iputils \
	tcpdump \
	strace \
    '
