include $(TOPDIR)/rules.mk

PKG_NAME:=ianatzdata
PKG_VERSION:=2019a
PKG_RELEASE:=1

PKG_SOURCE:=tzcode$(PKG_VERSION).tar.gz
PKG_SOURCE_URL:=http://www.iana.org/time-zones/repository/releases/
PKG_MD5SUM:=27585a20bc5401324f42c8deb6e4677f
PKG_LICENSE:=PD & BSD & BSD-3-Clause
UNPACK_CMD=gzip -dc $(DL_DIR)/$(PKG_SOURCE) | $(HOST_TAR) -C $(1) $(TAR_OPTIONS)

include $(INCLUDE_DIR)/package.mk

TZDATA_FILE:=tzdata$(PKG_VERSION).tar.gz
define Download/tzdata
	FILE:=$(TZDATA_FILE)
	URL:=http://www.iana.org/time-zones/repository/releases/
	HASH:=90366ddf4aa03e37a16cd49255af77f801822310b213f195e2206ead48c59772
endef

$(eval $(call Download,tzdata))

define Package/$(PKG_NAME)
	SECTION:=Data
	CATEGORY:=QTI-PACKAGES
	TITLE:=ianatzdata
	URL:=http://www.iana.org/time-zones
endef

define Package/$(PKG_NAME)/Description
	ianatzdata, timezone zoneinfo utils -- zic, zdump, tzselect
endef

define Build/Prepare
	$(PKG_UNPACK)
	tar -C $(PKG_BUILD_DIR) -xvzf $(DL_DIR)/$(TZDATA_FILE)
endef

TZONES:=africa antarctica asia australasia europe northamerica southamerica \
         factory etcetera backward systemv
define Build/Compile
	$(call Build/Compile/Default)
# generate zoneinfo file using zic
	for zone in $(TZONES); do \
		zic -d $(PKG_BUILD_DIR)/zoneinfo -L /dev/null -y $(PKG_BUILD_DIR)/yearistype.sh $(PKG_BUILD_DIR)/$$$$zone; \
	done

# Create an iana database file with timezones and respective posix code
	cd $(PKG_BUILD_DIR)/zoneinfo; \
	timezones=$$$$(find . -type f \( ! -iname "*.list" ! -iname "*.tab" \)); \
	for tz in $$$$timezones; do \
	if ! ( [[ $$$$tz == *"posix"* ]] || [[ $$$$tz == *"right"* ]] ); then\
		pcode=$$$$(tail -1 $$$$tz); \
		echo "$$$${tz:2}:[$$$$pcode]" >> $(PKG_BUILD_DIR)/iana_tzdb; \
	fi; \
	done
endef

define Package/$(PKG_NAME)/install
	$(INSTALL_DIR) $(1)/bin
	$(INSTALL_DATA) $(PKG_BUILD_DIR)/iana_tzdb $(1)/bin
endef

$(eval $(call BuildPackage,$(PKG_NAME)))
