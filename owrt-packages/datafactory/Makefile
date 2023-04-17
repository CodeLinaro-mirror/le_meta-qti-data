include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/target.mk

PKG_NAME:=datafactory
PKG_VERSION:=1.0
PKG_RELEASE:=1

include $(INCLUDE_DIR)/package.mk


define Package/datafactory
  SECTION:=QTI
  CATEGORY:=QTI-PACKAGES
  TITLE:=datafactory
endef

define Package/datafactory/Description
        datafactory
endef

define Build/Configure
        @true
endef

define Build/Compile
        @true
endef

define Package/datafactory/install
	$(INSTALL_DIR) $(1)/etc/data/factory
	$(INSTALL_BIN) ./mobileap/server/files/factory_dhcp $(1)/etc/data/factory/
	$(INSTALL_BIN) ./mobileap/server/files/factory_firewall $(1)/etc/data/factory/
	$(INSTALL_BIN) ./mobileap/server/files/factory_network $(1)/etc/data/factory/
#	$(INSTALL_BIN) ./mobileap/server/files/factory_test $(1)/etc/data/factory/
endef

$(eval $(call BuildPackage,datafactory))
