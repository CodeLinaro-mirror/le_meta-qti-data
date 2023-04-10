inherit autotools-brokensep pkgconfig qprebuilt systemd useradd
DESCRIPTION = "pmipv6"
SECTION = "console/network"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS = "virtual/kernel  linux-msm-headers libnfnetlink libpcap libcap configdb libcutils libsystemdq bison-native"
do_configure[depends] += "virtual/kernel:do_shared_workdir "
inherit qcommon

S = "${WORKDIR}/pmipv6"
SRC_DIR =  "${WORKSPACE}/pmipv6/"

RDEPENDS_${PN} += "libsystemdq"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI +="file://pmip_service.service"
SRC_URI +="file://pmip6d_exec"
PR = "r0"

FILES_${PN} += "/lib/systemd/*"

EXTRA_OECONF += "--host arm-linux-gnueabi"
EXTRA_OECONF += "--with-builtin-crypto"
do_configure_prepend() {
    cp ${S}/src/pmgr.c.in ${S}/src/pmgr.c
    cp ${S}/src/pmgr.h.in ${S}/src/pmgr.h
    bison -d ${S}/src/gram.y
    mv ${S}/gram.tab.c ${S}/src/gram.c
    mv ${S}/gram.tab.h ${S}/src/gram.h
}


do_install_append() {
install -d ${D}/usr/sbin ${D}/data/
install -d ${D}/etc/systemd/system/
install -d ${D}${systemd_unitdir}/system/
install -d ${D}${sysconfdir}/initscripts
install -m 0777 ${WORKDIR}/pmip6d_exec ${D}${sysconfdir}/initscripts
install -m 0777 ${WORKDIR}/pmip_service.service -D ${D}${systemd_unitdir}/system/pmip_service.service
install -m 0777 ${S}/extras/example-mag1.conf -D ${D}${sysconfdir}/data/pmip-mag.conf
}
FILES_${PN} += "${sysconfdir}/data/pmip-mag.conf"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN} = "file-rdeps"
