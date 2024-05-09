SUMMARY = "Thermal Mitigation"
DESCRIPTION = "Helper recipe to build Thermal mitigation out-of-tree or in devshell"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKSPACE}/data-eth/drivers/thermal-eth-netlink/thermal_util_netlink.c;\
beginline=1;endline=2;md5=e24bd042d70c476a624ba5e5798f8409"

inherit module
inherit qperf
inherit systemd
inherit autotools
inherit pkgconfig useradd

FILESPATH =+ "${WORKSPACE}/data-eth/drivers/:"
SRC_URI   = "file://thermal-eth-netlink/"
SRC_URI += "file://thermal-eth-netlink.service"
S = "${WORKDIR}/thermal-eth-netlink"

DEPENDS = "qmi-framework glib-2.0 common-headers libcutils libnl linux-msm-headers"

EXTRA_OECONF  = "--with-qmi-framework --with-glib"
EXTRA_OECONF += "--with-netlink-framework"
EXTRA_OECONF += "--with-sanitized-headers=${STAGING_INCDIR}/linux-msm/usr/include"
EXTRA_OECONF += "--enable-target-${BASEMACHINE}=yes"

SYSTEMD_SERVICE:${PN} = "thermal-eth-netlink.service"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/build/thermal-eth-netlink ${D}${bindir}/thermal-eth-netlink

	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/thermal-eth-netlink.service -D \
		${D}${systemd_system_unitdir}/thermal-eth-netlink.service
}

RPROVIDES:${PN} += "kernel-module-thermal-eth-netlink"
FILES:${PN}     += "${bindir}/thermal-eth-netlink"
FILES:${PN} += "${systemd_system_unitdir}"
