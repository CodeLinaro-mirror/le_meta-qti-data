# Common kernel config fragment for qca-nss-ecm.
# Included by both linux-qcom-custom_%.bbappend and linux-msm-src_%.bbappend
# so the same ECM kconfig options are applied regardless of which kernel
# recipe is used in a given branch.
#
# These options correspond to the KCONFIG entries in the OpenWRT
# owrt-packages/qca-nss-ecm/Makefile.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://qca-nss-ecm.cfg"

KERNEL_CONFIG_FRAGMENTS:append = " ${UNPACKDIR}/qca-nss-ecm.cfg"
