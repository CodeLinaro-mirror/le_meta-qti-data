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
  DEPENDS:= +kmod-qca-nss-sfe +kmod-dataipa
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
	echo "ccflags-y += -I$(STAGING_DIR)/usr/include/qca-nss-sfe" >> $(PKG_BUILD_DIR)/Makefile
	echo "ccflags-y += -I$(STAGING_DIR)/usr/include/dataipa" >> $(PKG_BUILD_DIR)/Makefile
endef

ECM_MAKE_OPTS:=ECM_DB_PER_CLIENT_ROUTED_STATS_ENABLE=y \
	ECM_SDX_STATS_ENABLE=y \
	ECM_CLASSIFIER_PCC_ENABLE=y \
	ECM_INTERFACE_RAWIP_ENABLE=y \
	ECM_TRACKER_DPI_SUPPORT_ENABLE=y \
	ECM_CLASSIFIER_DSCP_ENABLE=y \
	ECM_INTERFACE_VLAN_ENABLE=y \
	ECM_BRIDGE_VLAN_FILTERING_ENABLE=y \
	ECM_BAND_STEERING_ENABLE=y \
	ECM_DB_CTA_TRACK_ENABLE=y \
	ECM_DB_ADVANCED_STATS_ENABLE=y \
	ECM_FRONT_END_CONN_LIMIT_ENABLE=y \
	ECM_STATE_OUTPUT_ENABLE=y \
	ECM_DB_XREF_ENABLE=y \
	ECM_IPV6_ENABLE=y \
	ECM_FRONT_END_SFE_ENABLE=y \
	ECM_SDX_PCC_ENABLED=y \
	ECM_INTERFACE_PPP_ENABLE=n \
	ECM_INTERFACE_PPPOE_ENABLE=n \
	ECM_FRONT_END_IPA_ENABLE=y

MAKE_OPTS := \
	M="$(PKG_BUILD_DIR)" \
	CONFIG_QCA_NSS_ECM=m

define Build/Compile
	echo "Linux Dir: $(LINUX_DIR)"
	if [ "$(BOARD)" = "echo" ]; then \
		$(KERNEL_MAKE) -C "$(LINUX_DIR)" $(ECM_MAKE_OPTS) $(MAKE_OPTS) \
		EXAMPLES_BUILD_SDX="$(CONFIG_QCA_NSS_ECM_EXAMPLES_SDX)" \
		modules; \
	else \
		$(MAKE) -C "$(LINUX_DIR)" $(MAKE_OPTS) modules; \
	fi
endef

define Build/InstallDev
	$(INSTALL_DIR) $(STAGING_DIR)
	$(INSTALL_DIR) $(1)/usr/include/qca-nss-ecm
	mkdir -p  $(1)/usr/lib/
	$(CP) $(PKG_BUILD_DIR)/ecm.ko $(1)/usr/lib/
	if [ "$(BOARD)" = "echo" ]; then \
		$(CP) $(PKG_BUILD_DIR)/examples/ecm_ae_select.ko $(1)/usr/lib/; \
	else \
		$(CP) $(PKG_BUILD_DIR)/ecm_ae_select.ko $(1)/usr/lib/; \
	fi
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
	if [ "$(BOARD)" = "echo" ]; then \
		$(INSTALL_BIN) $(PKG_BUILD_DIR)/examples/ecm_sdx_pcc.ko $(1)/lib/modules/; \
		$(INSTALL_BIN) $(PKG_BUILD_DIR)/examples/ecm_sdx_pcc.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/; \
	else \
		$(INSTALL_BIN) $(PKG_BUILD_DIR)/ecm_sdx_pcc.ko $(1)/lib/modules/; \
		$(INSTALL_BIN) $(PKG_BUILD_DIR)/ecm_sdx_pcc.ko $(TOPDIR)/bin/targets/$(TARGET_VARIANT)/modules/; \
	fi
endef

define KernelPackage/$(PKG_NAME)/config
menu "ECM Configuration"
	config QCA_NSS_ECM_EXAMPLES_SDX
		bool "Build PCC SDX module"
		help
			Selecting this will build the SDX PCC classifier usage module.
		default y
endmenu
endef

$(eval $(call KernelPackage,$(PKG_NAME)))
