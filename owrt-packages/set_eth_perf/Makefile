include $(TOPDIR)/rules.mk

PKG_NAME:=set_eth_perf
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
	TITLE:=set_eth_perf
	DEPENDS:=+bash
endef

define Package/$(PKG_NAME)/description
	Script to enable RPS and setting SMP Affinity for linux kernel mapped IRQs for eth interfaces
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/etc/data
	$(INSTALL_BIN) $(LOCAL_SRC)/set_eth_perf $(1)/etc/data/set_eth_perf
endef

$(eval $(call BuildPackage,$(PKG_NAME)))