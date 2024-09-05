inherit autotools pkgconfig qprebuilt systemd useradd autotools-brokensep
DESCRIPTION = "pmipv6"
SECTION = "console/network"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS = "virtual/kernel  linux-msm-headers libnfnetlink libpcap libcap configdb libcutils libsystemdq bison-native glib-2.0"
do_configure[depends] += "virtual/kernel:do_shared_workdir "

S = "${WORKDIR}/pmipv6"

RDEPENDS_${PN} += "libsystemdq"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://pmipv6/"


SRC_URI +="file://pmip_service.service"
SRC_URI +="file://pmip6d_exec"
PR = "r0"

FILES_${PN} += "/lib/systemd/*"

EXTRA_OECONF += "--host arm-linux-gnueabi"
EXTRA_OECONF += "--with-builtin-crypto"
EXTRA_OECONF += "--with-glib"

do_configure_prepend() {
    cp ${WORKDIR}/pmipv6/src/pmgr.c.in ${WORKDIR}/pmipv6/src/pmgr.c
    cp ${WORKDIR}/pmipv6/src/pmgr.h.in ${WORKDIR}/pmipv6/src/pmgr.h
    bison -d ${WORKDIR}/pmipv6/src/gram.y
    mv ${WORKDIR}/pmipv6/gram.tab.c ${WORKDIR}/pmipv6/src/gram.c
    mv ${WORKDIR}/pmipv6/gram.tab.h ${WORKDIR}/pmipv6/src/gram.h
}


do_install_append() {
install -d ${D}/usr/sbin ${D}/data/
install -d ${D}/etc/systemd/system/
install -d ${D}${systemd_unitdir}/system/
install -d ${D}${sysconfdir}/initscripts
install -m 0777 ${WORKDIR}/pmip6d_exec ${D}${sysconfdir}/initscripts
install -m 0777 ${WORKDIR}/pmip_service.service -D ${D}${systemd_unitdir}/system/pmip_service.service
install -m 0777 ${WORKDIR}/pmipv6/extras/example-mag1.conf -D ${D}${sysconfdir}/data/pmip-mag.conf
}
FILES_${PN} += "${sysconfdir}/data/pmip-mag.conf"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN} = "file-rdeps"
