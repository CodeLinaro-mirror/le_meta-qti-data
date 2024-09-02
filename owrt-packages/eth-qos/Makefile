include $(TOPDIR)/rules.mk

PKG_NAME:=eth-qos
PKG_VERSION:=1
PKG_RELEASE:=1

PKG_BUILD_DIR:=$(BUILD_DIR)/$(PKG_NAME)-$(PKG_VERSION)

LOCAL_SRC := $(TOPDIR)/src/data-eth/cli

include $(INCLUDE_DIR)/package.mk

define Build/Compile
endef

define Package/$(PKG_NAME)
	SECTION:=Data
	CATEGORY:=QTI-PACKAGES
	TITLE:=eth-qos
	USERID:=ethqos:ethqos
	DEPENDS:=+bash
endef

define Package/$(PKG_NAME)/description
	CLI for EMAC Quality of Service Handling
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(LOCAL_SRC)/eth-qos $(1)/usr/sbin/eth-qos
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) files/stmmac.init $(1)/etc/init.d/
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
