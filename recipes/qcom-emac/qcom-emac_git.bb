DESCRIPTION = "QUALCOMM EMAC Driver"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit linux-kernel-base deploy

FILESEXTRAPATHS:prepend := "${WORKSPACE}:"
DEPENDS += "virtual/kernel"

do_compile[depends] += "virtual/kernel:do_shared_workdir"
do_compile[cleandirs] += "${WORKDIR}/out/${KERNEL_DEFCONFIG}"

do_configure() {
  :
}

do_install() {
    install -d ${D}${base_libdir}/modules/emac
    install -m 0755 ${KERNEL_OUT_PATH}/dist/at803x.ko -D ${D}${base_libdir}/modules/emac/
    install -m 0755 ${KERNEL_OUT_PATH}/dist/qca8337.ko -D ${D}${base_libdir}/modules/emac/
    install -m 0755 ${KERNEL_OUT_PATH}/dist/qcom-emac.ko -D ${D}${base_libdir}/modules/emac/
}

FILES:${PN} += "${base_libdir}/modules/emac/*"
