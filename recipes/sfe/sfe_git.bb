DESCRIPTION = "Shortcut Forward Engine Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

inherit linux-kernel-base deploy

FILESEXTRAPATHS:prepend := "${WORKSPACE}:"
SRC_URI     =  "file://shortcut-fe/shortcut-fe"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI  += "file://sfe.service"


S = "${WORKDIR}/shortcut-fe/shortcut-fe"
DEPENDS += "virtual/kernel"

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
EXT_MODULES = "${@os.path.relpath("${S}", "${KERNEL_PLATFORM_PATH}")}"

do_configure() {
  :
}
do_compile() {
        cd ${KERNEL_PLATFORM_PATH}
        ENABLE_DDK_BUILD=${ENABLE_DDK_BUILD} \
        TARGET_BOARD_PLATFORM=${TARGET_BOARD_PLATFORM} \
        KBUILD_OPTIONS+="TARGET_SUPPORT=${BASEMACHINE}" \
        BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
        EXT_MODULES=${EXT_MODULES} \
        KERNEL_KIT=${KERNEL_PREBUILT_PATH} \
        OUT_DIR=${KERNEL_OUT_PATH} \
        ROOTDIR=${WORKSPACE}/ \
        MODULE_OUT=${WORKDIR}/shortcut-fe/shortcut-fe \
        KERNEL_UAPI_HEADERS_DIR=${STAGING_KERNEL_BUILDDIR} \
        ./build/build_module.sh ${KBUILD_OPTIONS}
}


do_install() {

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${WORKDIR}/shortcut-fe/shortcut-fe/shortcut-fe.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${WORKDIR}/shortcut-fe/shortcut-fe/shortcut-fe-ipv6.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${WORKDIR}/shortcut-fe/shortcut-fe/shortcut-fe-cm.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/

# SFE SERVICE
install -d ${D}${systemd_unitdir}/system/
install -m 0644 ${WORKDIR}/sfe.service  -D ${D}${systemd_unitdir}/system/sfe.service
}

FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN} += "${systemd_unitdir}/*"
