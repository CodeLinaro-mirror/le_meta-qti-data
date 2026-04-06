include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=dataipa
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=GPL-2.0-only

LOCAL_SRC := $(TOPDIR)/src/dataipa

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
  SECTION:=Kernel Modules
  CATEGORY:= External Kernel Modules
  TITLE:=Driver for IPA
endef

define KernelPackage/$(PKG_NAME)/Description
 This package contains a driver for the IPA.
endef


define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS:= \
    M="$(PKG_BUILD_DIR)"

ifeq ($(BOARD),echo)
MAKE_OPTS += CONFIG_ARCH_ECHO=y
endif

ifeq ($(BOARD),sa535m)
MAKE_OPTS += CONFIG_ARCH_SA535M=y
endif

define Build/Compile
  if [ "$(BOARD)" = "echo" ]; then \
      $(KERNEL_MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
  else \
      $(MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
  fi
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/lib/
	$(INSTALL_DIR) $(STAGING_DIR)/usr/include/linux
	$(INSTALL_DIR) $(STAGING_DIR)/usr/include/dataipa
	$(CP) $(LOCAL_SRC)/exports/ipa_api.h $(STAGING_DIR)/usr/include/dataipa
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/uapi/linux/msm_ipa.h $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/linux/* $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/uapi/linux/ipa_qmi_service_v01.h $(STAGING_DIR)/usr/include/linux
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/include/uapi/linux/rmnet_ipa_fd_ioctl.h $(STAGING_DIR)/usr/include/linux
	$(INSTALL_DIR) $(STAGING_DIR)/usr/include/ipa_test_module
	$(CP) $(LOCAL_SRC)/drivers/platform/msm/ipa/ipa_test_module/* $(STAGING_DIR)/usr/include/ipa_test_module/
endef

define KernelPackage/$(PKG_NAME)/install
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/gsi/gsim.ko
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipam.ko
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipanetm.ko
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipatestm.ko
	$(INSTALL_DIR) $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/gsi/gsim.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipam.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipanetm.ko $(1)/lib/modules/ipa/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipatestm.ko $(1)/lib/modules/ipa/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/dataipa.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_dataipa_le  $(1)/etc/initscripts/
	$(INSTALL_DIR) $(1)/etc/data/ipa_be
	$(INSTALL_DATA) ./files/ipa_ini_file.ini $(1)/etc/data/ipa_be/ipa_ini_file.ini
	$(INSTALL_DIR) $(1)/lib/firmware/ipa_be
	ln -s /etc/data/ipa_be/ipa_ini_file.ini $(1)/lib/firmware/ipa_be/ipa_ini_file.ini

#install dataipa ko's in /bin/targets/$(TARGET_VARIANT)/modules to be shown in owrt CRM APPS
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/gsi/gsim.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipam.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipanetm.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/drivers/platform/msm/ipa/ipatestm.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
