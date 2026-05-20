FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " \
    file://system_defaults \
    file://00001-qti-utopia_brlan0_bring_up.patch \
    file://00002-ethlan_utopia.patch \
    file://00003-Utopia-DNSProxy-bringup.patch \
    file://00004-firewall-macro.patch \
    file://00005-qti-utopia-Embedded_Downlink_fix.patch \
"
