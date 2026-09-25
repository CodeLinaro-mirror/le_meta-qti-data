DEPENDS += "virtual/kernel"

# sa535m: build iptables against nftables (libnftnl) so the iptables/ip6tables
# binaries symlink to xtables-nft-multi instead of the legacy xtables backend.
PACKAGECONFIG:append:sa535m = " libnftnl"

# The upstream iptables/Makefile.am install-exec-hook always symlinks both
# iptables and ip6tables to xtables-legacy-multi; the recipe's do_install:append
# only re-points iptables at xtables-nft-multi, leaving ip6tables on the legacy
# backend even with libnftnl enabled. Fix up ip6tables the same way.
do_install:append:sa535m() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'libnftnl', 'true', 'false', d)} ; then
        ln -sf ${sbindir}/xtables-nft-multi ${D}${sbindir}/ip6tables
    fi
}

FILESEXTRAPATHS:prepend:mdm := "${THISDIR}/files:"
SRC_URI:append:mdm = " \
        file://103-ubicom32-nattype_lib.patch \
"
#Leading space before cflag is compulsory. Otherwise it is getting added to existing flag.
CFLAGS:append:mdm = " -I${STAGING_KERNEL_BUILDDIR}/usr/include/linux/netfilter_ipv4"