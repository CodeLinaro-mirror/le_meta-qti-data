SECTION = "console/network"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DESCRIPTION = "TinyProxy"

PR = "r1"

SRC_URI = " \
        https://github.com/banu/tinyproxy/archive/${PV}.zip \
        file://001_Makefile.patch \
        file://001-tiny_config.patch \
        file://tinyproxyd.service \
        "

inherit autotools pkgconfig

EXTRA_OECONF = " \
        --enable-regexcheck=no \
        --enable-xtinyproxy=no \
        --enable-transparent=yes"

SRC_URI[md5sum] = "c88819d19a69bbe7c1d3572944f813c4"
SRC_URI[sha256sum] = "27c30690a23670f62a128fd1ec918274de5974e7166a5259f2eeecf9f3f31e4a"

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
