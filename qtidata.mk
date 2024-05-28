include $(INCLUDE_DIR)/target.mk

QTIDATA:=rmnetctl libpugixml

###Add target specific packages
ifeq ($(BOARD),sdx35)
    QTIDATA+= ipanat ipacm kmod-dataipa kmod-datarmnet kmod-ioss kmod-emac_ioss datafactory kmod-sfe kmod-gsb
    QTIDATAM2+= ipanat kmod-dataipa kmod-datarmnet kmod-ioss kmod-emac_ioss datafactory kmod-sfe kmod-gsb
else ifeq ($(BOARD),sdx65)
    QTIDATA+= kmod-rmnet-core kmod-rmnet-ctl qps615 ioss qps615-ioss ipanat ipacm
else ifeq ($(BOARD),sdx75)
    QTIDATA+= ipanat ipacm kmod-dataipa kmod-datarmnet
endif
