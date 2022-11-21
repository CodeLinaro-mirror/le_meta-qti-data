FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
        file://Makefile.patch \
"

DEPENDS += "virtual/kernel"

EXTRA_OEMAKE += "KERNEL_INCLUDES=${STAGING_KERNEL_BUILDDIR}/usr/include"

EXTRA_OECONF = "--without-crypto \
        ${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', '--enable-ipv6', '--disable-ipv6', d)}"

FILES_${PN}     += "${libdir}/lib*.so"
FILES_${PN}     += "${sbindir}/*"
FILES_${PN}-doc += "${mandir}/*"

do_configure_append() {
    if [ -d "${S}" ]; then
        if [ ! -d "${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/" ]; then
            mkdir -p ${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/
        fi
        install -m 555 ${S}/include/linux/netfilter_bridge/ebt_ulog.h ${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/ebt_ulog.h
    fi
}

do_install_append () {
      rm -f ${D}${systemd_unitdir}/system/ebtables.service
}

SYSTEMD_SERVICE_${PN} = ""
