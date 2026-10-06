FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://00001_qti-usp-pa.patch \
                   file://00002_qti-usp-pa.patch;patchdir=../../.. \
                   file://00003_qti-usp-pa.patch;patchdir=../../../conf \
                   file://00004_qti-usp-pa.patch;patchdir=../../../conf \
                   file://00005_qti-usp-pa-perf-image-compilation-fix.patch \
"
DEPENDS_append = " utopia "

LDFLAGS += "-L${STAGING_LIBDIR} -lsysevent"

CFLAGS_append = "-D_PLATFORM_SDX_"

