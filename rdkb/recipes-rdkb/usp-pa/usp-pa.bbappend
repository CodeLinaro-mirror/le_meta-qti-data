FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://00001_qti-usp-pa.patch \
                   file://00002_qti-usp-pa.patch;patchdir=../../.. \
                   file://00003_qti-usp-pa.patch;patchdir=../../../conf \
                   file://00004_qti-usp-pa.patch;patchdir=../../../conf \
"
DEPENDS_append = " utopia "

LDFLAGS += "-L${STAGING_LIBDIR} -lsysevent"

CFLAGS_append = "-D_PLATFORM_SDX_"

