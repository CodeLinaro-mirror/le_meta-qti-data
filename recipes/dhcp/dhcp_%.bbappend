FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://dhcpv6_service@.service"

EXTRA_OEMAKE = "CC='${CC}' \
                CFLAGS='${TARGET_CFLAGS}' \
                LDFLAGS='${TARGET_LDFLAGS}'"

do_install_append () {
  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
    install -d ${D}/etc/systemd/system/
    install -d ${D}/etc/systemd/system/multi-user.target.wants/
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/dhcpv6_service@.service -D ${D}${systemd_unitdir}/system/dhcpv6_service@.service
  fi
}

FILES_${PN} += "${systemd_unitdir}/system/*"

#SYSTEMD_SERVICE_${PN} = ""

SYSTEMD_SERVICE_${PN}-server += "dhcpv6_service@.service"
