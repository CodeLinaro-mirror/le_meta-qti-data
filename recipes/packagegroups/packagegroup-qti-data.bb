SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    packagegroup-qti-data-1g \
    '

RDEPENDS_packagegroup-qti-data = ' \
    iproute2 \
    iptables \
    ebtables \
    dhcpcd \
    ddclient \
    dnsmasq \
    data-ipa-cfg-mgr \
    ethtool \
    tcpdump \
    conntrack-tools \
    data-oss \
    datarmnet \
    ppp \
    rtsp-alg \
    pimd \
    iputils \
    jquery \
    jquery-ui \
    strace \
    sfe \
    gsb \
    locationdb \
    zonedetect \
    ianatzdata \
    qps615 \
    '

RDEPENDS_packagegroup-qti-data-1g = ' \
    avahi-daemon \
    avahi-dnsconfd \
    avahi-autoipd \
    avahi-utils \
    avahi-systemd \
    libavahi-common \
    libavahi-core \
    libavahi-client \
    libavahi-glib \
    tinyproxy \
    minidlna \
    miniupnpd \
    lighttpd \
    '
