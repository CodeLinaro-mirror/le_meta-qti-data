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
    data-ipa-cfg-mgr \
    dataipa \
    data-eth \
    ethtool \
    tcpdump \
    conntrack-tools \
    rtsp-alg \
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
    lighttpd \
    libgpiod \
    phytool \
    data-rc \
    '
