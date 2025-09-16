DEPENDS += "virtual/kernel"

FILESEXTRAPATHS:prepend:mdm := "${THISDIR}/files:"
SRC_URI:append:mdm = " \
        file://103-ubicom32-nattype_lib.patch \
"
#Leading space before cflag is compulsory. Otherwise it is getting added to existing flag.
CFLAGS:append:mdm = " -I${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_ipv4"