SUMMARY = "DATA open source package groups"
LICENSE = "BSD-3-Clause"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = ' \
    packagegroup-qti-data \
    '

RDEPENDS_packagegroup-qti-data = ' \
    avahi-daemon \
    avahi-dnsconfd \
    avahi-autoipd \
    avahi-utils \
    avahi-systemd \
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
    libavahi-common \
    libavahi-core \
    libavahi-client \
    libavahi-glib \
    tinyproxy \
    ppp \
    rtsp-alg \
    pimd \
    minidlna \
    miniupnpd \
    lighttpd \
    iputils \
    jquery \
    jquery-ui \
    strace \
    '
