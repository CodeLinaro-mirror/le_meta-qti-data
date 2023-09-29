DEPENDS += "virtual/kernel"

FILESEXTRAPATHS_prepend_mdm := "${THISDIR}/files:"
SRC_URI_append_mdm = " \
        file://103-ubicom32-nattype_lib.patch \
"

CFLAGS_append_mdm = "-I${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_ipv4"

do_install_append () {
    rm -f ${D}${systemd_unitdir}/system/ip6tables.service
    rm -f ${D}${systemd_unitdir}/system/iptables.service
}

SYSTEMD_SERVICE_${PN} = ""
