inherit useradd
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://dnsmasq.conf \
           file://dnsmasq_script.sh \
           file://dnsmasq_service@.service \
           file://qcmap_start_dnsmasq.sh \
           file://qcmap_stop_dnsmasq.sh"

EXTRA_OEMAKE = "CC='${CC}' \
                CFLAGS='${TARGET_CFLAGS}' \
                LDFLAGS='${TARGET_LDFLAGS}'"

do_install_append () {
        install -d ${D}${sysconfdir}/data/
        install -m 664 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}/data
        chown -R root:1001 ${D}${sysconfdir}/data/dnsmasq.conf
        # symlink dnsmasq.conf under /etc
        ln -sf ../${sysconfdir}/data/dnsmasq.conf ${D}${sysconfdir}/dnsmasq.conf

        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
         install -d ${D}/etc/initscripts
         install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/initscripts/dnsmasq
         install -d ${D}/etc/systemd/system/
         install -d ${D}/etc/systemd/system/multi-user.target.wants/
         install -d ${D}${systemd_unitdir}/system/
         install -m 0644 ${WORKDIR}/dnsmasq_service@.service -D ${D}${systemd_unitdir}/system/dnsmasq_service@.service
         install -m 0755 ${WORKDIR}/qcmap_start_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_start_dnsmasq.sh
         install -m 0755 ${WORKDIR}/qcmap_stop_dnsmasq.sh ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
         chown radio:radio ${D}${sysconfdir}/initscripts/qcmap_stop_dnsmasq.sh
        else
         install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/dnsmasq
        fi
        install -d ${D}${base_bindir}
        install -m 0755 ${WORKDIR}/dnsmasq_script.sh ${D}${base_bindir}
        chown -h 65534:65534 ${D}${base_bindir}/dnsmasq_script.sh

        rm -f ${D}${sysconfdir}/systemd/resolved.conf.d/*
        rm -d ${D}${sysconfdir}/systemd/resolved.conf.d
}

CONFFILES_${PN} = "${sysconfdir}/data/dnsmasq.conf"
FILES_${PN} += "${sysconfdir}/data/dnsmasq.conf"
FILES_${PN} += "${systemd_unitdir}/system/*"

SYSTEMD_SERVICE_${PN} = ""
