SUMMARY = "ModemManager is a daemon controlling broadband devices/connections"
DESCRIPTION = "ModemManager is a DBus-activated daemon which controls mobile broadband (2G/3G/4G) devices and connections"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/ModemManager/"
LICENSE = "GPL-2.0-or-later & LGPL-2.1-or-later"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
"
GNOMEBASEBUILDCLASS = "meson"
inherit gnomebase gettext systemd gobject-introspection bash-completion

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "glib-2.0 libgudev libxslt-native dbus"

SRCREV ?= "9bf1095434a340d79ab1933a622ac971eedf7037"

# Patch 0001 will be in ModemManager > 1.19
SRC_URI = " \
    git://git.codelinaro.org/clo/rdkb/mobile-broadband/ModemManager.git;protocol=https;branch=rdkb/mm-1-20 \
"
SRC_URI[sha256sum] = "6b2c0a194a1a7ba1880dd4af01c86e28182285803edd7cae69628742b517695c"

SRC_URI_append += " \
    file://00001-service-update.patch \
    file://00003-libqrtr-timer-increase.patch \
    file://00004-QMAPV5-flag-check-enable.patch \
    file://00005-Fix-mtu-updation.patch \
    file://00006-ModemManager_qrtr_timer_update.patch \
    file://00007-Modem_is_not_found_bugfix.patch \
    file://00008-ModemManager_service_dependency_update.patch \
    file://00009-ModemManager_timer_optimization.patch \
"

S = "${WORKDIR}/git"
# strict, permissive
MODEMMANAGER_POLKIT_TYPE ??= "permissive"
PACKAGECONFIG ??= "vala qmi qrtr \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd polkit', d)} \
"
PACKAGECONFIG[at] = "-Dat_command_via_dbus=true"
PACKAGECONFIG[systemd] = " \
    -Dsystemdsystemunitdir=${systemd_unitdir}/system/, \
    -Dsystemdsystemunitdir=no -Dsystemd_journal=false -Dsystemd_suspend_resume=false \
"
PACKAGECONFIG[polkit] = "-Dpolkit=${MODEMMANAGER_POLKIT_TYPE},-Dpolkit=no,polkit"
# Support WWAN modems and devices which speak the Qualcomm MSM Interface (QMI) protocol.
PACKAGECONFIG[qmi] = "-Dqmi=true,-Dqmi=false,libqmi"
PACKAGECONFIG[qrtr] = "-Dqrtr=true,-Dqrtr=false,libqrtr-glib"
PACKAGECONFIG[vala] = "-Dvapi=true,-Dvapi=false"
PACKAGECONFIG[mbim] = "-Dmbim=false"

inherit ${@bb.utils.contains('PACKAGECONFIG', 'vala', 'vala', '', d)}
EXTRA_OEMESON = " \
    -Dudevdir=${nonarch_base_libdir}/udev \
    -Dqrtr=true \
    -Dmbim=false \
"
FILES:${PN} += " \
    ${datadir}/icons \
    ${datadir}/polkit-1 \
    ${datadir}/dbus-1 \
    ${datadir}/ModemManager \
    ${libdir}/ModemManager \
    ${systemd_unitdir}/system \
"

EXTRA_OECONF_append = " --enable-plugin-qcom-soc"

SYSTEMD_SERVICE:${PN} = "ModemManager.service"
