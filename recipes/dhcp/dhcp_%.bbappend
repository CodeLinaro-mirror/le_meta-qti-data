do_install_append() {

  install -d ${D}${includedir}/dhcp
  install -d ${D}${includedir}/dhcp/arpa
  install -d ${D}${includedir}/dhcp/netinet
  install -d ${D}${includedir}/dhcp/omapip

  install -m 755 ${WORKDIR}/dhcp-4.4.2/includes/arpa/*.h ${D}${includedir}/dhcp/arpa/
  install -m 755 ${WORKDIR}/dhcp-4.4.2/includes/netinet/*.h ${D}${includedir}/dhcp/netinet
  install -m 755 ${WORKDIR}/dhcp-4.4.2/includes/omapip/*.h ${D}${includedir}/dhcp/omapip
  install -m 755 ${WORKDIR}/dhcp-4.4.2/includes/*.h ${D}${includedir}/dhcp/

}

FILES_${PN} += "${includedir}/dhcp/*.h"
FILES_${PN} += "${includedir}/dhcp/arpa/*.h"
FILES_${PN} += "${includedir}/dhcp/netinet/*.h"
FILES_${PN} += "${includedir}/dhcp/omapip/*.h"
