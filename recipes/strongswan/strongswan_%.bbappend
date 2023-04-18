DEPENDS:append = " libcap"
SRC_URI:append = " file://qti"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Enable swanctl command which is required to load connections by QNCM
PACKAGECONFIG:append = " swanctl systemd-charon"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--home /usr/sbin --no-create-home --shell /bin/false --user-group charon"

EXTRA_OECONF:append = " --with-capabilities=libcap --with-user=charon --with-group=charon"

do_install_append() {
	rm ${D}${systemd_unitdir}/system/strongswan.service
	install -m 0644 ${WORKDIR}/qti/strongswan.service -D ${D}${systemd_unitdir}/system/strongswan.service
}

SYSTEMD_SERVICE_${PN} = "strongswan.service"

