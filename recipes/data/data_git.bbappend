FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI  += "file://qcmap_wlan.service"
SRC_URI  += "file://qcmap_wpa_cli@.service"
SRC_URI  += "file://qcmap_wpa_supplicant@.service"
SRC_URI  += "file://qcmap_hostapd_cli@.service"
SRC_URI  += "file://qcmap_hostapd@.service"
SRC_URI  += "file://start_qcmap_wlan_le"
SRC_URI  += "file://start_qcmap_wpa_supplicant_le"
SRC_URI  += "file://start_qcmap_hostapd_le"
SRC_URI  += "file://qcmap_load_module@.service"

do_install_append() {
        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
            install -d ${D}${systemd_unitdir}/system/
            install -d ${D}${sysconfdir}/initscripts

            #WLAN service
            #auto-wlan.service file will take care of the Auto usecases
         if (test "x${BASEPRODUCT}" != "xauto"); then
            install -m 0755 ${WORKDIR}/start_qcmap_wlan_le ${D}${sysconfdir}/initscripts
            install -m 0644 ${WORKDIR}/qcmap_wlan.service -D ${D}${systemd_unitdir}/system/qcmap_wlan.service
         fi

            #WPA_CLI Service
            install -m 0644 ${WORKDIR}/qcmap_wpa_cli@.service -D ${D}${systemd_unitdir}/system/qcmap_wpa_cli@.service

            #WPA_SUPPLICANT Service
            install -m 0755 ${WORKDIR}/start_qcmap_wpa_supplicant_le ${D}${sysconfdir}/initscripts
            install -m 0644 ${WORKDIR}/qcmap_wpa_supplicant@.service -D ${D}${systemd_unitdir}/system/qcmap_wpa_supplicant@.service

            #HOSTAPD_CLI Service
            install -m 0644 ${WORKDIR}/qcmap_hostapd_cli@.service -D ${D}${systemd_unitdir}/system/qcmap_hostapd_cli@.service

            #HOSTAPD Service
            install -m 0755 ${WORKDIR}/start_qcmap_hostapd_le ${D}${sysconfdir}/initscripts
            install -m 0644 ${WORKDIR}/qcmap_hostapd@.service -D ${D}${systemd_unitdir}/system/qcmap_hostapd@.service

            #Load modules from qcmap
            install -m 0644 ${WORKDIR}/qcmap_load_module@.service -D ${D}${systemd_unitdir}/system/qcmap_load_module@.service
        fi
}
