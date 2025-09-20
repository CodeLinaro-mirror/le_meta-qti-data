include $(INCLUDE_DIR)/target.mk

QTIDATA:=rmnetctl libpugixml newstrongswan newstrongswan-full

###Add target specific packages
ifeq ($(BOARD),sdx35)
  	QTIDATA+= ipanat ipacm kmod-dataipa datafactory
else ifeq ($(BOARD),sdx65)
  	QTIDATA+= kmod-rmnet-core kmod-rmnet-ctl qps615 ioss qps615-ioss ipanat ipacm
else ifeq ($(BOARD),sdx75)
	QTIDATA+= ipanat ipacm kmod-dataipa kmod-datarmnet kmod-datarmnet-ext kmod-ioss kmod-emac_ioss kmod-gsb kmod-rtsp_alg datafactory kmod-r8125 kmod-r8168 avahi-nodbus-daemon kmod-r8125_ioss kmod-r8168_ioss kmod-sfe thermal-eth-netlink kmod-aqc_ioss hostap phytool kmod-r8152 zonedetect locationdb ianatzdata kmod-smem-mailbox eth-qos eth-cfg set_eth_perf
	QTIDATA512M:=kmod-dataipa ipacm rmnetctl libpugixml kmod-datarmnet
else ifeq ($(BOARD),sdx85)
	QTIDATA+= kmod-dataipa ipanat ipacm kmod-datarmnet kmod-datarmnet-ext datafactory avahi-nodbus-daemon thermal-eth-netlink hostap phytool zonedetect locationdb ianatzdata eth-qos kmod-smem-mailbox kmod-data-devicetree kmod-ioss kmod-emac_ioss kmod-rtl8125 kmod-r8125_ioss eth-cfg set_eth_perf kmod-qca-nss-sfe kmod-qca-nss-ecm-premium kmod-aquantia nanopb kmod-r8152 kmod-atlantic_fwd kmod-aqc_ioss
	QTIDATA512M:=kmod-dataipa ipacm rmnetctl libpugixml kmod-datarmnet kmod-data-devicetree
endif
ifneq ($(PRPL_VERSION),)
	QTIDATA:=kmod-dataipa ipanat ipacm kmod-rtsp_alg kmod-ioss kmod-emac_ioss avahi-nodbus-daemon thermal-eth-netlink hostap phytool zonedetect locationdb ianatzdata eth-qos eth-cfg set_eth_perf kmod-smem-mailbox kmod-data-devicetree kmod-aquantia
 
	QTIDATA512M:=
endif
