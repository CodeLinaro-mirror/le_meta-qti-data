include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=rmnetctl
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_FIXUP:=autoreconf

LOCAL_SRC:=$(TOPDIR)/src/data-oss/rmnetctl

#PKG_BUILD_DIR:=$(BUILD_DIR)/$(PKG_NAME)-$(PKG_VERSION)

include $(INCLUDE_DIR)/package.mk
CONFIGURE_ARGS += --with-glib

define Package/rmnetctl
 SECTION:=Dataservices
 CATEGORY:=Data Services Open Source Software
 TITLE:=RMnet Data Driver User space Library
endef

define Package/rmnetctl/Description
 Data Services Open Source Software
endef

TARGET_CFLAGS += -Werror -Wall -fpic

define Build/Prepare
#$(warning "********************Host prepare *********************")
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(1)/usr/include
	mkdir -p $(1)/usr/lib
	$(CP) $(PKG_BUILD_DIR)/inc/*.h $(1)/usr/include/
	$(CP) $(PKG_BUILD_DIR)/lib/*.so* $(1)/usr/lib/
endef

define Package/rmnetctl/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_BUILD_DIR)/lib/*.so* $(1)/usr/lib
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/bin/rmnetcli $(1)/usr/bin
endef

$(eval $(call BuildPackage,rmnetctl))

