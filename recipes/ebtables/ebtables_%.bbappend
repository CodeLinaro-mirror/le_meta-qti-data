FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
        file://Makefile.patch \
"

DEPENDS += "virtual/kernel linux-msm-headers"

EXTRA_OEMAKE += "KERNEL_INCLUDES=${STAGING_INCDIR}/linux-msm/usr/include"
EXTRA_OEMAKE += "CFLAGS+="-I${S}/include""

#EXTRA_OECONF = "--without-crypto \
#        ${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', '--enable-ipv6', '--disable-ipv6', d)}"

FILES:${PN}     += "${libdir}/lib*.so"
FILES:${PN}     += "${sbindir}/*"
FILES:${PN}-doc += "${mandir}/*"

do_configure:append() {
    if [ -d "${S}" ]; then
        if [ ! -d "${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/" ]; then
            mkdir -p ${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/
        fi
        install -m 555 ${S}/include/linux/netfilter_bridge/ebt_ulog.h ${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/ebt_ulog.h
    fi
}

do_install:append () {
      rm -f ${D}${systemd_unitdir}/system/ebtables.service
}

SYSTEMD_SERVICE:${PN} = ""

INSANE_SKIP:${PN} += "installed-vs-shipped"
