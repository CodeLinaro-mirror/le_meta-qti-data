include $(INCLUDE_DIR)/target.mk

QTIDATA:=rmnetctl libpugixml newstrongswan newstrongswan-full

###Add target specific packages 
ifeq ($(BOARD),sdx35)
  	QTIDATA+=
else ifeq ($(BOARD),sdx65)
  	QTIDATA+= kmod-rmnet-core kmod-rmnet-ctl qps615 ioss qps615-ioss ipanat ipacm
else ifeq ($(BOARD),sdx75)
	QTIDATA+= ipanat ipacm kmod-dataipa kmod-datarmnet kmod-aquantia flashBurn aqr113-firmware kmod-ioss kmod-emac_ioss kmod-gsb kmod-rtsp_alg kmod-rtl8261 datafactory kmod-r8125 avahi-nodbus-daemon kmod-r8125_ioss kmod-sfe kmod-aqc_ioss
endif
