SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause-Clear"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    packagegroup-qti-data-iptables \
    '
RDEPENDS:packagegroup-qti-data = ' \
	conntrack-tools \
	data-ipa-cfg-mgr \
	data-oss \
	datarmnet \
        dhcpcd \
	ebtables \
	ethtool \
	iproute2 \
	iptables \
        dnsmasq \
        rtsp-alg \
	sfe \
	iputils \
	gsb \
	tcpdump \
	strace \
    '

RDEPENDS:packagegroup-qti-data-iptables = ' \
	iptables-module-xt-hashlimit \
	iptables-module-xt-conntrack \
	iptables-module-xt-tos \
	iptables-module-xt-rateest \
	iptables-module-xt-mark \
	iptables-module-xt-connmark \
	iptables-module-xt-sctp \
	iptables-module-ip6t-srh \
	iptables-module-xt-tcpmss \
	iptables-module-xt-dscp \
	iptables-module-ipt-ttl \
	iptables-module-ip6t-hl \
	iptables-module-xt-recent \
	iptables-module-xt-owner \
	iptables-module-xt-nfqueue \
	iptables-module-xt-multiport \
	iptables-module-xt-iprange \
	iptables-module-xt-ct \
	iptables-module-xt-connlimit \
	iptables-module-xt-u32 \
	iptables-module-xt-time \
	iptables-module-xt-tcp \
	iptables-module-xt-string \
	iptables-module-xt-policy \
	iptables-module-xt-limit \
	iptables-module-xt-ipvs \
	iptables-module-xt-hmark \
	iptables-module-xt-dccp \
	iptables-module-xt-connbytes \
	iptables-module-xt-bpf \
	iptables-module-xt-addrtype \
	iptables-module-ipt-reject \
	iptables-module-ipt-icmp \
	iptables-module-ip6t-rt \
	iptables-module-ip6t-mh \
	iptables-module-ip6t-ipv6header \
	iptables-module-ip6t-icmp6 \
	iptables-module-xt-tproxy \
	iptables-module-xt-socket \
	iptables-module-xt-cgroup \
	iptables-module-xt-udp \
	iptables-module-xt-trace \
	iptables-module-xt-tee \
	iptables-module-xt-tcpoptstrip \
	iptables-module-xt-synproxy \
	iptables-module-xt-statistic \
	iptables-module-xt-secmark \
	iptables-module-xt-rpfilter \
	iptables-module-xt-quota \
	iptables-module-xt-pkttype \
	iptables-module-xt-physdev \
	iptables-module-xt-osf \
	iptables-module-xt-nflog \
	iptables-module-xt-nfacct \
	iptables-module-xt-mac \
	iptables-module-xt-length \
	iptables-module-xt-led \
	iptables-module-xt-ipcomp \
	iptables-module-xt-idletimer \
	iptables-module-xt-helper \
	iptables-module-xt-esp \
	iptables-module-xt-ecn \
	iptables-module-xt-devgroup \
	iptables-module-xt-cpu \
	iptables-module-xt-connsecmark \
	iptables-module-xt-comment \
	iptables-module-xt-cluster \
	iptables-module-xt-classify \
	iptables-module-xt-checksum \
	iptables-module-xt-audit \
	iptables-module-ipt-ulog \
	iptables-module-ipt-realm \
	iptables-module-ipt-netmap \
	iptables-module-ipt-ecn \
	iptables-module-ipt-clusterip \
	iptables-module-ipt-ah \
	iptables-module-ip6t-snpt \
	iptables-module-ip6t-reject \
	iptables-module-ip6t-netmap \
	iptables-module-ip6t-hbh \
	iptables-module-ip6t-frag \
	iptables-module-ip6t-eui64 \
	iptables-module-ip6t-dst \
	iptables-module-ip6t-dnpt \
	iptables-module-ip6t-ah \
        iptables-module-ip6t-dnat \
        iptables-module-ip6t-log \
        iptables-module-ip6t-masquerade \
        iptables-module-ip6t-redirect \
        iptables-module-ip6t-snat \
        iptables-module-ipt-dnat \
        iptables-module-ipt-log \
        iptables-module-ipt-masquerade \
        iptables-module-ipt-redirect \
        iptables-module-ipt-snat \
        iptables-modules \
        iptables-module-xt-set \
    '
