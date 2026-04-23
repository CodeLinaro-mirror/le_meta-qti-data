DESCRIPTION = "QCA NSS Shortcut Forwarding Engine Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

inherit pkgconfig autotools deploy module linux-kernel-base systemd

do_configure[depends] += "virtual/kernel:do_shared_workdir"

DEPENDS = "virtual/kernel linux-kernel-qcom-headers"

PR = "r0"

FILESPATH += "${WORKSPACE}:"

FILESEXTRAPATHS:prepend := "${TOPDIR}/../src:"

SRC_URI = "file://qca-nss-sfe/"
SRC_URI += "file://sfe_dump"

S = "${WORKDIR}/qca-nss-sfe"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"
EXTRA_OEMAKE += "CONFIG_QCA_NSS_SFE=m"
EXTRA_OEMAKE += "SFE_PROCESS_LOCAL_OUT=y"

EXTRA_OEMAKE += "${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'SFE_SUPPORT_IPV6=y', '', d)}"
EXTRA_OEMAKE += "${@bb.utils.contains('DISTRO_FEATURES', 'bridge-vlan-filtering', 'SFE_BRIDGE_VLAN_FILTERING_ENABLE=y', '', d)}"

KERNEL_MODULE_AUTOLOAD:${PN} = "qca-nss-sfe"

do_compile() {
    cd ${S}

    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS

    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules \
        KERNEL_PATH=${STAGING_KERNEL_DIR} \
        KERNEL_VERSION=${KERNEL_VERSION} \
        CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
        AR="${KERNEL_AR}" OBJCOPY="${KERNEL_OBJCOPY}" \
        STRIP="${KERNEL_STRIP}" \
        O=${STAGING_KERNEL_BUILDDIR} \
        KBUILD_EXTRA_SYMBOLS="${KBUILD_EXTRA_SYMBOLS}" \
        EXTRA_CFLAGS="${EXTRA_CFLAGS}" \
        ${EXTRA_OEMAKE}
}

do_install() {
    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/qca-nss-sfe.ko \
        ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/qca-nss-sfe.ko
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/sfe_dump ${D}${bindir}/sfe_dump
    # Install public API headers so dependent recipes (e.g. qca-nss-ecm)
    # can find them via ${STAGING_INCDIR}/qca-nss-sfe/ (mirrors OpenWrt
    # Build/InstallDev: cp exports/sfe_api.h $(STAGING_DIR)/usr/include/qca-nss-sfe)
    install -d ${D}${includedir}/qca-nss-sfe
    install -m 0644 ${S}/exports/*.h ${D}${includedir}/qca-nss-sfe/

    # Install Module.symvers so dependent modules (e.g. qca-nss-ecm) can
    # resolve SFE-exported symbols via KBUILD_EXTRA_SYMBOLS at modpost time.
    install -m 0644 ${S}/Module.symvers ${D}${includedir}/qca-nss-sfe/Module.symvers
}

FILES:${PN} += "\
    /usr/lib/modules/${KERNEL_VERSION}/extra/qca-nss-sfe.ko \
    ${bindir}/sfe_dump \
"
FILES:${PN} += "${includedir}/*"