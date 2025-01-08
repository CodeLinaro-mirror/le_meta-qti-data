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
	data-oss \
	datarmnet \
	datarmnet-ext \
        dhcpcd \
	ebtables \
	ethtool \
	eth-qos \
	iproute2-tc \
	eth-cli \
	iproute2 \
	iptables \
        dnsmasq \
	sfe \
	iputils \
	tcpdump \
	strace \
	libgpiod \
	phytool \
	tcp-splice \
	${@oe.utils.conditional('BASEMACHINE', 'sa525m', 'hostap-2.10', '', d)} \
        ${@bb.utils.contains('MACHINE_FEATURES', 'qti-eap','', 'dataipa data-ipa-cfg-mgr datarmnet',  d)} \
    '
