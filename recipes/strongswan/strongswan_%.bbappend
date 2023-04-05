
SRC_URI:append = " file://qti"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Enable swanctl command which is required to load connections by QNCM
PACKAGECONFIG:append = " swanctl systemd-charon"

do_install_append() {
	rm ${D}${systemd_unitdir}/system/strongswan.service
	install -m 0644 ${WORKDIR}/qti/strongswan.service -D ${D}${systemd_unitdir}/system/strongswan.service
}

SYSTEMD_SERVICE_${PN} = "strongswan.service"

