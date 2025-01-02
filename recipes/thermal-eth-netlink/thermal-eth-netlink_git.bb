SUMMARY = "Thermal Mitigation"
DESCRIPTION = "Helper recipe to build Thermal mitigation out-of-tree or in devshell"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${WORKSPACE}/data-eth/bin/thermal-eth-netlink/thermal_util_netlink.c;\
beginline=1;endline=2;md5=08b5dca7151415fc526363b62898e94b"

inherit module
inherit qperf
inherit systemd
inherit autotools
inherit pkgconfig useradd

FILESPATH =+ "${WORKSPACE}/data-eth/bin/:"
SRC_URI   = "file://thermal-eth-netlink/"
SRC_URI += "file://thermal-eth-netlink.service"
S = "${WORKDIR}/thermal-eth-netlink"

DEPENDS = "qmi-framework glib-2.0 common-headers libcutils libnl linux-msm-headers"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--home /usr/bin --no-create-home --shell /bin/false --user-group powermgr"

EXTRA_OECONF  = "--with-qmi-framework  --with-glib"
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
