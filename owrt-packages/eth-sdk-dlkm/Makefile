include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=eth-sdk-dlkm
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=GPL-2.0-only

ifeq ($(VARIANT),perf)
  COMPILE_MODE :=perf
else
  COMPILE_MODE :=debug
endif

DISTRO := owrt

LOCAL_SRC := $(TOPDIR)/src/data-eth/drivers
LOCAL_FILES_SRC := $(TOPDIR)/src/data-eth/files/$(BOARD)-$(DISTRO)
LOCAL_CLI_SRC := $(TOPDIR)/src/data-eth/cli
ETH_ADDR :=lib/modules/ethernet

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
 SECTION:=eth-sdk-dlkm
 CATEGORY:=Kernel modules
 SUBMENU:=Network Devices
 TITLE:= ETH SDK DLKM Driver
 DEPENDS:= +kmod-dataipa +bash
endef

define KernelPackage/$(PKG_NAME)/Description
	ETH SDK DLKM Driver
endef

TARGET_CFLAGS += -I$(TOOLCHAIN_DIR)/include/linux/ethtool.h

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) -H $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_FILES_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS := \
	M="$(PKG_BUILD_DIR)" BOARD="$(BOARD)"

define Build/Compile
  if [ "$(BOARD)" = "echo" ]; then \
      $(KERNEL_MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS)  modules; \
  fi
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(1)/$(ETH_ADDR)/
	mkdir -p  $(1)/usr/lib/
	find "$(PKG_BUILD_DIR)" -type f -name '*.ko' -exec $(CP) {} "$(1)/usr/lib/" \;
endef

define KernelPackage/$(PKG_NAME)/install
	find $(PKG_BUILD_DIR) -type f -name "*.ko" -exec $(SIGN_KEY) {} \;
	$(INSTALL_DIR) $(1)/$(ETH_ADDR)/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	find "$(PKG_BUILD_DIR)" -type f -name '*.ko' -exec $(INSTALL_BIN) {} "$(1)/$(ETH_ADDR)/" \;
	find "$(PKG_BUILD_DIR)" -type f -name '*.ko' -exec $(INSTALL_BIN) {} "$(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/" \;
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/initscripts/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/eth-sdk-dlkm.init $(1)/etc/init.d
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/eth-sdk-dlkm  $(1)/etc/initscripts/
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(LOCAL_CLI_SRC)/eth-cfg $(1)/usr/sbin/eth-cfg
	$(INSTALL_DIR) $(1)/etc/data/eth
	$(INSTALL_BIN) $(LOCAL_CLI_SRC)/set_eth_perf $(1)/etc/data/eth/set_eth_perf
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
