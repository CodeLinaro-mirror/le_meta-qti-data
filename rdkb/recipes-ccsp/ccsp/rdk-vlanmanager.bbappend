FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://00001-Vlanmanager-Crash-Fix.patch \
    file://00002-vlanmanager-fix-for-leak.patch \
    file://00003-Ipv6-Global-address.patch \
"

DEPENDS += " ccsp-psm libsyswrapper utopia "

LDFLAGS_aarch64 += " -lutctx -lprivilege  -lbreakpadwrapper "
