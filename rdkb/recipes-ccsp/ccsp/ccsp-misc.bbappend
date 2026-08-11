FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

require ccsp_common.inc

SRC_URI += "file://00001-misc-dhcpv6-stateless.patch"

DEPENDS += "utopia-headers"

CFLAGS += " -DDHCPV4_CLIENT_UDHCPC -DUDHCPC_RUN_IN_BACKGROUND"
CFLAGS_append += " -Wno-error=stringop-truncation -Wno-error=stringop-overflow -Wno-error=format-security "

LDFLAGS_append_aarch64 = " -lutctx"
