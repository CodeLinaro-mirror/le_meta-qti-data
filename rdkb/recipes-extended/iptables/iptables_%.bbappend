FILESEXTRAPATHS_prepend:="${THISDIR}/files:"

SRC_URI_append = "file://00001-add-port-triggering-support.patch"

DEPENDS += "make-mod-scripts"
