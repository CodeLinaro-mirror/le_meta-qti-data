FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://bbhm_def_cfg_sdx.xml \
            file://00001-fix-psm-null-deref.patch \
"
require ccsp_common.inc
do_install_append() {
    # Config files and scripts
    install -d ${D}/usr/ccsp/config
    install -m 777 ${UNPACKDIR}/bbhm_def_cfg_sdx.xml ${D}/usr/ccsp/config/bbhm_def_cfg.xml
    install -m 755 ${S}/scripts/bbhm_patch.sh ${D}/usr/ccsp/psm/bbhm_patch.sh
}
