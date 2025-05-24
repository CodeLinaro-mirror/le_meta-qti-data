# Copyright (C) 2017 Aaron Brice <aaron.brice@datasoft.com>
# Released under the MIT license

DESCRIPTION = "Administration tool for IP sets"
HOMEPAGE = "http://ipset.netfilter.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
SECTION = "net"

DEPENDS = "libtool libmnl"

SRC_URI = "http://ipset.netfilter.org/ipset-7.23.tar.bz2"
SRC_URI[md5sum] = "2742dd36ba721f3d304799160fef78ab"
SRC_URI[sha256sum] = "db3a51a9ebf27c7cbd0a1482c46c5e0ed630c28c796f73287c4b339dd46086e5"

inherit autotools pkgconfig module-base

EXTRA_OECONF += "-with-kbuild=${KBUILD_OUTPUT} --with-ksource=${STAGING_KERNEL_DIR}"
