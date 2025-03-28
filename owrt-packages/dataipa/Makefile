include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=dataipa
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause

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
	$(INSTALL_DIR) $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/uapi/linux/msm_ipa.h $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/linux/* $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/uapi/linux/ipa_qmi_service_v01.h $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/uapi/linux/rmnet_ipa_fd_ioctl.h $(STAGING_DIR)/usr/include/linux
endef

define KernelPackage/$(PKG_NAME)/install
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/gsim.ko
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/ipam.ko
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/ipanetm.ko
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/ipatestm.ko
	$(INSTALL_DIR) $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/gsim.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ipam.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ipanetm.ko $(1)/lib/modules/ipa/
#	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ipatestm.ko $(1)/lib/modules/ipa/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/dataipa.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_dataipa_le  $(1)/etc/initscripts/

#install dataipa ko's in /bin/targets/$(TARGET_VARIANT)/modules to be shown in owrt CRM APPS
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/gsim.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ipam.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ipanetm.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,dataipa))
