include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=dataipa
PKG_VERSION:=1.0
PKG_RELEASE:=1

LOCAL_SRC := $(TOPDIR)/src/dataipa

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
  SECTION:=ipa
  CATEGORY:= External Kernel Modules
  TITLE:=Driver for IPA
endef

define KernelPackage/$(PKG_NAME)/Description
 This package contains a driver for the IPA.
endef


define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
	$(Build/Patch)
endef

MAKE_OPTS:= \
    M="$(PKG_BUILD_DIR)"


define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	$(MAKE_OPTS)\
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/gsi/gsim.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipam.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipanetm.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipa_clients/ecmipam.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipatestm.ko
	$(INSTALL_DIR) $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/gsi/gsim.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipam.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipanetm.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipa_clients/rndisipam.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipa_clients/ecmipam.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipa_clients/ipa_clientsm.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipatestm.ko $(1)/lib/modules/ipa/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/dataipa.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_dataipa_le  $(1)/etc/initscripts/
endef

$(eval $(call KernelPackage,dataipa))
