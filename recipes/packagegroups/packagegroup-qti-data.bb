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
    ethtool \
    tcpdump \
    conntrack-tools \
    data-oss \
    libavahi-common \
    libavahi-core \
    libavahi-client \
    libavahi-glib \
    tinyproxy \
    ppp \
    lighttpd \
    iputils \
    jquery \
    jquery-ui \
    strace \
    '
