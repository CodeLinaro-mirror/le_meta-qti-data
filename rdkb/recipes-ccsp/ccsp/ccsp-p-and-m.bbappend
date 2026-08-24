FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
require ccsp_common.inc

DEPENDS_append = " utopia-headers utopia ccsp-common-library "
RDEPENDS_${PN}-ccsp_remove_dunfell = " bash "
RDEPENDS_${PN}_append = " bash "
DEPENDS += " glib-2.0 "

CFLAGS_append += " -Dstrlcpy=g_strlcpy -Dstrlcat=g_strlcat "
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'syslog-ng', ' -D_SYSLOG_NG ', '', d)}"
SRC_URI += " \
              file://00001-qti-ccsp-p-and-m_23Q1_Port.patch \
              file://00002-qti-ccsp-p-and-m_vendor.patch \
              file://00003-qti-ccsp-p-and-m_fix-iana-addr-null-deref.patch \
              file://00004-qti-ccsp-p-and-m_DNSProxy-bringup.patch \
              file://00005-qti-ccsp-p-and-m_Cellular_v6_bringup.patch \
              file://00006-qti-ccsp-p-and-m_Cellular_v6_Extension_Support.patch \
              file://00007-qti-ccsp-p-and-m_Fix-nonnull-RfcJsonInit-null-filename.patch \
              file://00008-qti-ccsp-p-and-m_USB-DM.patch \
              file://00009-qti-ccsp-p-and-m_IPPT-changes.patch \
              file://00010-qti-ccsp-p-and-m_Fix-ipv6-dm-population.patch \
              file://00011-qti-ccsp-p-and-m_changes_for_factory_reset.patch \
              file://00012-qti-ccsp-p-and-m_lan-vlan-changes.patch \
              file://00013-qti-ccsp-p-and-m_lan-restart-after-dhcp-pool-change.patch \
              file://00014-qti-ccsp-p-and-m_sip-dm-implementation.patch \
              file://00015-qti-ccsp-p-and-m_vpn-passthrough-read-syscfg-state.patch \
              file://00016-qti-CcspPandM_address_reconf.patch \
              file://00017-qti-ccsp-p-and-m_v6_mtu_fix.patch \
              file://00018-qti-ccsp-p-and-m_dhcpv6-stateless.patch \
              file://00019-qti-ccsp-p-and-m-RTSP-DM-support.patch \
              file://00020-qti-ccsp-p-and-m_PacketStats-Enable-Disable.patch \
              file://00021-qti-ccsp-p-and-m-usb-ecm-interface-count-fix.patch \
"
SECURITY_CFLAGS_remove += " -Werror=format-security -Werror=format "
SECURITY_CFLAGS_append += " -Wno-error=format-security -Wno-error=format "

LDFLAGS_append = " -L${STAGING_LIBDIR} -lglib-2.0 "

CFLAGS_append += " -Wno-stringop-truncation -Wno-stringop-overflow -Wno-error=format-truncation= -Wno-error=unused-parameter -Wno-error=return-type -Wno-error=sign-compare -Wno-error=implicit-function-declaration -Wno-error=int-conversion -Wno-error=parentheses -Wno-error=unused-variable -Wno-error=int-to-pointer-cast -Wno-error=pointer-to-int-cast -Wno-error=format -Wno-error=unused-parameter -Wno-error=return-type -Wno-error=nonnull "
CFLAGS_append += " \
    -I=${includedir}/utctx \
    -I=${includedir}/utapi \
    -I=${includedir}/ccsp \
"

do_install_append() {
    # Config files and scripts
    install -m 644 ${S}/config-arm/CcspDmLib.cfg ${D}/usr/ccsp/pam/CcspDmLib.cfg
    install -m 644 ${S}/config-arm/CcspPam.cfg -t ${D}/usr/ccsp/pam
    install -m 644 ${S}/config-arm/TR181-USGv2.XML -t ${D}/usr/ccsp/pam
    install -m 777 ${D}/usr/bin/CcspPandMSsp -t ${D}/usr/ccsp/pam/
}
FILES_${PN} += " \
    ${prefix}/ccsp/pam/CcspPandMSsp \
"
FILES_${PN}_append = " \
    /usr/ccsp/pam/CcspDmLib.cfg \
    /usr/ccsp/pam/CcspPam.cfg \
    /usr/ccsp/pam/TR181-USGv2.XML \
    /usr/ccsp/pam/launch_tr69.sh \
"
