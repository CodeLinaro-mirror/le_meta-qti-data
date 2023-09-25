include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/target.mk

PKG_NAME:=thermal-eth-netlink
PKG_VERSION:=1
PKG_RELEASE:=1

LOCAL_SRC:=$(TOPDIR)/src/data-eth/drivers/thermal-eth-netlink

include $(INCLUDE_DIR)/package.mk

define Package/$(PKG_NAME)
 SECTION:=data-eth
 CATEGORY:=data-eth
 TITLE:=thermal-eth-netlink
 USERID:=thermal=1002:thermal=1002 thermal=1002:kmsg=2902
# DEPENDS:=+dsutils +diag +libqcmaputils +tlpd_ext +musl-fts +libnl3 +linux-msm-headers
 DEPENDS:=+dsutils +diag +libutils +qmi-framework +libcutils +libnl
endef

define Package/thermal-eth-netlink/Description
	data-eth/drivers/thermal-eth-netlink
endef

TARGET_CFLAGS += -Wall -Werror -Wextra \
	-fpic \
	-D__packed__= \
	-DIMAGE_APPS_PROC \
	-DFEATURE_DATA_LINUX_LE \
	-DFEATURE_DSUTILS_OFFTARGET \
	-DFEATURE_DATA_PREFIX_DELEGATION \
	-DRADISH_OFFLINE \
	-DRADISH_LOG_QXDM \
	-DFEATURE_DATA_LOG_QXDM \
	$(DIAG_CFLAGS) \
	$(DSUTILS_CFLAGS) \
        $(DATA_CFLAGS) \
        $(DATA_LIBS) \
	-I${STAGING_DIR}/linux-msm/usr/include \
	-I${STAGING_DIR}/usr/include/libnl3 \
	-I$(STAGING_DIR)/usr/include/dsutils \
	-I$(STAGING_DIR)/usr/include/diag

TARGET_LDFLAGS += \
	-L$(STAGING_DIR)/usr/lib \
	-ldsutils \
	-ldiag

define Build/Prepare
#$(warning "********************Host prepare *********************")
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
	$(Build/Patch)
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(1)/usr/include/thermal-eth-netlink
	mkdir -p $(1)/usr/lib
endef

define Package/thermal-eth-netlink/install
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/bin/thermal-eth-netlink $(1)/usr/bin
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/thermal-eth-netlink.init $(1)/etc/init.d
endef
$(eval $(call BuildPackage,thermal-eth-netlink))
