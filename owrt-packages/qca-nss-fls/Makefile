include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=qca-nss-fls
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=ISC

include $(INCLUDE_DIR)/package.mk
LOCAL_SRC:=$(TOPDIR)/src/nss-nsm/fls

define KernelPackage/$(PKG_NAME)
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=QCA NSS Flow Statistics (FLS)
  PROVIDES:=kmod-qca-nss-fls
  DEPENDS:=+kmod-qca-nss-sfe +kmod-qca-nss-ecm-premium
endef

define KernelPackage/$(PKG_NAME)/Description
Flow Identification
endef

FLS_MAKE_OPTS:= \
        M="$(PKG_BUILD_DIR)"

ifneq ($(CONFIG_PACKAGE_kmod-emesh-sp),)
FLS_MAKE_OPTS+=FLS_ECM_CLASSIFIER_EMESH_ENABLE=y
endif

EXTRA_CFLAGS+= -I$(STAGING_DIR)/usr/include/qca-nss-sfe
EXTRA_CFLAGS+= -I$(STAGING_DIR)/usr/include/qca-nss-ecm

define Build/Prepare
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/Compile
		echo "Linux Dir: $(LINUX_DIR)"
		$(MAKE) -C "$(LINUX_DIR)" \
		M="$(PKG_BUILD_DIR)" \
		#CONFIG_QCA_NSS_ECM=m \
		modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	$(INSTALL_DIR) $(STAGING_DIR)/usr/include/qca-nss-fls
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/qca-nss-fls.ko $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qca-nss-fls.ko $(1)/lib/modules/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/qca-nss-fls.init $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/qca-nss-fls.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))