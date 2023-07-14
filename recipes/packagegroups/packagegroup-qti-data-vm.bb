SUMMARY = "QTI package group for data modules on VMs"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "\
    packagegroup-qti-data-vm \
    "

RDEPENDS_packagegroup-qti-data-vm += "\
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-vm-host qti-vm-tele', 'dataipa', '', d)} \
    iperf3 \
    ethtool \
    dhcpcd \
    iproute2 \
    tcpdump \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-vm-tele', 'data-eth', '', d)} \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'qti-vm-host qti-vm-tele', 'datarmnet', '', d)} \
    "
