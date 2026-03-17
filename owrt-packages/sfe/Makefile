include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=shortcut-fe
PKG_VERSION:=1.0
LOCAL_SRC := $(TOPDIR)/src/shortcut-fe/$(PKG_NAME)
PKG_LICENSE:=BSD-3-Clause

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
	SECTION:=Kernel Modules
	CATEGORY:= External Kernel Modules
	TITLE:=shortcut-fe
endef

define KernelPackage/$(PKG_NAME)/Description
	shortcut-fe
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS:= \
	M="$(PKG_BUILD_DIR)"

define Build/Compile
	if [ "$(BOARD)" = "echo" ]; then \
		$(KERNEL_MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
	else \
		$(MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
	fi
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(STAGING_DIR)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/shortcut-fe.ko $(STAGING_DIR)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/shortcut-fe-ipv6.ko $(STAGING_DIR)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/shortcut-fe-cm.ko $(STAGING_DIR)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(SIGN_KEY) $(PKG_BUILD_DIR)/shortcut-fe.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/shortcut-fe-ipv6.ko
	$(SIGN_KEY) $(PKG_BUILD_DIR)/shortcut-fe-cm.ko
	$(INSTALL_DIR) $(1)/lib/modules/shortcut_fe
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/shortcut-fe.ko $(1)/lib/modules/shortcut_fe
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/shortcut-fe-ipv6.ko $(1)/lib/modules/shortcut_fe
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/shortcut-fe-cm.ko $(1)/lib/modules/shortcut_fe
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
