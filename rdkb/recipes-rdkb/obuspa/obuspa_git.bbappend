FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://00001_qti-obuspa-TR369.patch"

CFLAGS_append = " -D_PLATFORM_SDX_"
