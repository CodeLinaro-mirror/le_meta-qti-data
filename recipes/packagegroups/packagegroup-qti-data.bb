SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause-Clear"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS:packagegroup-qti-data = ' \
	conntrack-tools \
	data-oss \
	datarmnet \
	dataipa \
	data-ipa-cfg-mgr \
        dhcpcd \
	ebtables \
	ethtool \
	iproute2 \
	iptables \
        dnsmasq \
	iputils \
	tcpdump \
	strace \
	libgpiod \
	phytool \
    '
