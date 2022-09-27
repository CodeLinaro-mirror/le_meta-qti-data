DESCRIPTION = "Lassen drivers"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit linux-kernel-base deploy qdlkm systemd

PR = "r0"

DEPENDS = "rsync-native"
DEPENDS += "bc-native bison-native"

do_configure[depends] += "${@oe.utils.conditional('KERNEL_USE_PREBUILTS', 'True', 'virtual/kernel:do_prebuilt_shared_workdir', 'virtual/kernel:do_shared_workdir',d)}"
FILESPATH   =+ "${WORKSPACE}:"
SRC_URI     =  "file://datacsm-kernel/"
SRC_URI += "file://techpack-ecpri.service"
SRC_URI += "file://ecpri_install"
SRC_URI += "file://ecpri_uninstall"

S = "${WORKDIR}/datacsm-kernel"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"

do_configure() {
    cp -f ${WORKSPACE}/datacsm-kernel/Makefile.am ${WORKSPACE}/datacsm-kernel/Makefile
}

do_compile() {

    cd ${WORKDIR}/datacsm-kernel && \
    mkdir -p usr && \
    cd usr && \
    mkdir -p include && \
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../datacsm-kernel \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_GSI=m \
    MODULE_ECPRI_DMA=m \
    MODULE_LASSEN_MTIP=m \
    MODULE_ECPRI_CORE=m \
    MODULE_ECPRI_OXTOR=m \
    MODULE_LASSEN_MACSEC=m \
    MODULE_LASSEN_QCOM_AW_PHY=m \
    MODULE_OUT=${WORKDIR}/datacsm-kernel \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    KERNEL_UAPI_HEADERS_DIR=${STAGING_KERNEL_BUILDDIR} \
    INSTALL_MODULE_HEADERS=1 \
    ./build/build_module.sh
}

DEBUG_SYMBOLS = "${@oe.utils.conditional('DEBUG_BUILD', '1', 'true', 'false',d)}"

do_install() {
    install -d ${D}${sysconfdir}/initscripts
    install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
    install -d ${D}/usr/include/
    install -d ${D}/usr/lib/modules/


    # strip debug symbols and sign the module
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/ecpri_dmam.ko ${DEBUG_SYMBOLS}
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/gsim.ko ${DEBUG_SYMBOLS}
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/mtip/lassen_mtip.ko ${DEBUG_SYMBOLS}
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/ecpri_core.ko ${DEBUG_SYMBOLS}
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/ecpri_oxtor.ko ${DEBUG_SYMBOLS}
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/macsec/lassen_macsec.ko ${DEBUG_SYMBOLS}
    do_strip_and_sign_dlkm ${WORKDIR}/datacsm-kernel/drivers/qcom_aw_phy/lassen_qcom_aw_phy.ko ${DEBUG_SYMBOLS}

    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/ecpri_dmam.ko -D ${WORKDIR}/ecpri_dmam.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/gsim.ko -D ${WORKDIR}/gsim.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/mtip/lassen_mtip.ko -D ${WORKDIR}/lassen_mtip.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/ecpri_core.ko -D ${WORKDIR}/ecpri_core.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/ecpri_oxtor.ko -D ${WORKDIR}/ecpri_oxtor.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/macsec/lassen_macsec.ko -D ${WORKDIR}/lassen_macsec.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/qcom_aw_phy/lassen_qcom_aw_phy.ko -D ${WORKDIR}/lassen_qcom_aw_phy.ko

    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/ecpri_dmam.ko -D ${D}${libdir}/modules/ecpri_dmam.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/gsim.ko -D ${D}${libdir}/modules/gsim.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/mtip/lassen_mtip.ko -D ${D}${libdir}/modules/lassen_mtip.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/ecpri_core.ko -D ${D}${libdir}/modules/ecpri_core.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/ecpri_oxtor.ko -D ${D}${libdir}/modules/ecpri_oxtor.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/macsec/lassen_macsec.ko -D ${D}${libdir}/modules/lassen_macsec.ko
    install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/qcom_aw_phy/lassen_qcom_aw_phy.ko -D ${D}${libdir}/modules/lassen_qcom_aw_phy.ko

    cp -r ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/include/uapi/ecpri ${D}/usr/include/
    cp -r ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/include/uapi/ecpri ${D}/usr/include/
    cp -r ${WORKDIR}/datacsm-kernel/drivers/qcom_aw_phy/include/uapi/qcom_aw_phy ${D}/usr/include/

	install -m 0755 \
		${WORKDIR}/ecpri_install -D ${D}${bindir}/ecpri_install

	install -m 0755 \
		${WORKDIR}/ecpri_uninstall -D ${D}${bindir}/ecpri_uninstall

	# Install unit files to systemd system directory and they will be
	# packaged and enabled by the systemd class if 'systemd' feature
	# is enabled in the distro.
	install -m 0644 ${WORKDIR}/techpack-ecpri.service \
		-D ${D}${systemd_system_unitdir}/techpack-ecpri.service
}

do_deploy() {
    cp -rp ${WORKDIR}/ecpri_dmam.ko ${DEPLOYDIR}/
    cp -rp ${WORKDIR}/gsim.ko ${DEPLOYDIR}/
    cp -rp ${WORKDIR}/lassen_mtip.ko ${DEPLOYDIR}/
    cp -rp ${WORKDIR}/ecpri_core.ko ${DEPLOYDIR}/
    cp -rp ${WORKDIR}/ecpri_oxtor.ko ${DEPLOYDIR}/
    cp -rp ${WORKDIR}/lassen_macsec.ko ${DEPLOYDIR}/
    cp -rp ${WORKDIR}/lassen_qcom_aw_phy.ko ${DEPLOYDIR}/
}

addtask do_deploy after do_install

SYSTEMD_SERVICE_${PN}  += "techpack-ecpri.service"

FILES_${PN} += "${sysconfdir}/*"
FILES_${PN} += "${libdir}/modules/*"
FILES_${PN} += "${systemd_unitdir}/system/multi-user.target.wants/*"
FILES_${PN} += "${systemd_system_unitdir}/*"

