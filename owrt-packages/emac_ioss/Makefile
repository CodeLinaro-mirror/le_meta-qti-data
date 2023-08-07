include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=emac_ioss
PKG_VERSION:=1.0
PKG_RELEASE:=1

ifeq ($(VARIANT),perf)
  COMPILE_MODE :=perf
else
  COMPILE_MODE :=debug
endif

LOCAL_SRC := $(TOPDIR)/src/data-eth/drivers/$(PKG_NAME)
EMAC_ADDR :=lib/modules/emac/

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
 SECTION:=emac_ioss
 CATEGORY:=Kernel modules
 SUBMENU:=Network Devices
 TITLE:= EMAC IOSS Driver
endef

define KernelPackage/$(PKG_NAME)/Description
	EMAC IOSS Driver
endef

ifneq ($(KERNEL_PLATFORM_TARGET), sdxbaagha-128m)

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	M="$(PKG_BUILD_DIR)" \
	CONFIG_EMAC_IOSS=m \
	KBUILD_EXTRA_SYMBOLS=$(STAGING_DIR_TARGET)/usr/modules-symbols/ioss/Module.symvers \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(1)/$(EMAC_ADDR)/
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/iemac_ioss.ko $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/$(EMAC_ADDR)/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/iemac_ioss.ko $(1)/$(EMAC_ADDR)/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/iemac_ioss.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

else

define Build/Prepare
	@true
endef

define Build/Compile
	@true
endef

define Build/InstallDev
	@true
endef

define KernelPackage/$(PKG_NAME)/install
	@true
endef

endif

$(eval $(call KernelPackage,$(PKG_NAME)))
