SRC_URI_remove = "file://verbose.patch"
SRC_URI_remove = "file://revsshipv6.patch"
SRC_URI_remove = " file://CVE-2021-36369_fix.patch"
SYSTEMD_SERVICE_${PN}_remove_broadband = "dropbear.socket"

FILES_${PN} += "${systemd_system_unitdir}/dropbearkey.service \
                ${systemd_system_unitdir}/dropbear@.service \
                ${systemd_system_unitdir}/dropbear.socket"
