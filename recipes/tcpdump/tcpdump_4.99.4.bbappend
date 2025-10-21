#Add qti specific changes for tcpdump_4.99.4 package

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}-${PV}:"

SRC_URI:append = "file://configure.patch \
                  file://tcpdump_configure_no_-O2.patch \
                "
