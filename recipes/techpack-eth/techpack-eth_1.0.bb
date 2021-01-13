SUMMARY = "Techpack by Data Ethernet"
DESCRIPTION = "Helper recipe to build Data Ethernet Techpack drivers out-of-tree or in devshell"

export ETH_OBJDIR = "${WORKDIR}/kobj"
export ETH_SRCDIR = "${WORKSPACE}/kernel/msm-5.4/techpack/data-eth"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${ETH_SRCDIR}/data-eth.c;\
beginline=1;endline=4;md5=35144d93ffd061a7458db62d36405265"

inherit module

# Files from meta-qti-data
SRC_URI += "file://kobj/Makefile"

S = "${ETH_OBJDIR}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "kernel-module-data_eth"

# vim: syntax=bitbake
