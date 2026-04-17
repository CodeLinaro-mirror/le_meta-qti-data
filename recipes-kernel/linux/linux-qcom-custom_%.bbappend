# Apply kernel config fragment required by qca-nss-ecm module.
# These options correspond to the KCONFIG entries in the OpenWRT
# owrt-packages/qca-nss-ecm/Makefile.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://qca-nss-ecm.cfg"

KERNEL_CONFIG_FRAGMENTS:append = " ${WORKDIR}/qca-nss-ecm.cfg"
