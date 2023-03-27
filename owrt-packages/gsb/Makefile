include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=generic-sw-bridge
PKG_VERSION:=1.0
LOCAL_SRC := $(TOPDIR)/src/data-kernel/drivers/$(PKG_NAME)

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
	SECTION:=Kernel Modules
	CATEGORY:= External Kernel Modules
	TITLE:=generic-sw-bridge
endef

define KernelPackage/$(PKG_NAME)/Description
	generic-sw-bridge driver
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS:= \
	M="$(PKG_BUILD_DIR)"

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	$(MAKE_OPTS) \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(STAGING_DIR)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/gsb.ko $(STAGING_DIR)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/generic_sw_bridge
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/gsb.ko $(1)/lib/modules/generic_sw_bridge
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
