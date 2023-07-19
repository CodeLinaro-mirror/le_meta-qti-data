#
# Copyright (C) 2012-2018 OpenWrt.org
#
# This is free software, licensed under the GNU General Public License v2.
# See /LICENSE for more information.
#

include $(TOPDIR)/rules.mk

PKG_NAME:=newstrongswan
PKG_VERSION:=5.9.10
PKG_RELEASE:=1

PKG_SOURCE:=strongswan-$(PKG_VERSION).tar.bz2
PKG_SOURCE_URL:= https://sources.openwrt.org/
#PKG_HASH:=61c72f741edb2c1295a7b7ccce0317a104b3f9d39efd04c52cd05b01b55ab063
PKG_HASH:=3b72789e243c9fa6f0a01ccaf4f83766eba96a5e5b1e071d36e997572cf34654
PKG_LICENSE:=GPL-2.0-or-later
PKG_MAINTAINER:=Philip Prindeville <philipp@redfish-solutions.com>, Noel Kuntze <noel.kuntze@thermi.consulting>
PKG_CPE_ID:=cpe:/a:strongswan:strongswan

PKG_MOD_AVAILABLE:= \
	addrblock \
	aes \
	af-alg \
	agent \
	attr \
	attr-sql \
	blowfish \
	ccm \
	cmac \
	constraints \
	connmark \
	coupling \
	ctr \
	curl \
	curve25519 \
	des \
	dhcp \
	dnskey \
	duplicheck \
	eap-identity \
	eap-md5 \
	eap-mschapv2 \
	eap-radius \
	eap-tls \
	farp \
	fips-prf \
	forecast \
	gcm \
	gcrypt \
	gmp \
	gmpdh \
	ha \
	hmac \
	kernel-libipsec \
	kernel-netlink \
	ldap \
	led \
	load-tester \
	nonce \
	md4 \
	md5 \
	mysql \
	openssl \
	pem \
	pgp \
	pkcs1 \
	pkcs7 \
	pkcs8 \
	pkcs11 \
	pkcs12 \
	pubkey \
	random \
	rc2 \
	resolve \
	revocation \
	sha1 \
	sha2 \
	smp \
	socket-default \
	socket-dynamic \
	sql \
	sqlite \
	sshkey \
	stroke \
	test-vectors \
	unity \
	uci \
	updown \
	vici \
	whitelist \
	x509 \
	xauth-eap \
	xauth-generic \
	xcbc

PKG_CONFIG_DEPENDS:= \
	CONFIG_NEWSTRONGSWAN_ROUTING_TABLE \
	CONFIG_NEWSTRONGSWAN_ROUTING_TABLE_PRIO \
	$(patsubst %,CONFIG_PACKAGE_newstrongswan-mod-%,$(PKG_MOD_AVAILABLE)) \

PKG_FIXUP:=autoreconf
PKG_INSTALL:=1
PKG_BUILD_PARALLEL:=1

include $(INCLUDE_DIR)/package.mk
# newstrongswan-mod-mysql needs iconv
include $(INCLUDE_DIR)/nls.mk

define Package/newstrongswan/Default
  SECTION:=Data
  CATEGORY:=QTI-PACKAGES
  TITLE:=newstrongswan
  URL:=http://www.strongswan.org/
endef

define Package/newstrongswan/description/Default
 StrongSwan is an OpenSource IPsec implementation for the Linux operating system.
endef

define Package/newstrongswan
$(call Package/newstrongswan/Default)
  MENU:=1
  DEPENDS:= +libpthread +ip \
	+kmod-crypto-authenc \
	+kmod-ipsec +kmod-ipsec4 +IPV6:kmod-ipsec6 \
	+kmod-ipt-ipsec +iptables-mod-ipsec \
	+libexecinfo \
	+libip4tc
endef

define Package/newstrongswan/config
  source "$(SOURCE)/Config.in"
endef

define Package/newstrongswan/description
$(call Package/newstrongswan/description/Default)
 This package contains shared libraries and scripts.
endef

