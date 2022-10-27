include $(INCLUDE_DIR)/target.mk

QTIDATA:=rmnetctl libpugixml ipanat ipacm

###Add target specific packages
ifeq ($(BOARD),sdx35)
  	QTIDATA+=
else ifeq ($(BOARD),sdx65)
  	QTIDATA+= kmod-rmnet-core kmod-rmnet-ctl qps615 ioss qps615-ioss
else ifeq ($(BOARD),sdx75)
  	QTIDATA+=
endif
