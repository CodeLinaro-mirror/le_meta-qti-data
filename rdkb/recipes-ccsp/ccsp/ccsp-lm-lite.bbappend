FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
require ccsp_common.inc

DEPENDS_append = " utopia-headers glib-2.0 "
CFLAGS_append += " -Wno-error=stringop-truncation -Wno-error=stringop-overflow -Wno-error=implicit-function-declaration "
CFLAGS_append += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "
CFLAGS_append += " ${@bb.utils.contains('DISTRO_FEATURES', 'guest_wifi', ' -DGUEST_WIFI ', '', d)}"
LDFLAGS_append += " -L${STAGING_LIBDIR} -lglib-2.0 "
SRC_URI += " \
              file://00001-IPPT-changes-for-lm-lite.patch \
              file://00002-lm-lite-crash-fix.patch \
              file://00003-lm-lite-changes-for-LAN-VLAN.patch \
              file://00004-lm-lite-host-entry-duplication-fix.patch \
              file://00005-lm-lite-changes-for-showing-ip-nei-address.patch \
              file://00006-lm-lite-Fix-for-layer1Interface-DM-showing-wrong-value.patch \
              file://00007-lm-lite-fix-for-core-dump-due-to-LAN-client-IPv6.patch \
              file://00008-lm-lite-Modification-to-Layer1Interface-DM-for-InterfaceType.patch \
              file://00009-lm-lite-dnsmasq-leases-fix.patch \
              file://00010-lm-lite-fix-for-crash-caused-by-buffer-size-mismatch.patch \
              file://00011-lm-lite-packet-stats-changes.patch \
              file://00012-lm-lite-ipa-connect-fix-for-vlan-client.patch \
              file://00013-lm-lite-Hosts-dm-update-globalip.patch \
"