define Package/newstrongswan-full
$(call Package/newstrongswan/Default)
  TITLE+= (full)
  DEPENDS:= newstrongswan \
	+newstrongswan-charon \
	+newstrongswan-charon-cmd \
	+newstrongswan-ipsec \
	+newstrongswan-mod-addrblock \
	+newstrongswan-mod-aes \
	+newstrongswan-mod-af-alg \
	+newstrongswan-mod-agent \
	+newstrongswan-mod-attr \
	+newstrongswan-mod-attr-sql \
	+newstrongswan-mod-blowfish \
	+newstrongswan-mod-ccm \
	+newstrongswan-mod-cmac \
	+newstrongswan-mod-constraints \
	+newstrongswan-mod-connmark \
	+newstrongswan-mod-coupling \
	+newstrongswan-mod-ctr \
	+newstrongswan-mod-curl \
	+newstrongswan-mod-curve25519 \
	+newstrongswan-mod-des \
	+newstrongswan-mod-dhcp \
	+newstrongswan-mod-dnskey \
	+newstrongswan-mod-duplicheck \
	+newstrongswan-mod-eap-identity \
	+newstrongswan-mod-eap-md5 \
	+newstrongswan-mod-eap-mschapv2 \
	+newstrongswan-mod-eap-radius \
	+newstrongswan-mod-eap-tls \
	+newstrongswan-mod-farp \
	+newstrongswan-mod-fips-prf \
	+newstrongswan-mod-forecast \
	+newstrongswan-mod-gcm \
	+newstrongswan-mod-gcrypt \
	+newstrongswan-mod-gmp \
	+newstrongswan-mod-ha \
	+newstrongswan-mod-hmac \
	+newstrongswan-mod-kernel-netlink \
	+newstrongswan-mod-ldap \
	+newstrongswan-mod-led \
	+newstrongswan-mod-load-tester \
	+newstrongswan-mod-nonce \
	+newstrongswan-mod-md4 \
	+newstrongswan-mod-md5 \
	+newstrongswan-mod-mysql \
	+newstrongswan-mod-openssl \
	+newstrongswan-mod-pem \
	+newstrongswan-mod-pgp \
	+newstrongswan-mod-pkcs1 \
	+newstrongswan-mod-pkcs7 \
	+newstrongswan-mod-pkcs8 \
	+newstrongswan-mod-pkcs11 \
	+newstrongswan-mod-pkcs12 \
	+newstrongswan-mod-pubkey \
	+newstrongswan-mod-random \
	+newstrongswan-mod-rc2 \
	+newstrongswan-mod-resolve \
	+newstrongswan-mod-revocation \
	+newstrongswan-mod-sha1 \
	+newstrongswan-mod-sha2 \
	+newstrongswan-mod-smp \
	+newstrongswan-mod-socket-default \
	+newstrongswan-mod-sql \
	+newstrongswan-mod-sqlite \
	+newstrongswan-mod-sshkey \
	+newstrongswan-mod-stroke \
	+newstrongswan-mod-test-vectors \
	+newstrongswan-mod-uci \
	+newstrongswan-mod-unity \
	+newstrongswan-mod-updown \
	+newstrongswan-mod-vici \
	+newstrongswan-mod-whitelist \
	+newstrongswan-mod-x509 \
	+newstrongswan-mod-xauth-eap \
	+newstrongswan-mod-xauth-generic \
	+newstrongswan-mod-xcbc \
	+newstrongswan-pki \
	+newstrongswan-swanctl \
	@DEVEL
endef

define Package/newstrongswan-full/description
$(call Package/newstrongswan/description/Default)
 This meta-package contains dependencies for all of the strongswan plugins
 except kernel-libipsec,
 socket-dynamic and which are omitted in favor of the kernel-netlink and
 socket-default plugins.
endef


define Package/newstrongswan-default
$(call Package/newstrongswan/Default)
  TITLE+= (default)
  DEPENDS:= newstrongswan \
	+newstrongswan-charon \
	+newstrongswan-ipsec \
	+newstrongswan-mod-aes \
	+newstrongswan-mod-attr \
	+newstrongswan-mod-connmark \
	+newstrongswan-mod-constraints \
	+newstrongswan-mod-des \
	+newstrongswan-mod-dnskey \
	+newstrongswan-mod-fips-prf \
	+newstrongswan-mod-gmp \
	+newstrongswan-mod-hmac \
	+newstrongswan-mod-kernel-netlink \
	+newstrongswan-mod-md5 \
	+newstrongswan-mod-nonce \
	+newstrongswan-mod-pem \
	+newstrongswan-mod-pgp \
	+newstrongswan-mod-pkcs1 \
	+newstrongswan-mod-pubkey \
	+newstrongswan-mod-random \
	+newstrongswan-mod-rc2 \
	+newstrongswan-mod-resolve \
	+newstrongswan-mod-revocation \
	+newstrongswan-mod-sha1 \
	+newstrongswan-mod-sha2 \
	+newstrongswan-mod-socket-default \
	+newstrongswan-mod-sshkey \
	+newstrongswan-mod-stroke \
	+newstrongswan-mod-updown \
	+newstrongswan-mod-x509 \
	+newstrongswan-mod-xauth-generic \
	+newstrongswan-mod-xcbc
