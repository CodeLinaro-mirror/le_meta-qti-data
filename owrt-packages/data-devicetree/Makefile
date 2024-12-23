include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=data-devicetree
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=BSD-3-Clause
PKG_FIXUP:=autoreconf

LOCAL_SRC ?= $(TOPDIR)/src/vendor/qcom/opensource/data-devicetree

include $(INCLUDE_DIR)/package.mk

define KernelPackage/data-devicetree
  SECTION:=Kernel Modules
  CATEGORY:=External Kernel Modules
  TITLE:=DATA-DEVICETREE
endef

define KernelPackage/data-devicetree/Description
 This package contains devicetree for Data.
endef

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

MAKE_OPTS:= \
	M="$(PKG_BUILD_DIR)" \
	TARGET_SUPPORT="$(KERNEL_PLATFORM_TARGET)"\

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	$(MAKE_OPTS) \
	modules
endef

define Build/InstallDev
	@true
endef

define KernelPackage/data-devicetree/install
	mkdir -p $(LINUX_DIR)/../techpack-dtbos
	$(CP) $(PKG_BUILD_DIR)/ipa/*.dtbo $(LINUX_DIR)/../techpack-dtbos/
endef

$(eval	$(call KernelPackage,data-devicetree))
