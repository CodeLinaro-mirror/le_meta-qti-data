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
	data-eth \
	dataipa \
	data-ipa-cfg-mgr \
	data-oss \
	datarmnet \
        dhcpcd \
	ebtables \
	ethtool \
	iperf \
	iproute2 \
	iptables \
        dnsmasq \
	sfe \
	iputils \
	gsb \
	tcpdump \
	strace \
	libgpiod \
	phytool \
    '
