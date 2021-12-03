inherit useradd
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
        file://001_Makefile.patch \
        file://001-tiny_config.patch \
        file://tinyproxyd.service \
        "


do_install_append() {
  install -d ${D}${sysconfdir}/data/
  install -m 644 ${WORKDIR}/image/etc/tinyproxy.conf ${D}${sysconfdir}/data/
  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
      install -d ${D}${systemd_unitdir}/system/
      install -m 0644 ${WORKDIR}/tinyproxyd.service -D ${D}${systemd_unitdir}/system/tinyproxyd.service
  fi
  chown -R root:1001 ${D}${sysconfdir}/data/tinyproxy.conf
}
FILES_${PN} += "${sysconfdir}/data/tinyproxy.conf"
FILES_${PN} += "${systemd_unitdir}/system/*"
