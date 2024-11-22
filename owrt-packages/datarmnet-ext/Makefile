include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=datarmnet-ext
PKG_RELEASE:=1.0
LOCAL_SRC:=$(TOPDIR)/src/datarmnet-ext/sch

include $(INCLUDE_DIR)/package.mk

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/../* $(PKG_BUILD_DIR)/
endef

define KernelPackage/$(PKG_NAME)
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=Drivers for handling RMNET extensions
endef

define KernelPackage/$(PKG_NAME)/Description
Drivers for handling RMNET extensions
endef

MAKE_OPTS:= \
        M="$(PKG_BUILD_DIR)"

define Build/Compile
        $(MAKE) -C "$(LINUX_DIR)" \
                $(MAKE_OPTS) \
                modules
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/rmnet_sch.ko
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rmnet_sch.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rmnet_sch.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/$(PKG_NAME).init $(1)/etc/init.d
endef

$(eval $(call KernelPackage,$(PKG_NAME)))