endef

define Package/newstrongswan-default/description
$(call Package/newstrongswan/description/Default)
 This meta-package contains only dependencies to match upstream defaults.
endef


define Package/newstrongswan-isakmp
$(call Package/newstrongswan/Default)
  TITLE+= (isakmp)
  DEPENDS:= newstrongswan \
	+newstrongswan-charon \
	+newstrongswan-ipsec \
	+newstrongswan-mod-aes \
	+newstrongswan-mod-des \
	+newstrongswan-mod-gmpdh \
	+newstrongswan-mod-hmac \
	+newstrongswan-mod-kernel-netlink \
	+newstrongswan-mod-md5 \
	+newstrongswan-mod-nonce \
	+newstrongswan-mod-pubkey \
	+newstrongswan-mod-random \
	+newstrongswan-mod-sha1 \
	+newstrongswan-mod-socket-default \
	+newstrongswan-mod-stroke \
	+newstrongswan-mod-uci \
	+newstrongswan-mod-updown
endef

define Package/newstrongswan-isakmp/description
$(call Package/newstrongswan/description/Default)
 This meta-package contains only dependencies to establish  ISAKMP /
 IKE PSK connections, dropping other capabilities in favor of small size 
 Can fit most routers even with 4Mb flash (after removing IPv6 support).
endef


define Package/newstrongswan-minimal
$(call Package/newstrongswan/Default)
  TITLE+= (minimal)
  DEPENDS:= newstrongswan \
	+newstrongswan-charon \
	+newstrongswan-mod-aes \
	+newstrongswan-mod-gmp \
	+newstrongswan-mod-hmac \
	+newstrongswan-mod-kernel-netlink \
	+newstrongswan-mod-nonce \
	+newstrongswan-mod-pubkey \
	+newstrongswan-mod-random \
	+newstrongswan-mod-sha1 \
	+newstrongswan-mod-socket-default \
	+newstrongswan-mod-stroke \
	+newstrongswan-mod-updown \
	+newstrongswan-mod-x509 \
	+newstrongswan-mod-xcbc
endef

define Package/newstrongswan-minimal/description
$(call Package/newstrongswan/description/Default)
 This meta-package contains only dependencies for a minimal IKEv2 setup.
endef

define Package/newstrongswan-charon
$(call Package/newstrongswan/Default)
  TITLE+= IKEv1/IKEv2 keying daemon
  DEPENDS:= newstrongswan
endef

define Package/newstrongswan-charon/description
$(call Package/newstrongswan/description/Default)
 This package contains charon, an IKEv2 keying daemon.
endef

define Package/newstrongswan-charon-cmd
$(call Package/newstrongswan/Default)
  TITLE+= charon-cmd utility
  DEPENDS:= newstrongswan +newstrongswan-charon
endef

define Package/newstrongswan-charon-cmd/description
$(call Package/newstrongswan/description/Default)
 This package contains the charon-cmd utility.
endef

define Package/newstrongswan-ipsec
$(call Package/newstrongswan/Default)
  TITLE+= utilities
  DEPENDS:= newstrongswan
endef

define Package/newstrongswan-ipsec/description
$(call Package/newstrongswan/description/Default)
 This package contains the ipsec utility.
endef

define Package/newstrongswan-pki
$(call Package/newstrongswan/Default)
  TITLE+= PKI tool
  DEPENDS:= newstrongswan +newstrongswan-libtls
endef

define Package/newstrongswan-pki/description
$(call Package/newstrongswan/description/Default)
 This package contains the pki tool.
endef

define Package/newstrongswan-scepclient
$(call Package/newstrongswan/Default)
  TITLE+= SCEP client
  DEPENDS:= newstrongswan
endef

define Package/newstrongswan-scepclient/description
$(call Package/newstrongswan/description/Default)
 This package contains the SCEP client.
endef

