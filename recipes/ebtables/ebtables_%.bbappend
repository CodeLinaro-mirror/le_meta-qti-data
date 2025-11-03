FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
        file://configure.patch \
"

DEPENDS += "virtual/kernel"

EXTRA_OEMAKE += "KERNEL_INCLUDES=${STAGING_KERNEL_BUILDDIR}/usr/include"

EXTRA_OECONF = "--without-crypto \
        ${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', '--enable-ipv6', '--disable-ipv6', d)}"

FILES:${PN}     += "${libdir}/lib*.so"
FILES:${PN}     += "${sbindir}/*"
FILES:${PN}-doc += "${mandir}/*"
FILES:${PN}     += "${base_libdir}/*"
do_configure:append() {
    if [ -d "${S}" ]; then
        install -m 555 ${S}/include/linux/netfilter_bridge/ebt_ulog.h ${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_bridge/ebt_ulog.h
    fi
}

do_install:append () {
      rm -f ${D}${systemd_unitdir}/system/ebtables.service
}

SYSTEMD_SERVICE:${PN} = ""
ERROR_QA:remove = "unknown-configure-option"
