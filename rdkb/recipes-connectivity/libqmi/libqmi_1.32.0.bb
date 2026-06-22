SUMMARY = "libqmi is a library for talking to WWAN devices by QMI protocol"
DESCRIPTION = "libqmi is a glib-based library for talking to WWAN modems and devices which speak the Qualcomm MSM Interface (QMI) protocol"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/libqmi"

LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = " \
    file://${S}/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://${S}/COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
"
inherit meson pkgconfig gobject-introspection

DEPENDS = "glib-2.0 glib-2.0-native libgudev"
DEPENDS=" \
  libxml2 \
  glib-2.0 \
  glib-2.0-native \
  libgudev \
  gtk-doc \
  intltool-native \
  libqrtr-glib \
"
FILES:${PN} += "/usr/share/bash-completion"
S = "${WORKDIR}/git"
SRCREV = "d0973775d2bce93fb927ce7bc6c61385b146f54d"
SRC_URI = "git://git.codelinaro.org/clo/rdkb/mobile-broadband/libqmi.git;protocol=https;branch=rdkb/qmi-1-32"

SRC_URI_append += " \
    file://00002-qmi_retry_mechanism_for_eagain_failure.patch \
    file://00001-support_for_xlat.patch \
"
PACKAGECONFIG ??= "udev qrtr"
PACKAGECONFIG[qrtr] = "-Dqrtr=true,-Dqrtr=false,libqrtr-glib"
PACKAGECONFIG[help2man] = "-Dman=true,-Dman=false,help2man"
PACKAGECONFIG[mbim] = "-Dmbim_qmux=false"

EXTRA_OEMESON = " \
    -Dqrtr=true \
    -Dman=false \
    -Dmbim_qmux=false \
"

EXTRA_OECONF_append_toolchain-clang = " --enable-more-warnings=no"


