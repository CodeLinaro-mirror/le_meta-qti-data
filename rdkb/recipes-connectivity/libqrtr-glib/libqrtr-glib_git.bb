SUMMARY = "library to use and manage the QRTR bus"
DESCRIPTION = "libqrtr-glib is a glib-based library to use and manage the QRTR (Qualcomm IPC Router) bus"
HOMEPAGE = "https://gitlab.freedesktop.org/mobile-broadband/libqrtr-glib"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://LICENSES/LGPL-2.1-or-later.txt;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://git.codelinaro.org/clo/rdkb/mobile-broadband/libqrtr-glib.git;protocol=https;branch=rdkb/qrtr-1-2"
SRC_URI += "file://00001-QMIProxy-Crash-Fix.patch"

SRC_URI[sha256sum] = "8627ffd6b70df34c18c61c902f288474dc3e7919f788fbe409ee0016ed14d109"

PV = "1.2.2+git${SRCPV}"
SRCREV = "8991f0e93713ebf4da48ae4f23940ead42f64c8c"

S = "${WORKDIR}/git"

inherit meson pkgconfig gobject-introspection

DEPENDS = "glib-2.0"

EXTRA_OEMESON = " \
    -Dgtk_doc=false \
"
