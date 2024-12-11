include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=smem-mailbox
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause

LOCAL_SRC := $(TOPDIR)/src/data-kernel/drivers/smem-mailbox/

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
  SECTION:=QTI
  CATEGORY:= External Kernel Modules
  TITLE:=Driver for SMEM-Mailbox
endef

define KernelPackage/$(PKG_NAME)/Description
 This package contains a driver for the SMEM-Mailbox.
endef


define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS:= \
    M="$(PKG_BUILD_DIR)"


define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/lib/
	mkdir -p  $(1)/usr/modules-symbols/$(PKG_NAME)
	$(CP) $(PKG_BUILD_DIR)/Module.symvers $(1)/usr/modules-symbols/$(PKG_NAME)/
	mkdir -p  $(STAGING_DIR)/usr/include/smemMailboxHeaders
	$(CP) $(PKG_BUILD_DIR)/include/*.h $(STAGING_DIR)/usr/include/smemMailboxHeaders
endef

define KernelPackage/$(PKG_NAME)/install
	$(SIGN_KEY) $(PKG_BUILD_DIR)/smem-mailbox.ko
	$(INSTALL_DIR) $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/smem-mailbox.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/smem-mailbox.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_smem-mailbox_le  $(1)/etc/initscripts/

#install smem-mailbox ko's in /bin/targets/$(TARGET_VARIANT)/modules to be shown in owrt CRM APPS
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/smem-mailbox.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,smem-mailbox))
