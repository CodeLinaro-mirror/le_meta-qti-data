FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += " \
        file://103-ubicom32-nattype_lib.patch \
"

CFLAGS += "-I${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_ipv4"
