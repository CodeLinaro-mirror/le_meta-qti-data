DEPENDS += "virtual/kernel"

FILESEXTRAPATHS_prepend_mdm := "${THISDIR}/files:"
SRC_URI_append_mdm = " \
        file://103-ubicom32-nattype_lib.patch \
"
#Leading space before cflag is compulsory. Otherwise it is getting added to existing flag.
CFLAGS_append_mdm = " -I${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_ipv4"
