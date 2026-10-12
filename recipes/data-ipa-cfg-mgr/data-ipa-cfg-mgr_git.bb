inherit autotools-brokensep pkgconfig update-rc.d useradd

DESCRIPTION = "Qualcomm IPA"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

PR = "r4"

DEPENDS  = "glib-2.0 libxml2 libnetfilter-conntrack virtual/kernel data-ipanat libnl"
DEPENDS += "${@bb.utils.contains('MACHINE_FEATURES', 'commonlog', 'commonlog-lib', '', d)}"

BASEPRODUCT = "${@d.getVar('PRODUCT', False)}"

PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'dlt-logging', 'dlt', '', d)}"
PACKAGECONFIG[dlt] = "--with-dltlogging,,dlt-daemon"

# CLOG unified logging (FR205597). Takes precedence over DLT at compile time
# (the source ladder selects USE_CLOG over USE_DLT when both are defined).
PACKAGECONFIG += "${@bb.utils.contains('MACHINE_FEATURES', 'commonlog', 'clog', '', d)}"
PACKAGECONFIG[clog] = "--with-clog,,commonlog-lib"

EXTRA_OECONF = "--enable-target=${BASEMACHINE} \
		--with-sanitized-headers=${KERNEL_OUT_PATH}/msm-kernel/usr/include/  \
                --with-ipanat-headers=${WORKSPACE}/dataipa/ipanat/inc \
                --with-glib"


FILESEXTRAPATHS:prepend := "${WORKSPACE}/:"
SRC_URI = "file://data-ipa-cfg-mgr"
SRC_URI  += "file://ipacm.service"
SRC_URI  += "file://ipacm.conf"

S = "${WORKDIR}/data-ipa-cfg-mgr"

INITSCRIPT_NAME   = "start_ipacm_le"
INITSCRIPT_PARAMS = "start 32 S . stop 62 0 1 6 ."
FILES:${PN} += "${sysconfdir}/data/ipa/IPACM_cfg.xml"

do_install:append() {
	install -d 0664 -o 1001 -g 1001 ${D}${userfsdatadir}/misc/ipa
	if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then

	  #IPACM Service
	  rm -rf ${D}${sysconfdir}/init.d/start_ipacm_le
	  install -m 0644 ${WORKDIR}/ipacm.service -D ${D}${systemd_unitdir}/system/ipacm.service
	  install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
	  # enable the service for local-fs.target
	  ln -sf ${systemd_unitdir}/system/ipacm.service \
	  ${D}${systemd_unitdir}/system/local-fs.target.wants/ipacm.service

          chown -R 1001:1001 ${D}${sysconfdir}/data/ipa/

          #temp-file for ipacm
          install -d ${D}${sysconfdir}/tmpfiles.d
          install -m 0644 ${WORKDIR}/ipacm.conf -D ${D}${sysconfdir}/tmpfiles.d/ipacm.conf

          #IPACM_cfg file stored as factory settings
		  install -m 0644 -o 1001 -g 1001 ${WORKDIR}/data-ipa-cfg-mgr/ipacm/src/IPACM_cfg.xml -D ${D}${sysconfdir}/data/ipa/IPACM_cfg.xml
		  install -m 0644 -o 1001 -g 1001 ${WORKDIR}/data-ipa-cfg-mgr/ipacm/src/IPACM_cfg.xml -D ${D}${sysconfdir}/data/ipa/factory_IPACM_cfg.xml

	  # CLOG logs via DLT, so both backends need the dlt group and ordering
	  if ${@bb.utils.contains_any('PACKAGECONFIG', 'dlt clog', 'true', 'false', d)}; then
	      grep -q '^After=dlt.service$' ${D}${systemd_unitdir}/system/ipacm.service || \
	          sed -i '/^\[Unit\]/a After=dlt.service' ${D}${systemd_unitdir}/system/ipacm.service

	      grep -q '^SupplementaryGroups=.*\bdlt\b' ${D}${systemd_unitdir}/system/ipacm.service || \
	          sed -i 's/^SupplementaryGroups=.*/& dlt/' ${D}${systemd_unitdir}/system/ipacm.service
	  fi
	fi
}
FILES:${PN} += "${userfsdatadir}/misc/ipa"
FILES:${PN} += "${systemd_unitdir}/system"
FILES:${PN} += "${sysconfdir}/tmpfiles.d/ipacm.conf"
FILES:${PN} += "${systemd_unitdir}/system/local-fs.target.wants/"
