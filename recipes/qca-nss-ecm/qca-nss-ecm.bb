DESCRIPTION = "QCA NSS Enhanced Connection Manager Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

inherit pkgconfig autotools deploy module linux-kernel-base systemd

KERNEL_VERSION = "${@get_kernelversion_file("${STAGING_KERNEL_BUILDDIR}")}"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

DEPENDS = "virtual/kernel linux-kernel-qcom-headers dataipa"

RDEPENDS:${PN} += "kernel-module-ipam-${KERNEL_VERSION}"

PR = "r0"

SRC_URI = "file://qca-nss-ecm/"
SRC_URI += "file://qca-nss-ecm.service"
SRC_URI += "file://qca-nss-ecm.sh"
SRC_URI += "file://qca-nss-ecm-config"
SRC_URI += "file://qca-nss-ecm.sysctl"
SRC_URI += "file://ecm_dump.sh"

S = "${WORKDIR}/qca-nss-ecm"

FILESPATH =+ "${WORKSPACE}:"

EXTRA_OEMAKE += "TARGET_SUPPORT=${BASEMACHINE}"

# ECM feature flags passed as make variables to the kernel build system.
# These control which source files are compiled (ecm-$(FLAG) += ...) and
# which -D defines are injected via ccflags-$(FLAG) in the ECM Makefile.
EXTRA_OEMAKE += " \
    ECM_DB_PER_CLIENT_ROUTED_STATS_ENABLE=y \
    ECM_SDX_STATS_ENABLE=y \
    ECM_CLASSIFIER_PCC_ENABLE=y \
    ECM_INTERFACE_RAWIP_ENABLE=y \
    ECM_TRACKER_DPI_SUPPORT_ENABLE=y \
    ECM_CLASSIFIER_DSCP_ENABLE=y \
    ECM_INTERFACE_VLAN_ENABLE=y \
    ECM_BRIDGE_VLAN_FILTERING_ENABLE=y \
    ECM_BAND_STEERING_ENABLE=y \
    ECM_DB_CTA_TRACK_ENABLE=y \
    ECM_DB_ADVANCED_STATS_ENABLE=y \
    ECM_FRONT_END_CONN_LIMIT_ENABLE=y \
    ECM_STATE_OUTPUT_ENABLE=y \
    ECM_DB_XREF_ENABLE=y \
    ECM_IPV6_ENABLE=y \
    ECM_FRONT_END_SFE_ENABLE=n \
    ECM_FRONT_END_IPA_ENABLE=y \
    ECM_SDX_PCC_ENABLED=y \
    ECM_INTERFACE_PPP_ENABLE=n \
    ECM_INTERFACE_PPPOE_ENABLE=n \
    EXAMPLES_BUILD_SDX=y \
"

# Extra compiler flags passed to the kernel build system via EXTRA_CFLAGS.
# These are appended to ccflags-y inside the kernel Makefile infrastructure.

# Use explicit recipe-sysroot path for dataipa recipes.
EXTRA_CFLAGS += "-I${RECIPE_SYSROOT}${includedir}/dataipa"

# Point modpost to the IPA module's exported symbols so that inter-module
# references (ipa_ipv4_tx, ipa_ipv6_tx, etc.) are resolved at build time.
IPA_SYMVERS = "${RECIPE_SYSROOT}${includedir}/dataipa/Module.symvers"

# "-DCONFIG_FUNCTION_ALIGNMENT=16 \
#                 -DCONFIG_CLANG_VERSION=180000 \
#                 -DCONFIG_KERNEL_ATOMIC64=y"

do_configure:append() {
     # Propagate EXTRA_CFLAGS (include paths, defines) into the kernel build
     # system via ccflags-y, mirroring the standard pattern:
     #   ccflags-y += $(EXTRA_CFLAGS)
     # This replaces the hardcoded echo approach and lets the recipe control
     # all include paths purely through EXTRA_CFLAGS.
     grep -q 'ccflags-y.*EXTRA_CFLAGS' ${S}/Makefile || \
        echo 'subdir-ccflags-y += $(EXTRA_CFLAGS)' >> ${S}/Makefile
}

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
               KBUILD_EXTRA_SYMBOLS="${KBUILD_EXTRA_SYMBOLS} ${IPA_SYMVERS}" \
               EXTRA_CFLAGS="${EXTRA_CFLAGS}" \
               ${EXTRA_OEMAKE} \
               ${MAKE_TARGETS}
}

