include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=rtsp-alg
PKG_RELEASE:=$(AUTORELEASE)
PKG_VERSION:=5.3

PKG_SOURCE_PROTO:=git
PKG_SOURCE_URL:=https://github.com/maru-sama/rtsp-linux.git
PKG_SOURCE_VERSION:=475af0aa62dc30f1b823126eceed4eb32de52e5b

PKG_MAINTAINER:=
PKG_LICENSE:=GPL-3.0+
PKG_LICENSE_FILES:=LICENSE

include $(INCLUDE_DIR)/package.mk

define KernelPackage/$(PKG_NAME)
	SECTION:=Kernel Modules
	CATEGORY:= External Kernel Modules
	TITLE:=rtsp-alg
	URL:=https://github.com/maru-sama/rtsp-linux
endef

define KernelPackage/$(PKG_NAME)/Description
	rtsp-alg
endef

MAKE_OPTS:= \
	M="$(PKG_BUILD_DIR)"

define Build/Compile
	$(MAKE) -C "$(LINUX_DIR)" \
	$(MAKE_OPTS) \
	modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	mkdir -p $(STAGING_DIR)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/nf_nat_rtsp.ko $(STAGING_DIR)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/nf_conntrack_rtsp.ko $(STAGING_DIR)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/rtsp-alg
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/nf_nat_rtsp.ko $(1)/lib/modules/rtsp-alg
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/nf_conntrack_rtsp.ko $(1)/lib/modules/rtsp-alg
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
