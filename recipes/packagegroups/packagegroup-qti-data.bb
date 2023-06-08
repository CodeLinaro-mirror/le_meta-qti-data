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
	dataipa \
	data-ipa-cfg-mgr \
	data-oss \
	datarmnet \
	dhcpcd \
	ethtool \
	iproute2 \
	iptables \
	dnsmasq \
	ebtables \
	sfe \
	iputils \
	tcpdump \
	strace \
	${@oe.utils.conditional('ETHADAPT', 'True', 'eth-adaption-layer', '', d)} \
	${@oe.utils.conditional('BASEMACHINE', 'sdxprairie', 'data-rc', '', d)} \
	${@oe.utils.conditional('BASEMACHINE', 'sdxprairie', 'hostap-2.9', '', d)} \
    '
