# Kernel config fragment for data layer netfilter features.
# Included by linux-msm-src_%.bbappend and linux-qcom-custom_%.bbappend
# so the same options are applied regardless of which kernel recipe is used.
#
# Enables:
#   - CONFIG_NF_CONNTRACK               : core connection tracking engine
#   - CONFIG_NF_CONNTRACK_EVENTS        : event notifications (required by conntrack -E)
#   - CONFIG_NF_CONNTRACK_CHAIN_EVENTS  : notifier chain enabling NF_CT_NETLINK to
#                                         receive and deliver conntrack events to userspace
#   - CONFIG_NF_CONNTRACK_MARK          : connmark field in conntrack entries
#   - CONFIG_NFNETLINK                  : netlink interface for netfilter (conntrack utility)
#   - CONFIG_NF_CONNTRACK_NETLINK       : exposes conntrack table to userspace
#   - CONFIG_NET_NS                     : network namespaces (ip netns)
#   - CONFIG_NF_NAT + helpers           : NAT engine and protocol helpers (FTP/SIP/TFTP etc.)
#   - CONFIG_NETFILTER_XT_TARGET_CONNMARK : connmark iptables target
#   - CONFIG_NETFILTER_XT_TARGET_CT     : CT iptables target for explicit conntrack control
#   - CONFIG_NF_TABLES                  : nftables framework (required by qosmgrd nftables module)
#   - CONFIG_NF_TABLES_IPV4             : nftables IPv4 support (ip table family)
#   - CONFIG_NFT_NAT                    : nftables NAT support (masquerade/SNAT rules)

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://data-netfilter.cfg"

KERNEL_CONFIG_FRAGMENTS:append = " ${UNPACKDIR}/data-netfilter.cfg"
