FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://iproute2_config_to_data.patch"

pkg_postinst_${PN}(){
    chown -Rh radio:radio $D${sysconfdir}/data/iproute2
}
