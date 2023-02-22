include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=ioss
PKG_VERSION:=1.0
PKG_RELEASE:=1

ifeq ($(VARIANT),perf)
  COMPILE_MODE :=perf
else
  COMPILE_MODE :=debug
endif

LOCAL_SRC := $(TOPDIR)/src/data-eth/drivers/$(PKG_NAME)
EXTRA_ADDR :=lib/modules/$(LINUX_VERSION)-${COMPILE_MODE}/extra

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
 SECTION:=ioss
 CATEGORY:=Kernel modules
 SUBMENU:=Network Devices
 TITLE:= IOSS Driver
endef

define KernelPackage/$(PKG_NAME)/Description
	IOSS Driver
endef

TARGET_CFLAGS += -I$(TOOLCHAIN_DIR)/include/linux/ethtool.h

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	M="$(PKG_BUILD_DIR)" \
	CONFIG_IOSS=m \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/modules-symbols/$(PKG_NAME)
	mkdir -p $(1)/$(EXTRA_ADDR)/
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/ioss.ko $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/Module.symvers $(1)/usr/modules-symbols/$(PKG_NAME)/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/$(EXTRA_ADDR)/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ioss.ko $(1)/$(EXTRA_ADDR)/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ioss.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))