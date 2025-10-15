include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=qca-nss-sfe
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=ISC

include $(INCLUDE_DIR)/package.mk

LOCAL_SRC := $(TOPDIR)/src/qca-nss-sfe/
SFE_ADDR := lib/modules/sfe/

define KernelPackage/$(PKG_NAME)
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=QCA NSS Shortcut Forwarding Engine (SFE)
  #DEPENDS:=@kmod-pppoe
  KCONFIG:=CONFIG_NF_CONNTRACK_EVENTS=y
  PROVIDES:=kmod-qca-nss-sfe
  FILES:=$(PKG_BUILD_DIR)/qca-nss-sfe.ko
  #AUTOLOAD:=$(call AutoLoad,09,qca-nss-sfe)
endef

define KernelPackage/$(PKG_NAME)/Description
This package contains the QCA NSS Shortcut Forwarding Engine (SFE).
endef

ifneq (, $(filter $(CONFIG_IPV6),m y))
SFE_MAKE_OPTS:=SFE_SUPPORT_IPV6=y
EXTRA_CFLAGS += -DSFE_SUPPORT_IPV6
endif


#EXTRA_CFLAGS += -DSFE_SUPPORT_PPPOE
#SFE_MAKE_OPTS += SFE_SUPPORT_PPPOE

SFE_MAKE_OPTS+=SFE_PROCESS_LOCAL_OUT=y

ifneq ($(CONFIG_BRIDGE_VLAN_FILTERING),)
SFE_MAKE_OPTS+=SFE_BRIDGE_VLAN_FILTERING_ENABLE=y
endif

EXTRA_CFLAGS+= -I$(PKG_BUILD_DIR)/exports

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS := \
    M="$(PKG_BUILD_DIR)" \
    $(SFE_MAKE_OPTS)

define Build/Compile
    if [ "$(BOARD)" = "sdxecho" ]; then \
        $(KERNEL_MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) EXTRA_CFLAGS="$(EXTRA_CFLAGS)" modules; \
    else \
        $(MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) EXTRA_CFLAGS="$(EXTRA_CFLAGS)" modules; \
    fi
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	$(INSTALL_DIR) $(1)/usr/include/qca-nss-sfe
	$(CP) -rf $(PKG_BUILD_DIR)/exports/sfe_api.h $(1)/usr/include/qca-nss-sfe
	mkdir -p $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/qca-nss-sfe.ko $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qca-nss-sfe.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) ./files/usr/bin/sfe_dump $(1)/usr/bin
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qca-nss-sfe.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))