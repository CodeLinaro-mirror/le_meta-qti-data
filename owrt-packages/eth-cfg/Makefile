include $(TOPDIR)/rules.mk

PKG_NAME:=eth-cfg
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_LICENSE:=GPL-2.0-only

PKG_BUILD_DIR:=$(BUILD_DIR)/$(PKG_NAME)-$(PKG_VERSION)

LOCAL_SRC := $(TOPDIR)/src/data-eth/cli

include $(INCLUDE_DIR)/package.mk

define Build/Compile
endef

define Package/$(PKG_NAME)
	SECTION:=Data
	CATEGORY:=QTI-PACKAGES
	TITLE:=eth-cfg
	USERID:=ethcfg:ethcfg
	DEPENDS:=+bash
endef

define Package/$(PKG_NAME)/description
	CLI for changing ethernet configuration
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(LOCAL_SRC)/eth-cfg $(1)/usr/sbin/eth-cfg
endef

$(eval $(call BuildPackage,$(PKG_NAME)))