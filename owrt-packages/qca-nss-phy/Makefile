include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=qca-nss-phy
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=ISC

ifeq ($(VARIANT),perf)
  COMPILE_MODE:=perf
else
  COMPILE_MODE:=debug
endif

LOCAL_SRC:=$(TOPDIR)/src/data-eth/drivers/$(PKG_NAME)
EMAC_ADDR:=lib/modules/emac/

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
 SECTION:=qca-nss-phy
 CATEGORY:=Kernel modules
 SUBMENU:=Network Devices
 TITLE:=qca-nss-phy driver
endef

define KernelPackage/$(PKG_NAME)/Description
	qca-nss-phy Driver
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/../../* $(PKG_BUILD_DIR)/
endef

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	M="$(PKG_BUILD_DIR)" \
	KBUILD_EXTRA_SYMBOLS=$(STAGING_DIR_TARGET)/usr/modules-symbols/ioss/Module.symvers \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(1)/$(EMAC_ADDR)/
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/qca81xx-phy.ko $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(SIGN_KEY) $(PKG_BUILD_DIR)/qca81xx-phy.ko
	$(INSTALL_DIR) $(1)/$(EMAC_ADDR)/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qca81xx-phy.ko $(1)/$(EMAC_ADDR)/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qca81xx-phy.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/qca-nss-phy.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_qca_nss_phy  $(1)/etc/initscripts/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
