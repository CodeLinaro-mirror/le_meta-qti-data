FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append += "file://00001-Dibbler-interface-updation-for-duid-generation-on-cellular-m.patch;apply=no"
SRC_URI += " file://00002-dibbler-interface.patch;patchdir=${WORKDIR} \
           "
do_dibbler_patches() {
    cd -P ${WORKDIR}/git
    if [ ! -e patch_applied ]; then
       patch -f -p1 < ${WORKDIR}/00001-Dibbler-interface-updation-for-duid-generation-on-cellular-m.patch
       touch -c patch_applied
    fi
}

addtask do_dibbler_patches after do_patch before do_compile
RM_WORK_EXCLUDE += "${PN}"
