require iproute2.inc

SRC_URI = "${KERNELORG_MIRROR}/linux/utils/net/${BPN}/${BP}.tar.xz \
           file://0001-libc-compat.h-add-musl-workaround.patch \
          "

SRC_URI[md5sum] = "f3ff4461e25dbc5ef1fb7a9167a9523d"
SRC_URI[sha256sum] = "5ce12a0fec6b212725ef218735941b2dab76244db7e72646a76021b0537b43ab"

# CFLAGS are computed in Makefile and reference CCOPTS
#
EXTRA_OEMAKE_append = " CCOPTS='${CFLAGS}'"
