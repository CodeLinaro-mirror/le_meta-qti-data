include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=rtl8261
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_FIXUP:=autoreconf

PKG_MAINTAINER:=
PKG_LICENSE:=BSD-3-Clause
PKG_LICENSE_FILES:=LICENSE

ifeq ($(VARIANT),perf)
  COMPILE_MODE :=perf
else
  COMPILE_MODE :=debug
endif

include $(INCLUDE_DIR)/package.mk
EXTRA_ADDR :=lib/modules/$(LINUX_VERSION)-${COMPILE_MODE}/extra

LOCAL_SRC:= $(TOPDIR)/src/data-eth/drivers/rtl8261/rtk-ms-linuxdrv-phy-1.0.0.PATCH-032_full

define KernelPackage/$(PKG_NAME)
  SECTION:=phydriver
  CATEGORY:=EMAC
  SUBMENU:=RTL8261
  TITLE:= RTL8261 PHY driver
endef

define KernelPackage/$(PKG_NAME)/description
 Realtek 8261 PHY driver
endef

TARGET_CFLAGS += -Werror -Wall

define Build/Prepare
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
	$(Build/Patch)
	$(CP) ./define_modules.bzl $(PKG_BUILD_DIR)/
	$(CP) ./BUILD.bazel $(PKG_BUILD_DIR)/
endef

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	M="$(PKG_BUILD_DIR)" \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/rtl8261.ko $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(SIGN_KEY) $(PKG_BUILD_DIR)/rtl8261.ko
	$(INSTALL_DIR) $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rtl8261.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/rtl8261.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_rtl8261  $(1)/etc/initscripts/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
