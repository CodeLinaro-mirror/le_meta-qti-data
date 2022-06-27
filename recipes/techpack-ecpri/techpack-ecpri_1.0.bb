DESCRIPTION = "Lassen drivers"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

inherit linux-kernel-base deploy

PR = "r0"

DEPENDS = "rsync-native"
DEPENDS += "bc-native bison-native"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

FILESPATH   =+ "${WORKSPACE}:"
SRC_URI     =  "file://datacsm-kernel/"

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

    BUILD_CONFIG=msm-kernel/build.config.msm.cinder \
    OUT_DIR=${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/out/msm-*-*-${KERNEL_VARIANT}defconfig/ \
    KERNEL_UAPI_HEADERS_DIR=${STAGING_KERNEL_BUILDDIR} \
    INSTALL_MODULE_HEADERS=1 \
    ./build/build_module.sh

    BUILD_CONFIG=msm-kernel/build.config.msm.cinder \
    EXT_MODULES=../../datacsm-kernel \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_GSI=m \
	MODULE_ECPRI_DMA=m \
	MODULE_LASSEN_MTIP=m \
	MODULE_ECPRI_CORE=m \
	MODULE_ECPRI_OXTOR=m \
	MODULE_LASSEN_MACSEC=m \
    MODULE_OUT=${WORKDIR}/datacsm-kernel \
    OUT_DIR=${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/out/msm-*-*-${KERNEL_VARIANT}defconfig/ \
    KERNEL_UAPI_HEADERS_DIR=${STAGING_KERNEL_BUILDDIR} \
    INSTALL_MODULE_HEADERS=1 \
    ./build/build_module.sh
}

do_install() {
	install -d ${D}${sysconfdir}/initscripts
	install -d ${D}${systemd_unitdir}/system/multi-user.target.wants/
	install -d ${D}/usr/include/
	install -d ${D}/usr/lib/modules/
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/ecpri_dmam.ko -D ${WORKDIR}/ecpri_dmam.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/gsim.ko -D ${WORKDIR}/gsim.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/mtip/lassen_mtip.ko -D ${WORKDIR}/lassen_mtip.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/ecpri_core.ko -D ${WORKDIR}/ecpri_core.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/ecpri_oxtor.ko -D ${WORKDIR}/ecpri_oxtor.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/macsec/lassen_macsec.ko -D ${WORKDIR}/lassen_macsec.ko

	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/ecpri_dmam.ko -D ${D}${libdir}/modules/ecpri_dmam.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/dma/gsim.ko -D ${D}${libdir}/modules/gsim.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/mtip/lassen_mtip.ko -D ${D}${libdir}/modules/lassen_mtip.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/ecpri_core.ko -D ${D}${libdir}/modules/ecpri_core.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/ecpri_oxtor.ko -D ${D}${libdir}/modules/ecpri_oxtor.ko
	install -m 0755 ${WORKDIR}/datacsm-kernel/drivers/macsec/lassen_macsec.ko -D ${D}${libdir}/modules/lassen_macsec.ko
	cp -r ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_core/include/uapi/ecpri ${D}/usr/include/
        cp -r ${WORKDIR}/datacsm-kernel/drivers/ecpri/ecpri_oxtor/include/uapi/ecpri ${D}/usr/include/

}

do_deploy() {
	cp -rp ${WORKDIR}/ecpri_dmam.ko ${DEPLOYDIR}/
	cp -rp ${WORKDIR}/gsim.ko ${DEPLOYDIR}/
	cp -rp ${WORKDIR}/lassen_mtip.ko ${DEPLOYDIR}/
	cp -rp ${WORKDIR}/ecpri_core.ko ${DEPLOYDIR}/
	cp -rp ${WORKDIR}/ecpri_oxtor.ko ${DEPLOYDIR}/
	cp -rp ${WORKDIR}/lassen_macsec.ko ${DEPLOYDIR}/
}

addtask do_deploy after do_install

FILES_${PN} += "${sysconfdir}/*"
FILES_${PN} += "${libdir}/modules/*"
