FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

require ccsp_common.inc

SRC_URI += " \
    file://0001-CcspHotspot-Fix-strncpy-stringop-truncation-in-HotspotApi.patch \
"
CFLAGS_append += " -Wno-error=pointer-to-int-cast -Wno-error=stringop-truncation "
