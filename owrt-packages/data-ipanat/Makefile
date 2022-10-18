include $(TOPDIR)/rules.mk

PKG_NAME:=ipanat
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause

include $(INCLUDE_DIR)/package.mk

ifeq ($(BOARD),sdx35)
	LOCAL_SRC:=$(TOPDIR)/src/dataipa/ipanat

else ifeq ($(TARGET_MACHINE),sdx65)
	LOCAL_SRC:=$(TOPDIR)/src/kernel/msm-5.4/techpack/dataipa/ipanat

else ifeq ($(BOARD),sdx75)
	LOCAL_SRC:=$(TOPDIR)/src/dataipa/ipanat
endif

define Package/$(PKG_NAME)
	SECTION:=ipa
	CATEGORY:=Data Services Open Source Software
	TITLE:=ipanat
	DEPENDS:=+librt +libstdcpp
endef

define Package/$(PKG_NAME)/Description
 IPA NAT shared library
endef

TARGET_CFLAGS += -g \
	-I../inc \
	-I$(WORKSPACE)/common/inc

TARGET_LDFLAGS += \
	-lrt \
	-lstdc++

define Build/Prepare
	$(INSTALL_DIR) $(PKG_BUILD_DIR)
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)
	$(Build/Patch)
endef

MAKE_PATH=src

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	$(INSTALL_DIR) $(1)/usr/include/ipanat
	$(CP) $(PKG_BUILD_DIR)/inc/*.h $(1)/usr/include/ipanat
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_BUILD_DIR)/src/lib/libipanat.so* $(1)/usr/lib
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/lib
	$(CP) $(PKG_BUILD_DIR)/src/lib/libipanat.so* $(1)/usr/lib
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
