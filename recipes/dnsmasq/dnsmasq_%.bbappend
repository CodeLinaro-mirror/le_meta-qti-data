DEPENDS = "libnetfilter-conntrack gettext-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://dnsmasq.conf \
           file://dnsmasq_script.sh \
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
        install -d ${D}${sysconfdir}/data/
        install -m 644 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}/data
        chown -R root:root ${D}${sysconfdir}/data/dnsmasq.conf
        # symlink dnsmasq.conf under /etc
        ln -sf ../${sysconfdir}/data/dnsmasq.conf ${D}${sysconfdir}/dnsmasq.conf

        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
         install -d ${D}/etc/initscripts
         install -m 555 ${WORKDIR}/init ${D}${sysconfdir}/initscripts/dnsmasq
         install -d ${D}/etc/systemd/system/
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
        rm -f ${D}${sysconfdir}/systemd/resolved.conf.d/*
        rm -d ${D}${sysconfdir}/systemd/resolved.conf.d
        # Add static dnsmasq parameters in /etc/data/dnsmasq.conf
        echo "except-interface=lo" >> ${D}${sysconfdir}/data/dnsmasq.conf
        echo "bind-interfaces" >> ${D}${sysconfdir}/data/dnsmasq.conf
        echo "dhcp-hostsfile=/etc/data/dhcp_hosts" >> ${D}${sysconfdir}/data/dnsmasq.conf
        echo "dhcp-script=/bin/dnsmasq_script.sh" >> ${D}${sysconfdir}/data/dnsmasq.conf
}

CONFFILES:${PN} = "${sysconfdir}/data/dnsmasq.conf"
FILES:${PN} += "${sysconfdir}/data/dnsmasq.conf"
FILES:${PN} += "${systemd_unitdir}/system/*"
SYSTEMD_SERVICE:${PN} = ""

# Fix host contamination in syslog-ng metadata directory ownership.
# This ensures BitBake wraps the task with fakeroot-native, so all directory and file creation inside ${D} is recorded with root:root
# ownership, eliminating the host contamination QA error.

do_write_metadata_syslog_ng[fakeroot] = "1"
