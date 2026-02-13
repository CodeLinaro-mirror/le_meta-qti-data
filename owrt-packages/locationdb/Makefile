include $(TOPDIR)/rules.mk

PKG_NAME:=locationdb
PKG_VERSION:=1
PKG_RELEASE:=1

PKG_SOURCE:=db-2019c.zip
PKG_SOURCE_URL:=https://codelinaro.jfrog.io/artifactory/codelinaro-le/
PKG_MD5SUM:=8ffbf97144f0bc827c6bd0c08a84328c
PKG_LICENSE:=ODbl-1.0
UNPACK_CMD=unzip -q -d $(1) $(DL_DIR)/$(PKG_SOURCE)

include $(INCLUDE_DIR)/package.mk

define Package/$(PKG_NAME)
	SECTION:=Data
	CATEGORY:=QTI-PACKAGES
	TITLE:=locationdb
	URL:=https://github.com/BertoldVdb/ZoneDetect/tree/master/database
endef

define Package/$(PKG_NAME)/Description
	locationdb
endef

define Build/Compile
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/bin
	$(INSTALL_DATA) $(PKG_BUILD_DIR)/out_v1/timezone21.bin $(1)/bin
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