define Package/newstrongswan-swanctl
$(call Package/newstrongswan/Default)
  TITLE+= swanctl utility
  DEPENDS:= newstrongswan +newstrongswan-mod-vici
endef

define Package/newstrongswan-swanctl/description
$(call Package/newstrongswan/description/Default)
 This package contains the swanctl utility.
endef

define Package/newstrongswan-libtls
$(call Package/newstrongswan/Default)
  TITLE+= libtls
  DEPENDS:= newstrongswan
endef

define Package/newstrongswan-libtls/description
$(call Package/newstrongswan/description/Default)
 This package contains libtls for strongSwan plugins eap-tls, eap-ttls,
 eap-peap, tnc-tnccs
endef

define Build/Prepare
	tar xvf $(TOPDIR)/dl/$(PKG_SOURCE) --strip-components 1 -C $(PKG_BUILD_DIR)
	$(Build/Patch)
endef

define BuildPlugin
  define Package/newstrongswan-mod-$(1)
    $$(call Package/newstrongswan/Default)
    TITLE:= StrongSwan $(2) plugin
    DEPENDS:= newstrongswan $(3)
  endef

  define Package/newstrongswan-mod-$(1)/install
	$(INSTALL_DIR) $$(1)/etc/strongswan.d/charon
	if [ -f $(PKG_INSTALL_DIR)/etc/strongswan.d/charon/$(1).conf ]; then \
		$(INSTALL_DATA) $(PKG_INSTALL_DIR)/etc/strongswan.d/charon/$(1).conf $$(1)/etc/strongswan.d/charon/; fi
	$(INSTALL_DIR) $$(1)/usr/lib/ipsec/plugins
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-$(1).so \
		$$(1)/usr/lib/ipsec/plugins/
	$(call Plugin/$(1)/install,$$(1))
  endef

  $$(eval $$(call BuildPackage,newstrongswan-mod-$(1)))
endef

CONFIGURE_ARGS+= \
	--disable-scripts \
	--disable-static \
	--disable-fast \
	--enable-mediation \
	--with-systemdsystemunitdir=no \
	$(if $(CONFIG_PACKAGE_newstrongswan-charon-cmd),--enable-cmd,--disable-cmd) \
	$(if $(CONFIG_PACKAGE_newstrongswan-pki),--enable-pki,--disable-pki) \
	--with-random-device=/dev/random \
	--with-urandom-device=/dev/urandom \
	--with-routing-table="$(call qstrip,$(CONFIG_NEWSTRONGSWAN_ROUTING_TABLE))" \
	--with-routing-table-prio="$(call qstrip,$(CONFIG_NEWSTRONGSWAN_ROUTING_TABLE_PRIO))" \
	$(foreach m,$(PKG_MOD_AVAILABLE), \
	  $(if $(CONFIG_PACKAGE_newstrongswan-mod-$(m)),--enable-$(m),--disable-$(m)) \
	) \
	ac_cv_search___atomic_load=no

EXTRA_LDFLAGS+= -Wl,-rpath-link,$(STAGING_DIR)/usr/lib \
	-lexecinfo

define Package/newstrongswan/conffiles
/etc/strongswan.conf
/etc/strongswan.d/
endef

define Package/newstrongswan/install
	$(INSTALL_DIR) $(1)/etc
	$(INSTALL_CONF) $(PKG_INSTALL_DIR)/etc/strongswan.conf $(1)/etc/
	echo -e "\ninclude /var/ipsec/strongswan.conf" >> $(1)/etc/strongswan.conf
	$(INSTALL_DIR) $(1)/usr/lib/ipsec
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/libstrongswan.so.* $(1)/usr/lib/ipsec/
endef

define Package/newstrongswan-default/install
	true
endef

define Package/newstrongswan-full/install
	true
endef

define Package/newstrongswan-isakmp/install
	true
endef

define Package/newstrongswan-minimal/install
	true
endef

define Package/newstrongswan-charon/install
	$(INSTALL_DIR) $(1)/etc/strongswan.d
	$(CP) $(PKG_INSTALL_DIR)/etc/strongswan.d/charon.conf $(1)/etc/strongswan.d
	$(CP) $(PKG_INSTALL_DIR)/etc/strongswan.d/charon-logging.conf $(1)/etc/strongswan.d
	$(INSTALL_DIR) $(1)/usr/lib/ipsec
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/lib/ipsec/charon $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/libcharon.so.* $(1)/usr/lib/ipsec/
endef

