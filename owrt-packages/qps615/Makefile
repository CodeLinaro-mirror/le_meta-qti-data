include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=qps615
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_FIXUP:=autoreconf

LOCAL_SRC ?= $(TOPDIR)/src/kernel/msm-5.4/techpack/data-eth/drivers/$(PKG_NAME)/src

include $(INCLUDE_DIR)/package.mk


TARGET_LDFLAGS := -lphylink

define Package/qps615
  SECTION:=qps615
  CATEGORY:=Kernel modules
  SUBMENU:=Network Devices
  TITLE:= EMAC network driver
  DEPENDS:= +kmod-phylink
endef

define Package/diag-router/Description
	EMAC Driver
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
	$(CP) $(PKG_BUILD_DIR)/qps615.ko $(1)/usr/lib/
endef

define Package/qps615/install
	$(INSTALL_DIR) $(1)/lib/modules/$(LINUX_VERSION)-debug/kernel/techpack/data-eth/drivers/qps615/src/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qps615.ko $(1)/lib/modules/$(LINUX_VERSION)-debug/kernel/techpack/data-eth/drivers/qps615/src/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/qps615.init $(1)/etc/init.d
endef

$(eval $(call BuildPackage,qps615))
