include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=datarmnet
PKG_RELEASE:=1
LOCAL_SRC:=$(TOPDIR)/src/datarmnet/core
PKG_LICENSE:=BSD-3-Clause

include $(INCLUDE_DIR)/package.mk

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/../* $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define KernelPackage/$(PKG_NAME)
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=RMNET driver for handling data in multiplexing and aggregation protocol (MAP) format
  DEPENDS:=kmod-dataipa
endef

define KernelPackage/$(PKG_NAME)/Description
RMNET driver handles data in multiplexing and aggregation protocol (MAP) format
endef

MAKE_OPTS:= \
        M="$(PKG_BUILD_DIR)"

define Build/Compile
	if [ "$(BOARD)" = "sdxecho" ]; then \
		$(KERNEL_MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
	else \
		$(MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
	fi
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/
#       $(SIGN_KEY) $(PKG_BUILD_DIR)/rmnet_core.ko
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rmnet_core.ko $(1)/lib/modules/
#	$(SIGN_KEY) $(PKG_BUILD_DIR)/rmnet_eth.ko
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rmnet_eth.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rmnet_core.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/rmnet_eth.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/$(PKG_NAME).init $(1)/etc/init.d
endef

$(eval $(call KernelPackage,$(PKG_NAME)))