define Package/newstrongswan-charon-cmd/install
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/sbin/charon-cmd $(1)/usr/sbin/
endef

define Package/newstrongswan-ipsec/conffiles
/etc/ipsec.d/
/etc/ipsec.conf
/etc/ipsec.secrets
/etc/ipsec.user
endef

define Package/newstrongswan-ipsec/install
	$(INSTALL_DIR) $(1)/etc/ $(1)/usr/sbin
	$(INSTALL_CONF) $(PKG_INSTALL_DIR)/etc/ipsec.conf $(1)/etc/
	echo -e "\ninclude /var/ipsec/ipsec.conf" >> $(1)/etc/ipsec.conf
	$(INSTALL_CONF) ./files/ipsec.secrets $(1)/etc/
	echo -e "\ninclude /var/ipsec/ipsec.secrets" >> $(1)/etc/ipsec.secrets
	$(INSTALL_CONF) ./files/ipsec.user $(1)/etc/
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_BIN) ./files/ipsec.init $(1)/etc/init.d/ipsec
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/sbin/ipsec $(1)/usr/sbin/
endef

define Package/newstrongswan-ipsec/postinst
#!/bin/sh

[ -z "$${IPKG_INSTROOT}" ] || exit 0

opkg list-changed-conffiles | grep -qx /etc/ipsec.conf || {
	rm -f /etc/ipsec.conf-opkg
}
endef

define Package/newstrongswan-pki/install
	$(INSTALL_DIR) $(1)/etc/strongswan.d
	$(CP) $(PKG_INSTALL_DIR)/etc/strongswan.d/pki.conf $(1)/etc/strongswan.d/
	$(INSTALL_DIR) $(1)/usr/bin
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/bin/pki $(1)/usr/bin/
endef

define Package/newstrongswan-scepclient/install
	$(INSTALL_DIR) $(1)/etc/strongswan.d
	$(CP) $(PKG_INSTALL_DIR)/etc/strongswan.d/scepclient.conf $(1)/etc/strongswan.d/
	$(INSTALL_DIR) $(1)/usr/lib/ipsec
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/lib/ipsec/scepclient $(1)/usr/lib/ipsec/
endef

define Package/newstrongswan-swanctl/conffiles
/etc/swanctl/
endef

define Package/newstrongswan-swanctl/install
	$(INSTALL_DIR) $(1)/etc/init.d
	$(INSTALL_DIR) $(1)/etc/swanctl/{bliss,conf.d,ecdsa,pkcs{12,8},private,pubkey,rsa}
	$(INSTALL_DIR) $(1)/etc/swanctl/x509{,aa,ac,ca,crl,ocsp}
	$(CP) $(PKG_INSTALL_DIR)/etc/swanctl/swanctl.conf $(1)/etc/swanctl/
	echo "include /var/swanctl/swanctl.conf" >> $(1)/etc/swanctl/swanctl.conf
	$(INSTALL_DIR) $(1)/usr/sbin
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/sbin/swanctl $(1)/usr/sbin/
	$(INSTALL_BIN) ./files/swanctl.init $(1)/etc/init.d/swanctl
endef

define Package/newstrongswan-libtls/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/libtls.so.* $(1)/usr/lib/ipsec/
endef

define Plugin/duplicheck/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec/plugins
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/duplicheck $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-duplicheck.so $(1)/usr/lib/ipsec/plugins/
endef

define Plugin/eap-radius/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec/plugins
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/libradius.so.* $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-eap-radius.so $(1)/usr/lib/ipsec/plugins/
endef

define Plugin/attr-sql/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/pool $(1)/usr/lib/ipsec/
endef

define Plugin/stroke/install
	$(INSTALL_DIR) $(1)/etc/ipsec.d/aacerts
	$(INSTALL_DIR) $(1)/etc/ipsec.d/acerts
	$(INSTALL_DIR) $(1)/etc/ipsec.d/cacerts
	$(INSTALL_DIR) $(1)/etc/ipsec.d/certs
	$(INSTALL_DIR) $(1)/etc/ipsec.d/crls
	$(INSTALL_DIR) $(1)/etc/ipsec.d/ocspcerts
	$(INSTALL_DIR) $(1)/etc/ipsec.d/private
	$(INSTALL_DIR) $(1)/etc/ipsec.d/reqs

	$(INSTALL_DIR) $(1)/usr/lib/ipsec/plugins
	$(INSTALL_BIN) $(PKG_INSTALL_DIR)/usr/lib/ipsec/{starter,stroke} $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-stroke.so $(1)/usr/lib/ipsec/plugins/
