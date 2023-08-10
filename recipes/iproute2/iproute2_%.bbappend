FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://iproute2_config_to_data.patch"

pkg_postinst_${PN}(){
        if [ -z "$D" ] && [ -d ${sysconfdir}/data/iproute2 ] ; then
            chown -Rh 1001:1001 ${sysconfdir}/data/iproute2
        fi
}