do_install() {
    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/extra
    install -d ${DEPLOY_DIR_IMAGE}/kernel_modules/qca-nss-ecm

    # Copy the modules that contain debug symbols to the deploy directory
    cp ${S}/ecm.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/qca-nss-ecm
    cp ${S}/examples/ecm_sdx_pcc.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/qca-nss-ecm
    cp ${S}/examples/ecm_ae_select.ko ${DEPLOY_DIR_IMAGE}/kernel_modules/qca-nss-ecm

    # Strip debug symbols
    ${STRIP} --strip-debug ${S}/ecm.ko
    ${STRIP} --strip-debug ${S}/examples/ecm_sdx_pcc.ko
    ${STRIP} --strip-debug ${S}/examples/ecm_ae_select.ko

    # Sign and install the modules
    LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${S}/ecm.ko

    LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${S}/examples/ecm_sdx_pcc.ko

    LD_LIBRARY_PATH=${WORKSPACE}/kernel/kernel_platform/prebuilts/kernel-build-tools/linux-x86/lib64/ \
    ${STAGING_KERNEL_BUILDDIR}/scripts/sign-file sha1 ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.pem \
    ${STAGING_KERNEL_BUILDDIR}/certs/signing_key.x509 ${S}/examples/ecm_ae_select.ko

    install -m 0755 ${S}/ecm.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ecm.ko
    install -m 0755 ${S}/examples/ecm_sdx_pcc.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ecm_sdx_pcc.ko
    install -m 0755 ${S}/examples/ecm_ae_select.ko -D ${D}/usr/lib/modules/${KERNEL_VERSION}/extra/ecm_ae_select.ko

    # Install sysctl config
    install -d ${D}${sysconfdir}/sysctl.d/
    install -m 0644 ${WORKDIR}/qca-nss-ecm.sysctl ${D}${sysconfdir}/sysctl.d/qca-nss-ecm.conf

    # Install ECM config file (replaces OpenWRT UCI config)
    install -d ${D}${sysconfdir}/qca-nss-ecm/
    install -m 0644 ${WORKDIR}/qca-nss-ecm-config ${D}${sysconfdir}/qca-nss-ecm/config

    # Install helper script (converted from OpenWRT init script)
    install -d ${D}${sbindir}/
    install -m 0755 ${WORKDIR}/qca-nss-ecm.sh ${D}${sbindir}/qca-nss-ecm.sh

    # Install utility script
    install -d ${D}${bindir}/
    install -m 0755 ${WORKDIR}/ecm_dump.sh ${D}${bindir}/ecm_dump.sh

    # Install systemd service
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/qca-nss-ecm.service ${D}${systemd_unitdir}/system/qca-nss-ecm.service
    install -d ${D}${systemd_unitdir}/system/local-fs.target.wants/
    ln -sf ${systemd_unitdir}/system/qca-nss-ecm.service \
           ${D}${systemd_unitdir}/system/local-fs.target.wants/qca-nss-ecm.service
}

FILES:${PN} += "/usr/lib/modules/${KERNEL_VERSION}/extra/*"
FILES:${PN} += "${sysconfdir}/sysctl.d/qca-nss-ecm.conf"
FILES:${PN} += "${sysconfdir}/qca-nss-ecm/config"
FILES:${PN} += "${sbindir}/qca-nss-ecm.sh"
FILES:${PN} += "${bindir}/ecm_dump.sh"
FILES:${PN} += "${systemd_unitdir}/system/qca-nss-ecm.service"
FILES:${PN} += "${systemd_unitdir}/system/local-fs.target.wants/qca-nss-ecm.service"

SYSTEMD_SERVICE:${PN} = "qca-nss-ecm.service"

RPROVIDES:${PN} += "kernel-module-ecm-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ecm-sdx-pcc-${KERNEL_VERSION}"
RPROVIDES:${PN} += "kernel-module-ecm-ae-select-${KERNEL_VERSION}"
