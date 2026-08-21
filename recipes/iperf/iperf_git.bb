SUMMARY = "iPerf - The ultimate speed test tool for TCP, UDP and SCTP"
DESCRIPTION = "iperf is a tool for active measurements of the maximum achievable \
bandwidth on IP networks.  It supports tuning of various parameters \
related to timing, protocols, and buffers.  For each test it reports \
the bandwidth, loss, and other parameters."
HOMEPAGE = "http://software.es.net/iperf/"

SECTION = "utils"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f2eb355b6d3b9d63b6b7a861cdc62440"

inherit autotools

PV = "3.21+git${SRCPV}"

SRC_URI = "git://github.com/esnet/iperf.git;protocol=https;branch=master"

SRCREV = "d39cf41526626b4e5a130f115d931cd6cbdffc19"

S = "${WORKDIR}/git"

EXTRA_OECONF = "--with-openssl=no"

RPROVIDES_${PN} += "iperf3"

do_compile:prepend () {
 cd ${S}
}

do_install:prepend () {
 cd ${S}
}

do_configure:prepend () {
#       damn picky automake...
        cd ${S}
        touch NEWS README AUTHORS COPYING ChangeLog
}
