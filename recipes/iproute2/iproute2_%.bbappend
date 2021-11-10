FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://iproute2_config_to_data.patch"

do_install_append() {
  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
    chown -Rh root:root ${D}${sysconfdir}/data/iproute2
  fi
}
