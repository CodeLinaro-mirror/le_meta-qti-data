FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"


SRC_URI:append = " \
    file://system_defaults \
    file://00001-qti-utopia_brlan0_bring_up.patch \
    file://00002-ethlan_utopia.patch \
    file://00021-Utopia-DNSProxy-bringup.patch \
"
