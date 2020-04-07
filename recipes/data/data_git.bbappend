FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI  += "file://qcmap_wlan.service"
SRC_URI  += "file://qcmap_wpa_cli@.service"
SRC_URI  += "file://qcmap_wpa_cli_status@.service"
SRC_URI  += "file://qcmap_wpa_cli_setband@.service"
SRC_URI  += "file://qcmap_wpa_supplicant@.service"
SRC_URI  += "file://qcmap_hostapd_cli@.service"
SRC_URI  += "file://qcmap_hostapd@.service"
SRC_URI  += "file://start_qcmap_wlan_le"
SRC_URI  += "file://start_qcmap_wpa_supplicant_le"
SRC_URI  += "file://start_qcmap_hostapd_le"
SRC_URI  += "file://qcmap_load_module@.service"

# MACSEC support
SRC_URI  += "file://mka-supplicant@.service"
SRC_URI  += "file://mka-authenticator@.service"
SRC_URI  += "file://wpa_supplicant-eth0.conf"
SRC_URI  += "file://hostapd-eth0.conf"

do_install_append() {
        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
            install -d ${D}${systemd_unitdir}/system/
            install -d ${D}${sysconfdir}/initscripts

            #WLAN service
            install -m 0755 ${WORKDIR}/start_qcmap_wlan_le ${D}${sysconfdir}/initscripts
            install -m 0644 ${WORKDIR}/qcmap_wlan.service -D ${D}${systemd_unitdir}/system/qcmap_wlan.service

            #WPA_CLI Service
            install -m 0644 ${WORKDIR}/qcmap_wpa_cli@.service -D ${D}${systemd_unitdir}/system/qcmap_wpa_cli@.service

            #WPA_CLI_STATUS Service
            install -m 0644 ${WORKDIR}/qcmap_wpa_cli_status@.service -D ${D}${systemd_unitdir}/system/qcmap_wpa_cli_status@.service

            #WPA_CLI_SETBAND Service
            install -m 0644 ${WORKDIR}/qcmap_wpa_cli_setband@.service -D ${D}${systemd_unitdir}/system/qcmap_wpa_cli_setband@.service

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

            # MKA (MACSEC) Supplicant and Authenticator services
            install -m 0644 ${WORKDIR}/mka-supplicant@.service -D ${D}${systemd_system_unitdir}/mka-supplicant@.service
            install -m 0644 ${WORKDIR}/mka-authenticator@.service -D ${D}${systemd_system_unitdir}/mka-authenticator@.service

            # MKA supplicant and authenticator configuration for eth0
            install -m 0644 ${WORKDIR}/wpa_supplicant-eth0.conf -D ${D}${sysconfdir}/data/wpa_supplicant-eth0.conf
            install -m 0644 ${WORKDIR}/hostapd-eth0.conf -D ${D}${sysconfdir}/data/hostapd-eth0.conf
        fi
}

# vim: syntax=bitbake
