include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=rmnet-core
PKG_RELEASE:=1

LOCAL_SRC:=$(TOPDIR)/src/datarmnet/core

include $(INCLUDE_DIR)/package.mk

define Build/Prepare
#$(warning "********************Host prepare*********************")
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define KernelPackage/rmnet-core
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=RMNET driver for handling data in multiplexing and aggregation protocol (MAP) format
  DEPENDS:=+kmod-rmnet-ctl
  FILES:=$(PKG_BUILD_DIR)/rmnet_core.ko
  KCONFIG:=RMNET_CORE=y
  AUTOLOAD:=$(call AutoLoad,33,rmnet-core)
endef

define KernelPackage/rmnet-core/Description
RMNET driver handles data in multiplexing and aggregation protocol (MAP) format
endef

define KernelPackage/rmnet-ctl
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=RMNET CTL module for handling QMAP commands for flow control purposes
  FILES:=$(PKG_BUILD_DIR)/rmnet_ctl.ko
  KCONFIG:=RMNET_CTL=y
  AUTOLOAD:=$(call AutoLoad,34,rmnet-ctl)
endef

define KernelPackage/rmnet-ctl/Description
RMNET CTL module handles QMAP commands for flow control purposes
endef
define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
		$(KERNEL_MAKEOPTS) \
		CROSS_COMPILE="$(TARGET_CROSS)" \
		ARCH="$(LINUX_KARCH)" \
		M="$(PKG_BUILD_DIR)" \
		CC="clang" \
		modules
endef

$(eval $(call KernelPackage,rmnet-ctl))
$(eval $(call KernelPackage,rmnet-core))

