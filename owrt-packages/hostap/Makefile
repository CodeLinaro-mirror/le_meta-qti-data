
include $(TOPDIR)/rules.mk

PKG_NAME:=hostap
PKG_VERSION:=2.10
PKG_RELEASE:=$(AUTORELEASE)

PKG_SOURCE_PROTO:=git
PKG_SOURCE:=$(PKG_NAME)-$(PKG_VERSION).tar.xz
PKG_SOURCE_URL:=https://git.codelinaro.org/clo/le/hostap.git
#PKG_SOURCE_SUBDIR:=$(PKG_NAME)-$(PKG_VERSION)
PKG_SOURCE_VERSION:=cff80b4f7d3c0a47c052e8187d671710f48939e4

PKG_MAINTAINER:=https://w1.fi
PKG_LICENSE:=BSD
PKG_LICENSE_FILES:=LICENSE

PKG_BUILD_DIR:=$(BUILD_DIR)/$(PKG_NAME)-$(PKG_VERSION)

include $(INCLUDE_DIR)/package.mk

define Package/$(PKG_NAME)
	SECTION:=Data
	CATEGORY:=QTI-PACKAGES
	TITLE:=hostap
	USERID:=macsec:macsec
	URL:=https://w1.fi/hostap.git
	DEPENDS:=+libnl +libopenssl +libstdcpp
endef


define Package/$(PKG_NAME)/description
	MKA Authenticator and Supplicant Services
endef


TARGET_CFLAGS += -g \
	-D__packed__= \
	-I${STAGING_DIR}/linux-msm/usr/include \
	-I${STAGING_DIR}/usr/include/libnl3
TARGET_CXXFLAGS += -flto \
	-L$(STAGING_DIR)/usr/lib

TARGET_LDFLAGS += \
	-lstdc++

define Build/Prepare
	$(info In build prepare)
	tar xvf $(TOPDIR)/dl/$(PKG_SOURCE) --strip-components 1 -C $(PKG_BUILD_DIR)
	$(Build/Patch)
endef

define Build/Configure
	$(info In build and configure)
	$(Build/Configure/rebuild)
	cat ./files/hostapd.buildconfig > $(PKG_BUILD_DIR)/hostapd/.config
	cat ./files/wpa_supplicant.buildconfig > $(PKG_BUILD_DIR)/wpa_supplicant/.config
endef

TARGET_CFLAGS += -I$(STAGING_INCDIR)/linux-msm/usr/include

define Build/Compile
	$(info In build compile)
	$(MAKE) -C $(PKG_BUILD_DIR)/hostapd clean

	$(TARGET_CONFIGURE_OPTS) \
	CFLAGS="$(TARGET_CPPFLAGS) $(TARGET_CFLAGS)" \
	LDFLAGS="$(TARGET_LDFLAGS)" \
	$(MAKE) -C $(PKG_BUILD_DIR)/hostapd \
	M=$(PKG_BUILD_DIR)/hostapd

	$(MAKE) -C $(PKG_BUILD_DIR)/wpa_supplicant clean
	$(TARGET_CONFIGURE_OPTS) \
	CFLAGS="$(TARGET_CPPFLAGS) $(TARGET_CFLAGS)" \
	LDFLAGS="$(TARGET_LDFLAGS)" \
	$(MAKE) -C $(PKG_BUILD_DIR)/wpa_supplicant \
	M=$(PKG_BUILD_DIR)/wpa_supplicant
endef



define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/hostapd/hostapd $(1)/usr/sbin/mka_authenticator

	$(INSTALL_DIR) $(1)/etc/
	$(INSTALL_DATA) files/mka_authenticator-eth0.conf  $(1)/etc/
	$(INSTALL_DATA) files/mka_authenticator-eth1.conf  $(1)/etc/
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/wpa_supplicant/wpa_supplicant $(1)/usr/sbin/mka_supplicant

	$(INSTALL_DIR) $(1)/etc/
	$(INSTALL_DATA) files/mka_supplicant-eth0.conf  $(1)/etc/
	$(INSTALL_DATA) files/mka_supplicant-eth1.conf  $(1)/etc/

	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) files/mka-supplicant.init $(1)/etc/init.d/

	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) files/mka-authenticator.init $(1)/etc/init.d/

	$(INSTALL_DIR) $(1)/etc/capabilities
	$(INSTALL_DATA) files/macsec.json $(1)/etc/capabilities
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
