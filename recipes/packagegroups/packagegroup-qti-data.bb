SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

# Install eth-adaption-layer for selected machines
ETHADAPT ?= 'True'
ETHADAPT_sa410m = 'FALSE'

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
	${@oe.utils.conditional('ETHADAPT', 'True', 'eth-adaption-layer', '', d)} \
    '
