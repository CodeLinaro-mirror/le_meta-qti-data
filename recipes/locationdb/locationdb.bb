DESCRIPTION = "Location Database used in ZoneDetect to figure out the timezone based on GPS Location"
HOMEPAGE = "https://github.com/BertoldVdb/ZoneDetect/tree/master/database"
LICENSE = "ODbl-1.0"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/DATA_LICENSE;md5=d81bbfe0c1b33b6d2072e820e2482562"
SECTION = "utils"
PR = "r1"

SRC_URI = "https://mirrors.edge.kernel.org/caf_mirrored_source/quic/le/db-2019c.zip;name=locdb \
           file://DATA_LICENSE"

SRC_URI[locdb.md5sum] = "8ffbf97144f0bc827c6bd0c08a84328c"

do_install () {
  install -d ${D}${base_bindir}
  install -m 0666 ${UNPACKDIR}/out_v1/timezone21.bin ${D}${base_bindir}
}
