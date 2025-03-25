DEPENDS += "virtual/kernel"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
        file://103-ubicom32-nattype_lib.patch \
	file://101-ubicom32_porttrigger_lib.patch \
	file://xtables.patch \
"
#Leading space before cflag is compulsory. Otherwise it is getting added to existing flag.
CFLAGS_append = " -I${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_ipv4"

do_install_append () {
      rm -f ${D}${systemd_unitdir}/system/ip6tables.service
          rm -f ${D}${systemd_unitdir}/system/iptables.service
}

SYSTEMD_SERVICE_${PN} = ""
