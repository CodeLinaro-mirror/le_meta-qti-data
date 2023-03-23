# Constant bitbake recipe information for the meta-qcom layer
# common values, statements and functions.
#
inherit autotools-brokensep gitsha pkgconfig
HOMEPAGE         = "https://git.codelinaro.org/"
FILESPATH        =+ "${WORKSPACE}:"
SRC_URI          = "file://${@d.getVar('SRC_DIR', True).replace('${WORKSPACE}/', '')}"
REPO_SRC_URI     = "file://${@d.getVar('SRC_DIR', True).replace('${WORKSPACE}/', '')}"
PV               = "git-${GITSHA}"
#LIC_FILES_CHKSUM = "file://${COREBASE}/meta-qcom/files/qcom-licenses/\
#${LICENSE};md5=92b1d0ceea78229551577d4284669bb8"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
LICENSE          = "BSD-3-Clause-Clear"

# Must set SRC_DIR to use qcommon
#add prebuilt task if the recipe wants it enabled.
def src_dir_after_parse(d):
    if d.getVar('SRC_DIR') == None:
        raise bb.build.FuncFailed("%s inherits qcommon but doesn't set SRC_DIR" % d.getVar('FILE'))
    if d.getVar('PREBUILT') == "1":
        bb.build.addtask('do_prebuilt', 'do_build', 'do_populate_sysroot', d)

python __anonymous() {
    src_dir_after_parse(d)
}

python do_fetch () {
    if (os.path.isdir("${SRC_DIR}")):
        bb.build.exec_func('base_do_fetch',d)
}

python do_unpack () {
    if (os.path.isdir("${SRC_DIR}")):
        bb.build.exec_func('base_do_unpack',d)
    else:
        bb.build.exec_func('qcommon_no_src_dir_unpack',d)
}

# SRC_DIR doesn't exist.
# We need to remove SRC_DIR from SRC_URI then fetch the rest.
python qcommon_no_src_dir_unpack () {
    src_uri = d.getVar('SRC_URI', True)
    if len(src_uri) == 0:
        return
    src_uri.replace("${REPO_SRC_URI}", "")
    d.setVar("SRC_URI", src_uri)
    bb.build.exec_func('base_do_unpack',d)
}

# Run install or copy from prebuilt.
qcommon_do_install() {
if [ -d "${SRC_DIR}" ];then
    autotools_do_install
else
    if [ "x${PREBUILT}" == "x1" ];then
        tar -xjvf ${WORKSPACE}/prebuilt/${PN}/${PN}-binaries.tar -C ${D}
    fi
fi
}

# Copy to prebuilt/pn if SRC_DIR is present and PREBUILT is set.
do_prebuilt[dirs] = "${WORKSPACE}/prebuilt/${PN}"
qcommon_do_prebuilt() {
if [ -d "${SRC_DIR}" ];then
    if [ "x${PREBUILT}" == "x1" ];then
        cp -fpPRa ${D}/* ${WORKSPACE}/prebuilt/${PN}
        cd ${WORKSPACE}/prebuilt/${PN}
        if [ -f ${PN}-binaries.tar ];then
            rm -fr ${PN}-binaries.tar
        fi
        tar -cjvf ${PN}-binaries.tar *
    fi
fi
}
EXPORT_FUNCTIONS do_prebuilt do_install
