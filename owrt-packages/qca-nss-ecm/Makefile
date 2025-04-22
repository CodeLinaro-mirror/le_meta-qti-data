include $(TOPDIR)/rules.mk
include $(INCLUDE_DIR)/kernel.mk

PKG_NAME:=qca-nss-ecm-premium
PKG_VERSION:=1.0
PKG_RELEASE:=1
PKG_LICENSE:=GPL-2.0-or-later

include $(INCLUDE_DIR)/package.mk

LOCAL_SRC := $(TOPDIR)/src/qca-nss-ecm/
ECM_ADDR := lib/modules/ecm/

define KernelPackage/$(PKG_NAME)
  SECTION:=kernel
  CATEGORY:=Kernel modules
  SUBMENU:=Network Support
  TITLE:=QCA NSS Enhanced Connection Manager (ECM)
  KCONFIG:=CONFIG_BRIDGE_NETFILTER=y \
	CONFIG_NF_CONNTRACK_EVENTS=y \
	CONFIG_NF_CONNTRACK_CHAIN_EVENTS=n \
	CONFIG_NF_CONNTRACK_DSCPREMARK_EXT=y
  PROVIDES:=kmod-qca-nss-ecm-premium
  DEPENDS:= +kmod-qca-nss-sfe
endef

define KernelPackage/$(PKG_NAME)/Description
This package contains the QCA NSS Enhanced Connection Manager with the premium features enabled
endef

EXTRA_CFLAGS += -DCONFIG_FUNCTION_ALIGNMENT=16 \
				-DCONFIG_CLANG_VERSION=180000 \
				-DCONFIG_KERNEL_ATOMIC64=y

define Build/Prepare
#$(warning "********************Prepare$(M) *********************")
	$(warning build_dir $(PKG_BUILD_DIR))
	mkdir -p $(PKG_BUILD_DIR)/
	$(CP) $(LOCAL_SRC)/* $(PKG_BUILD_DIR)/
endef

define Build/Compile
		echo "Linux Dir: $(LINUX_DIR)"
		$(MAKE) -C "$(LINUX_DIR)" \
		M="$(PKG_BUILD_DIR)" \
		CONFIG_QCA_NSS_ECM=m \
		modules
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	$(INSTALL_DIR) $(1)/usr/include/qca-nss-ecm
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/ecm.ko $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/ecm_ae_select.ko $(1)/usr/lib/
endef

define KernelPackage/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/lib/modules/
	$(INSTALL_DIR) $(1)/etc/firewall.d $(1)/etc/init.d $(1)/usr/bin $(1)/lib/netifd/offload $(1)/etc/config $(1)/etc/uci-defaults $(1)/etc/sysctl.d
	$(INSTALL_DATA) ./files/qca-nss-ecm.firewall $(1)/etc/firewall.d/qca-nss-ecm
	$(INSTALL_BIN) ./files/qca-nss-ecm.init $(1)/etc/init.d/qca-nss-ecm
	$(INSTALL_BIN) ./files/ecm_dump.sh $(1)/usr/bin/
	$(INSTALL_BIN) ./files/on-demand-down $(1)/lib/netifd/offload/on-demand-down
	$(INSTALL_DATA) ./files/qca-nss-ecm.uci $(1)/etc/config/ecm
	$(INSTALL_DATA) ./files/qca-nss-ecm.defaults $(1)/etc/uci-defaults/99-qca-nss-ecm
	$(INSTALL_BIN) ./files/qca-nss-ecm.sysctl $(1)/etc/sysctl.d/qca-nss-ecm.conf
	$(INSTALL_DIR) $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ecm.ko $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ecm.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ecm_sdx_pcc.ko $(1)/lib/modules/
	$(INSTALL_BIN) $(PKG_BUILD_DIR)/ecm_sdx_pcc.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
