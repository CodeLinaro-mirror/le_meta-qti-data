include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=aquantia
PKG_VERSION:=1
PKG_RELEASE:=1

PKG_SOURCE_PROTO:=git
PKG_SOURCE_URL:=https://git.codelinaro.org/clo/le/external/github.com/aquantia/linux-aqr-phy-only
PKG_SOURCE_VERSION:=1a4467eb0faa2a9fae96d1a68c79f9ea77342436
PKG_FIXUP:=autoreconf

PKG_MAINTAINER:=
PKG_LICENSE:=GPLv2-only
PKG_LICENSE_FILES:=LICENSE

ifeq ($(VARIANT),perf)
  COMPILE_MODE :=perf
else
  COMPILE_MODE :=debug
endif

include $(INCLUDE_DIR)/package.mk

define KernelPackage/aquantia
  SECTION:=aquantia-phydriver
  CATEGORY:=EMAC
  SUBMENU:=AQR113
  TITLE:= aquantia PHY driver
endef

define KernelPackage/aquantia/description
 PHY driver
endef

define Build/Prepare
	$(call Build/Prepare/Default)
	mv -f $(PKG_BUILD_DIR)/aquantia/* $(PKG_BUILD_DIR)/
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
	$(CP) $(PKG_BUILD_DIR)/aquantia.ko $(1)/usr/lib/
endef

define KernelPackage/aquantia/install
	$(SIGN_KEY) $(PKG_BUILD_DIR)/aquantia.ko
	$(INSTALL_DIR) $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/aquantia.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) ./files/aquantia.init $(1)/etc/init.d
	$(INSTALL_BIN) ./files/start_aquantia  $(1)/etc/initscripts/
endef

$(eval $(call KernelPackage,aquantia))
