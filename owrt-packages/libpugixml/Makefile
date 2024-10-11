include $(TOPDIR)/rules.mk

PKG_NAME:=libpugixml
PKG_VERSION:=v1.11.4

PKG_SOURCE_PROTO:=git
PKG_SOURCE_URL:=https://github.com/zeux/pugixml.git
PKG_SOURCE_VERSION:=08b3433180727ea2f78fe02e860a08471db1e03c
PKG_HASH:=8ddf57b65fb860416979a3f0640c2ad45ddddbbafa82508ef0a0af3ce7061716

PKG_MAINTAINER:=
PKG_LICENSE:=MIT
PKG_LICENSE_FILES:=LICENSE

include $(INCLUDE_DIR)/package.mk

define Package/libpugixml
  SECTION:=Data
  CATEGORY:=QTI-PACKAGES
  TITLE:=libpugixml
  URL:=https://github.com/zeux/pugixml
  DEPENDS:=+libstdcpp
endef

define Package/libpugixml/description
  pugixml is a C++ XML processing library, which consists of a DOM-like interface
  with rich traversal/modification capabilities, an extremely fast XML parser which
  constructs the DOM tree from an XML file/buffer, and an XPath 1.0 implementation
  for complex data-driven tree queries. Full Unicode support is also available,
  with Unicode interface variants and conversions between different Unicode encodings
  (which happen automatically during parsing/saving).
endef


TARGET_CFLAGS += -g \
			-D__packed__=

TARGET_CXXFLAGS += -flto

TARGET_LDFLAGS += \
				-lstdc++

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(1)/usr/include/pugixml
	mkdir -p $(1)/usr/lib
	$(CP) $(PKG_BUILD_DIR)/src/*.hpp $(1)/usr/include/pugixml
	$(CP) $(PKG_BUILD_DIR)/src/lib/*.so* $(1)/usr/lib
endef

define Package/libpugixml/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/src/lib/libpugixml.so* $(1)/usr/lib/
endef

$(eval $(call BuildPackage,libpugixml))
