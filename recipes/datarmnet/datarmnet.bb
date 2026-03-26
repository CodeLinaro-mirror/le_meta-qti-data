inherit module qperf

# if is TARGET_KERNEL_ARCH is set inherit qtikernel-arch to compile for that arch.
inherit ${@bb.utils.contains('TARGET_KERNEL_ARCH', 'aarch64', 'qtikernel-arch', '', d)}

DESCRIPTION = "Datarmnet drivers"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

DEPENDS = "virtual/kernel"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://datarmnet/core/"
SRC_URI += "file://datarmnet-load.conf"

S = "${WORKDIR}/datarmnet/core"

INITSCRIPT_PARAMS = "start 35 5 . stop 15 0 1 6 ."

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

EXTRA_OECONF += "--with-kernel-src=${STAGING_KERNEL_DIR} \
                 --with-kernel=${STAGING_KERNEL_BUILDDIR} \
                 --with-sanitized-headers=${STAGING_KERNEL_BUILDDIR}/usr/include"

# Disable parallel make
PARALLEL_MAKE = ""

# Disable parallel make
PARALLEL_MAKE = "-j1"


do_compile:qcm2290-mtp() {
   cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../datarmnet/core \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/datarmnet/core-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_compile:qcm4325-mtp() {
   cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    EXT_MODULES=../../datarmnet/core \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/datarmnet/core-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}


#currently not working but for some reason this compiles but do_install_append doesnt
do_install:qcm2290-mtp() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 ${WORKDIR}/datarmnet-load.conf -D ${D}${sysconfdir}/modules-load.d/datarmnet-load.conf
    install -m 0644 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}

}

do_install:qcm4325-mtp() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 ${WORKDIR}/datarmnet-load.conf -D ${D}${sysconfdir}/modules-load.d/datarmnet-load.conf
    install -m 0644 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}

}

do_compile:kera() {
    LE_EXTRA_CFLAGS="-I${STAGING_DIR}/usr/include"
    cd ${WORKSPACE}/kernel-${PREFERRED_VERSION_linux-msm}/kernel_platform  && \
    BUILD_CONFIG=${KERNEL_BUILD_CONFIG} \
    LE_EXTRA_CFLAGS="${LE_EXTRA_CFLAGS}" \
    EXT_MODULES=../../datarmnet/core \
    ROOTDIR=${WORKSPACE}/ \
    MODULE_OUT=${WORKDIR}/datarmnet/core-out \
    OUT_DIR=${KERNEL_OUT_PATH}/ \
    ./build/build_module.sh
}

do_install:kera() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0644 ${WORKDIR}/datarmnet-load.conf -D ${D}${sysconfdir}/modules-load.d/datarmnet-load.conf
    install -m 0644 ${WORKDIR}/datarmnet/core-out/rmnet_core.ko -D ${D}${base_libdir}/modules/${KERNEL_VERSION}
}

FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/*"
FILES:${PN} += "${sysconfdir}/modules-load.d/datarmnet-load.conf"
RPROVIDES:${PN} += "kernel-module-rmnet-core-${KERNEL_VERSION}"