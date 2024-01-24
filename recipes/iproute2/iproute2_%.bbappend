FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://iproute2_config_to_data.patch"

do_install_append() {
  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
    chown -Rh 1001:1001 ${D}${sysconfdir}/data/iproute2
  fi
  install -d ${D}${includedir}
  install -d ${D}${libdir}
  install -m 0755 ${S}/include/libnetlink.h ${D}${includedir}
  install -m 0755 ${S}/lib/libnetlink.a ${D}${libdir}
}
