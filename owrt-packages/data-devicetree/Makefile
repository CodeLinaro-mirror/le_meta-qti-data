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
	if [ "$(BOARD)" = "echo" ]; then \
		$(MAKE) -C $(PKG_BUILD_DIR) dtbs; \
	fi
endef

define Build/InstallDev
	@true
endef

define KernelPackage/data-devicetree/install
	@echo "LINUX_DIR: $(LINUX_DIR)"
	@echo "PKG_BUILD_DIR: $(PKG_BUILD_DIR)"
	mkdir -p $(LINUX_DIR)/../techpack-dtbos
	mkdir -p $(PKG_BUILD_DIR)/../techpack-dtbos
	$(CP) `find $(PKG_BUILD_DIR)/ -name "*.dtbo"` $(LINUX_DIR)/../techpack-dtbos/
	$(CP) `find $(PKG_BUILD_DIR)/ -name "*.dtbo"` $(PKG_BUILD_DIR)/../techpack-dtbos/
endef

$(eval	$(call KernelPackage,data-devicetree))
