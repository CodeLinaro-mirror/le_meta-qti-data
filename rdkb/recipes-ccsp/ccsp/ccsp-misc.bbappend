FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

require ccsp_common.inc

DEPENDS += "utopia-headers"

CFLAGS += " -DDHCPV4_CLIENT_UDHCPC -DDHCPV6_CLIENT_DIBBLER -DUDHCPC_RUN_IN_BACKGROUND"
CFLAGS_append += " -Wno-error=stringop-truncation -Wno-error=stringop-overflow -Wno-error=format-security "

LDFLAGS_append_aarch64 = " -lutctx"
