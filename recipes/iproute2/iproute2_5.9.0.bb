require iproute2.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/utils/net/${BPN}/${BP}.tar.xz \
           file://0001-libc-compat.h-add-musl-workaround.patch \
          "

SRC_URI[md5sum] = "3966f12374e1beb70f1c21f30a350673"
SRC_URI[sha256sum] = "a25dac94bcdcf2f73316c7f812115ea7a5710580bad892b08a83d00c6b33dacf"

# CFLAGS are computed in Makefile and reference CCOPTS
#
EXTRA_OEMAKE_append = " CCOPTS='${CFLAGS}'"
