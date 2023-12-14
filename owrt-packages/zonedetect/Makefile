include $(TOPDIR)/rules.mk

PKG_NAME:=zonedetect
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause

PKG_SOURCE_PROTO:=git
PKG_SOURCE_URL:=https://github.com/BertoldVdb/ZoneDetect
PKG_SOURCE_VERSION:=88e927d2302966993724f06d57e89cc4bf6d5e35

include $(INCLUDE_DIR)/package.mk

define Package/$(PKG_NAME)
	SECTION:=Data
	CATEGORY:=QTI-PACKAGES
	TITLE:=zonedetect
	URL:=https://github.com/BertoldVdb/ZoneDetect
	DEPENDS:=+libstdcpp +libc
endef

define Package/$(PKG_NAME)/Description
	zonedetect
endef

define Build/Configure
	$(INSTALL_DIR) $(PKG_BUILD_DIR)
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/bin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/tzdetect $(1)/bin
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
