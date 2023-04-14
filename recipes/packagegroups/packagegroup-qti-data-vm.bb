SUMMARY = "QTI package group for data modules on VMs"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-data-vm \
    "

RDEPENDS_packagegroup-qti-data-vm += "\
    iperf \
    ethtool \
    dhcpcd \
    iproute2 \
    tcpdump \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-vm-tele', 'data-eth', '', d)} \
    "