endef

define Plugin/updown/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec/plugins
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/_updown $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-updown.so $(1)/usr/lib/ipsec/plugins/
	$(INSTALL_DIR) $(1)/etc/hotplug.d/ipsec
	$(CP) ./files/etc/hotplug.d/ipsec/01-user \
		$(1)/etc/hotplug.d/ipsec/01-user
endef

define Plugin/vici/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec/plugins
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/libvici.so.* $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-vici.so $(1)/usr/lib/ipsec/plugins/
endef

define Plugin/whitelist/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec/plugins
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/whitelist $(1)/usr/lib/ipsec/
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/plugins/libstrongswan-whitelist.so $(1)/usr/lib/ipsec/plugins/
endef

define Plugin/kernel-libipsec/install
	$(INSTALL_DIR) $(1)/usr/lib/ipsec
	$(CP) $(PKG_INSTALL_DIR)/usr/lib/ipsec/libipsec.so.* $(1)/usr/lib/ipsec/
endef

$(eval $(call BuildPackage,newstrongswan))
$(eval $(call BuildPackage,newstrongswan-default))
$(eval $(call BuildPackage,newstrongswan-full))
$(eval $(call BuildPackage,newstrongswan-minimal))
$(eval $(call BuildPackage,newstrongswan-isakmp))
$(eval $(call BuildPackage,newstrongswan-charon))
$(eval $(call BuildPackage,newstrongswan-charon-cmd))
$(eval $(call BuildPackage,newstrongswan-ipsec))
$(eval $(call BuildPackage,newstrongswan-pki))
# $(eval $(call BuildPackage,newstrongswan-scepclient))
$(eval $(call BuildPackage,newstrongswan-swanctl))
$(eval $(call BuildPackage,newstrongswan-libtls))
$(eval $(call BuildPlugin,addrblock,RFC 3779 address block constraint support,))
$(eval $(call BuildPlugin,aes,AES crypto,))
$(eval $(call BuildPlugin,af-alg,AF_ALG crypto interface to Linux Crypto API,+kmod-crypto-user))
$(eval $(call BuildPlugin,agent,SSH agent signing,))
$(eval $(call BuildPlugin,attr,file based config,))
$(eval $(call BuildPlugin,attr-sql,SQL based config,+newstrongswan-charon))
$(eval $(call BuildPlugin,blowfish,Blowfish crypto,))
$(eval $(call BuildPlugin,ccm,CCM AEAD wrapper crypto,))
$(eval $(call BuildPlugin,cmac,CMAC crypto,))
$(eval $(call BuildPlugin,connmark,netfilter connection marking,))
$(eval $(call BuildPlugin,constraints,advanced X509 constraint checking,))
$(eval $(call BuildPlugin,coupling,IKEv2 plugin to couple peer certificates permanently to authentication,))
$(eval $(call BuildPlugin,ctr,Counter Mode wrapper crypto,))
$(eval $(call BuildPlugin,curl,cURL fetcher plugin,+PACKAGE_newstrongswan-mod-curl:libcurl))
$(eval $(call BuildPlugin,curve25519,Curve25519 Diffie-Hellman,))
$(eval $(call BuildPlugin,des,DES crypto,))
$(eval $(call BuildPlugin,dhcp,DHCP based attribute provider,))
$(eval $(call BuildPlugin,dnskey,DNS RR key decoding,))
$(eval $(call BuildPlugin,duplicheck,advanced duplicate checking,))
$(eval $(call BuildPlugin,eap-identity,EAP identity helper,))
$(eval $(call BuildPlugin,eap-md5,EAP MD5 (CHAP) EAP auth,))
$(eval $(call BuildPlugin,eap-mschapv2,EAP MS-CHAPv2 EAP auth,+newstrongswan-mod-md4 +newstrongswan-mod-des))
$(eval $(call BuildPlugin,eap-radius,EAP RADIUS auth,))
$(eval $(call BuildPlugin,eap-tls,EAP TLS auth,+newstrongswan-libtls))
$(eval $(call BuildPlugin,farp,fake arp respsonses,))
$(eval $(call BuildPlugin,fips-prf,FIPS PRF crypto,+newstrongswan-mod-sha1))
$(eval $(call BuildPlugin,forecast,forward multi/broadcast traffic,+kmod-ipt-conntrack-extra))
$(eval $(call BuildPlugin,gcm,GCM AEAD wrapper crypto,))
$(eval $(call BuildPlugin,gcrypt,libgcrypt,+PACKAGE_newstrongswan-mod-gcrypt:libgcrypt))
$(eval $(call BuildPlugin,gmp,libgmp,+PACKAGE_newstrongswan-mod-gmp:libgmp))
$(eval $(call BuildPlugin,gmpdh,DH-Groups; no libgmp dep,))
$(eval $(call BuildPlugin,ha,high availability cluster,))
$(eval $(call BuildPlugin,hmac,HMAC crypto,))
$(eval $(call BuildPlugin,kernel-libipsec,libipsec kernel interface,))
$(eval $(call BuildPlugin,kernel-netlink,netlink kernel interface,))
$(eval $(call BuildPlugin,ldap,LDAP,+PACKAGE_newstrongswan-mod-ldap:libopenldap))
$(eval $(call BuildPlugin,led,LED blink on IKE activity,))
$(eval $(call BuildPlugin,load-tester,load testing,))
$(eval $(call BuildPlugin,nonce,nonce genereation,))
$(eval $(call BuildPlugin,md4,MD4 crypto,))
$(eval $(call BuildPlugin,md5,MD5 crypto,))
$(eval $(call BuildPlugin,mysql,MySQL database interface,+newstrongswan-mod-sql +PACKAGE_newstrongswan-mod-mysql:libmysqlclient-r))
$(eval $(call BuildPlugin,openssl,OpenSSL crypto,+PACKAGE_newstrongswan-mod-openssl:libopenssl))
$(eval $(call BuildPlugin,pem,PEM decoding,))
$(eval $(call BuildPlugin,pgp,PGP key decoding,))
$(eval $(call BuildPlugin,pkcs1,PKCS1 key decoding,))
$(eval $(call BuildPlugin,pkcs7,PKCS7 key decoding,))
$(eval $(call BuildPlugin,pkcs8,PKCS8 key decoding,))
$(eval $(call BuildPlugin,pkcs11,PKCS11 key decoding,))
$(eval $(call BuildPlugin,pkcs12,PKCS12 key decoding,))
$(eval $(call BuildPlugin,pubkey,raw public key,))
$(eval $(call BuildPlugin,random,RNG,))
$(eval $(call BuildPlugin,rc2,RC2 crypto,))
$(eval $(call BuildPlugin,resolve,DNS resolver,))
$(eval $(call BuildPlugin,revocation,X509 CRL/OCSP revocation,))
$(eval $(call BuildPlugin,sha1,SHA1 crypto,))
$(eval $(call BuildPlugin,sha2,SHA2 crypto,))
$(eval $(call BuildPlugin,smp,SMP configuration and control interface,+PACKAGE_newstrongswan-mod-smp:libxml2))
$(eval $(call BuildPlugin,socket-default,default socket implementation for charon,))
$(eval $(call BuildPlugin,socket-dynamic,dynamic socket implementation for charon,))
$(eval $(call BuildPlugin,sql,SQL database interface,))
$(eval $(call BuildPlugin,sqlite,SQLite database interface,+newstrongswan-mod-sql +PACKAGE_newstrongswan-mod-sqlite:libsqlite3))
$(eval $(call BuildPlugin,sshkey,SSH key decoding,))
$(eval $(call BuildPlugin,stroke,Stroke,+newstrongswan-charon +newstrongswan-ipsec))
$(eval $(call BuildPlugin,test-vectors,crypto test vectors,))
$(eval $(call BuildPlugin,uci,UCI config interface,+PACKAGE_newstrongswan-mod-uci:libuci))
$(eval $(call BuildPlugin,unity,Cisco Unity extension,))
$(eval $(call BuildPlugin,updown,updown firewall,))
$(eval $(call BuildPlugin,vici,Versatile IKE Configuration Interface,))
$(eval $(call BuildPlugin,whitelist,peer identity whitelisting,))
$(eval $(call BuildPlugin,x509,x509 certificate,))
$(eval $(call BuildPlugin,xauth-eap,EAP XAuth backend,))
$(eval $(call BuildPlugin,xauth-generic,generic XAuth backend,))
$(eval $(call BuildPlugin,xcbc,xcbc crypto,))
