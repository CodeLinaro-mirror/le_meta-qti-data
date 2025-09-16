FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://iproute2_config_to_data.patch"

pkg_postinst_ontarget:${PN}(){
    chown -Rh 1001:1001 $D${sysconfdir}/data/iproute2
}