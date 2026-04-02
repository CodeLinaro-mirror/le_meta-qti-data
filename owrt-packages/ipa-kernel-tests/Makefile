include $(TOPDIR)/rules.mk

PKG_NAME:=ipa-kernel-tests
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause & BSD-3-Clause-Clear
PKG_FIXUP:=autoreconf

DATAIPA_TOP:=$(TOPDIR)/src/dataipa
LOCAL_SRC:=$(DATAIPA_TOP)/kernel-tests

include $(INCLUDE_DIR)/package.mk

ifeq ($(BOARD),sdx35)
	LOCAL_SRC:=$(TOPDIR)/src/dataipa/kernel-tests

else ifeq ($(BOARD),sdx65)
	LOCAL_SRC:=$(TOPDIR)/src/kernel/msm-5.4/techpack/dataipa/kernel-tests

else ifeq ($(BOARD),sdx75)
	LOCAL_SRC:=$(TOPDIR)/src/dataipa/kernel-tests
else ifeq ($(BOARD),sdx85)
	LOCAL_SRC:=$(TOPDIR)/src/dataipa/kernel-tests
else ifeq ($(BOARD),echo)
	LOCAL_SRC:=$(TOPDIR)/src/dataipa/kernel-tests
endif

define Package/$(PKG_NAME)
	SECTION:=ipa
	CATEGORY:=Data Services Open Source Software
	TITLE:=ipa-kernel-tests
	DEPENDS:=+ipanat +libstdcpp
endef

define Package/$(PKG_NAME)/Description
 ipa-kernel-tests
endef

TARGET_CFLAGS += -g \
	-I. \
	-I$(STAGING_DIR)/usr/include/libxml2 \
	-I$(STAGING_DIR)/usr/include/ipanat \
	-I$(STAGING_DIR)/usr/include/ipa_test_module

TARGET_LDFLAGS += \
	-L$(STAGING_DIR)/usr/lib \
	-lipanat \
	-lstdc++

define Build/Prepare
	$(INSTALL_DIR) $(PKG_BUILD_DIR)
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)
	$(Build/Patch)
endef

MAKE_PATH=.

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ipa_kernel_tests $(1)/usr/bin
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
