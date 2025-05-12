include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/package.mk
include $(INCLUDE_DIR)/cmake.mk

PKG_NAME:=nanopb
PKG_VERSION:=0.4.9.1
PKG_RELEASE:=1

PKG_SOURCE:=$(PKG_VERSION).tar.gz
PKG_SOURCE_URL:=https://github.com/nanopb/nanopb/archive/refs/tags/
PKG_HASH:=4575944a468718ef25f05eb01d994364650b581563089a9841986bb1e460eac3

PKG_BUILD_DIR:=$(BUILD_DIR)/nanopb-$(PKG_VERSION)

PKG_MAINTAINER:=Petteri Aimonen <jpa@nanopb.mail.kapsi.fi>
PKG_LICENSE:=Zlib
PKG_LICENSE_FILES:=LICENSE

define Package/nanopb
  CATEGORY:=Utilities
  DEPENDS:=+protobuf
  TITLE:=NanoPB
  URL:=https://github.com/nanopb/nanopb
endef

define Package/nanopb/description
  Nanopb is an ANSI-C library for encoding and decoding messages in
  Google's Protocol Buffers format with minimal requirements for RAM
  and code space. It is primarily suitable for 32-bit microcontrollers.
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)
	tar -xzf $(DL_DIR)/$(PKG_SOURCE) -C $(PKG_BUILD_DIR)
	$(CP) $(PKG_BUILD_DIR)/nanopb-$(PKG_VERSION)/* $(PKG_BUILD_DIR)
	rm -rf $(PKG_BUILD_DIR)/nanopb-$(PKG_VERSION)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(1)/usr/include/nanopb
	$(INSTALL_DIR) $(1)/usr/lib/nanopb
	$(INSTALL_DIR) $(1)/usr/bin/nanopb/generator
	$(CP) $(PKG_BUILD_DIR)/*.h $(1)/usr/include/nanopb
	$(CP) $(PKG_BUILD_DIR)/*.c $(1)/usr/include/nanopb
	$(CP) $(PKG_BUILD_DIR)/generator/* $(1)/usr/bin/nanopb/generator
endef


$(eval $(call BuildPackage,nanopb))
