FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://00001-Vlanmanager-Crash-Fix.patch \
    file://00002-vlanmanager-fix-for-leak.patch \
    file://00003-Ipv6-Global-address.patch \
    file://00004-qti-VlanManager_perf_build_fix.patch \
"

DEPENDS += " ccsp-psm libsyswrapper utopia "

LDFLAGS_aarch64 += " -lutctx -lprivilege  -lbreakpadwrapper "

CFLAGS_append += " -Wno-error=stringop-truncation -Wno-error=stringop-overflow "
