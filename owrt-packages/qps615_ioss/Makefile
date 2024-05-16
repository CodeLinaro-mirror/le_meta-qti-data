include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=qps615-ioss
PKG_VERSION:=1.0
PKG_LICENSE:=BSD-3-Clause
PKG_RELEASE:=1

LOCAL_SRC ?= $(TOPDIR)/src/kernel/msm-5.4/techpack/data-eth/drivers/qps615_ioss

include $(INCLUDE_DIR)/package.mk

define Package/qps615-ioss
  SECTION:=qps615-ioss
  CATEGORY:=Kernel modules
  SUBMENU:=Network Devices
  TITLE:= EMAC network driver
  DEPENDS:= +qps615 +ioss
endef

define Package/qps615-ioss/Description
	QPS615 IOSS Driver
endef

TARGET_LDFLAGS += \
		-L$(STAGING_DIR)/usr/lib \
		-lqps615 \
		-lioss

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/Configure
	@true
endef

define Build/Compile
	@true
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/qps615-ioss.ko $(1)/usr/lib/
endef

define Package/qps615-ioss/install
	$(INSTALL_DIR) $(1)/lib/modules/$(LINUX_VERSION)-debug/kernel/techpack/data-eth/drivers/qps615_ioss/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qps615-ioss.ko $(1)/lib/modules/$(LINUX_VERSION)-debug/kernel/techpack/data-eth/drivers/qps615_ioss/
endef

$(eval $(call BuildPackage,qps615-ioss))
