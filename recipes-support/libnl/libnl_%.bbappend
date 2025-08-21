FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI:remove = "file://rx_packets_stat.patch"

PR = "r1"
