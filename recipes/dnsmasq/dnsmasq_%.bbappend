DEPENDS = "libnetfilter-conntrack"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://dnsmasq.conf \
           file://dnsmasq_script.sh \
           file://dnsmasq.service \
           file://dnsmasq_service@.service \
           file://qcmap_dhcp_release.service \
           file://qcmap_stop_dnsmasq.sh \
           file://0001-dhcp_release_fix_for_ippt.patch \
           file://0001-Enable-conntrack-for-dnsmasq.patch \
           file://0001-Include-libnetfilter_conntrack-lib-for-dnsmasq.patch"

EXTRA_OEMAKE = "CC='${CC}' \
                CFLAGS='${TARGET_CFLAGS}' \
                LDFLAGS='${TARGET_LDFLAGS}'"

do_install:append () {
        install -m 644 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}
        chown -R root:root ${D}${sysconfdir}/dnsmasq.conf
        # symlink dnsmasq.conf under /etc -> not required after shifting conf file to /etc
        # ln -sf ../${sysconfdir}/data/dnsmasq.conf ${D}${sysconfdir}/dnsmasq.conf

        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
         install -d ${D}/etc/initscripts
         install -m 555 ${WORKDIR}/init ${D}${sysconfdir}/initscripts/dnsmasq
         install -d ${D}/etc/systemd/system/
         install -m 0644 ${WORKDIR}/dnsmasq.service -D ${D}/etc/systemd/system/dnsmasq.service
         install -d ${D}/etc/systemd/system/multi-user.target.wants/
         install -d ${D}${systemd_unitdir}/system/
         install -m 0644 ${WORKDIR}/dnsmasq_service@.service -D ${D}${systemd_unitdir}/system/dnsmasq_service@.service
         install -m 0555 ${WORKDIR}/qcmap_stop_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
         install -m 0644 ${WORKDIR}/qcmap_dhcp_release.service -D ${D}${systemd_unitdir}/system/qcmap_dhcp_release.service
        else
         install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/dnsmasq
        fi
        install -d ${D}${base_bindir}
        install -m 0755 ${WORKDIR}/dnsmasq_script.sh ${D}${base_bindir}
        chown -h root:root ${D}${base_bindir}/dnsmasq_script.sh
        rm -f ${D}${sysconfdir}/systemd/system/dnsmasq.service
        rm -f ${D}${systemd_unitdir}/system/dnsmasq.service
        rm -f ${D}${sysconfdir}/systemd/resolved.conf.d/*
        rm -d ${D}${sysconfdir}/systemd/resolved.conf.d
        # Add static dnsmasq parameters in /etc/dnsmasq.conf
        echo "except-interface=lo" >> ${D}${sysconfdir}/dnsmasq.conf
        echo "bind-interfaces" >> ${D}${sysconfdir}/dnsmasq.conf
        echo "dhcp-hostsfile=/etc/data/dhcp_hosts" >> ${D}${sysconfdir}/dnsmasq.conf
        echo "dhcp-script=/bin/dnsmasq_script.sh" >> ${D}${sysconfdir}/dnsmasq.conf
}

CONFFILES:${PN} = "${sysconfdir}/dnsmasq.conf"
FILES:${PN} += "${sysconfdir}/dnsmasq.conf"
FILES:${PN} += "${systemd_unitdir}/system/*"
SYSTEMD_SERVICE:${PN} = ""
