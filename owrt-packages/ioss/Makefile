include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=ioss
PKG_VERSION:=1.0
PKG_RELEASE:=1

LOCAL_SRC ?= $(TOPDIR)/src/kernel/msm-5.4/techpack/data-eth/drivers/$(PKG_NAME)

include $(INCLUDE_DIR)/package.mk

define Package/ioss
  SECTION:=ioss
  CATEGORY:=Kernel modules
  SUBMENU:=Network Devices
  TITLE:= EMAC network driver
endef

define Package/ioss/Description
	IOSS Driver
endef

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
	$(CP) $(PKG_BUILD_DIR)/ioss.ko $(1)/usr/lib/
endef

define Package/ioss/install
	$(INSTALL_DIR) $(1)/lib/modules/$(LINUX_VERSION)-debug/kernel/techpack/data-eth/drivers/ioss/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ioss.ko $(1)/lib/modules/$(LINUX_VERSION)-debug/kernel/techpack/data-eth/drivers/ioss/
endef

$(eval $(call BuildPackage,ioss))
