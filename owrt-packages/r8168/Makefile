include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=r8168
PKG_VERSION:=1.0
PKG_RELEASE:=1

ifeq ($(VARIANT),perf)
  COMPILE_MODE :=perf
else
  COMPILE_MODE :=debug
endif

LOCAL_SRC := $(TOPDIR)/src/data-eth/drivers/$(PKG_NAME)/src
EMAC_ADDR :=lib/modules/emac/

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
 SECTION:=r8168
 CATEGORY:=Kernel modules
 SUBMENU:=Network Devices
 TITLE:= r8168 Driver
endef

define KernelPackage/$(PKG_NAME)/Description
	r8168 Driver
endef

TARGET_CFLAGS += -I$(TOOLCHAIN_DIR)/include/linux/ethtool.h

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	M="$(PKG_BUILD_DIR)" \
	CONFIG_R8168=m \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/modules-symbols/$(PKG_NAME)
	mkdir -p $(1)/$(EMAC_ADDR)/
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/r8168.ko $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/Module.symvers $(1)/usr/modules-symbols/$(PKG_NAME)/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/$(EMAC_ADDR)/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/r8168.ko $(1)/$(EMAC_ADDR)/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/r8168.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/r8168.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_r8168_modules  $(1)/etc/initscripts/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
