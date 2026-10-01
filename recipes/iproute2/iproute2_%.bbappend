FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://iproute2_config_to_data.patch"
SRC_URI += "file://0001-libc-compat.h-add-musl-workaround.patch"

do_install:append() {
    install -d ${D}${sysconfdir}/data/iproute2
    install -m 0644 ${D}${libdir}/iproute2/* ${D}${sysconfdir}/data/iproute2/
}

FILES:${PN} += "${sysconfdir}/data/iproute2"

pkg_postinst:${PN}() {
    chown -Rh 1001:1001 $D${sysconfdir}/data/iproute2
}
