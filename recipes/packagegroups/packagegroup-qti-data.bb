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
	data-eth \
	dataipa \
	thermal-eth-netlink \
	data-ipa-cfg-mgr \
	data-oss \
	datarmnet \
	datarmnet-ext \
        dhcpcd \
	ebtables \
	ethtool \
	eth-qos \
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
        ipset \
	${@oe.utils.conditional('BASEMACHINE', 'sa525m', 'hostap-2.10', '', d)} \
    '
