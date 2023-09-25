include $(TOPDIR)/rules.mk

PKG_NAME:=ipacm
PKG_VERSION:=1
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause

LOCAL_SRC:=$(TOPDIR)/src/data-ipa-cfg-mgr/ipacm

include $(INCLUDE_DIR)/package.mk

define Package/$(PKG_NAME)
	SECTION:=ipa
	CATEGORY:=Data Services Open Source Software
	TITLE:=ipacm
	DEPENDS:=+libxml2 +libnetfilter-conntrack +ipanat +libstdcpp +libexecinfo
	USERID:=radio=1001:radio=1001 radio=1001:diag=2901 radio=1001:inet=3003
endef

define Package/$(PKG_NAME)/Description
 IPACM
endef

TARGET_CFLAGS += -Og \
	-DDEBUG \
	-DFEATURE_ETH_BRIDGE_LE \
	-DFEATURE_IPA_V3 \
	-DFEATURE_IPPASS_WA \
	-DFEATURE_VLAN_MPDN \
	-D__UAPI_DEF_ETHHDR=0 \
	-DTRUE=1 \
	-DFALSE=0 \
	-DFEATURE_IPACM_PER_CLIENT_STATS \
	-DFEATURE_IPACM_UL_FIREWALL \
	-DFEATURE_IPA_IPSEC \
	-DIPA_V6_UL_WL_FIREWALL_HANDLE \
	${LIBXML_CFLAGS} \
	-I../inc \
	-I$(WORKSPACE)/common/inc \
	-I$(STAGING_DIR)/usr/include/libxml2 \
	-I$(STAGING_DIR)/usr/include/ipanat

TARGET_LDFLAGS += \
	-L$(STAGING_DIR)/usr/lib \
	-lxml2 \
	-lpthread \
	-lnetfilter_conntrack \
	-lipanat \
	-lstdc++ \
        -lexecinfo

define Build/Prepare
        $(INSTALL_DIR) $(PKG_BUILD_DIR)
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)
	$(Build/Patch)
endef

MAKE_PATH=src

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	$(INSTALL_DIR) $(1)/usr/include/ipacm
	$(CP) $(PKG_BUILD_DIR)/inc/*.h $(1)/usr/include/ipacm
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/src/bin/ipacm $(1)/usr/bin
	$(INSTALL_DIR) $(1)/etc/data/ipa
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/src/IPACM_cfg.xml $(1)/etc/data/ipa
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/src/IPACM_cfg.xml $(1)/etc/data/ipa/factory_IPACM_cfg.xml
	$(INSTALL_DIR) $(1)/etc/capabilities
	$(INSTALL_DATA) ./files/ipacm.json $(1)/etc/capabilities
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/ipacm.init $(1)/etc/init.d
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
