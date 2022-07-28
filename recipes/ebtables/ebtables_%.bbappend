FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
        file://Makefile.patch \
"

EXTRA_OEMAKE += "KERNEL_INCLUDES=${STAGING_KERNEL_BUILDDIR}/usr/include"

EXTRA_OECONF = "--without-crypto \
        ${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', '--enable-ipv6', '--disable-ipv6', d)}"

FILES_${PN}     += "${libdir}/lib*.so"
FILES_${PN}     += "${sbindir}/*"
FILES_${PN}-doc += "${mandir}/*"

do_configure_append() {
    if [ -d "${S}" ]; then
        install -m 555 ${S}/include/linux/netfilter_bridge/ebt_ulog.h ${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/ebt_ulog.h
    fi
}
