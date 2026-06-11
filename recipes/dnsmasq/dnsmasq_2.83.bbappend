FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}-${PV}:"

# Fix host contamination seen under package/etc/syslog-ng
do_write_metadata_syslog_ng[fakeroot] = "1"

do_install:append() {
    # Defensive ownership cleanup in case syslog-ng metadata gets copied into ${D}
    if [ -e ${D}${sysconfdir}/syslog-ng ]; then
        chown -R root:root ${D}${sysconfdir}/syslog-ng || true
    fi
}
