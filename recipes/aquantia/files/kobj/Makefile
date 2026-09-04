obj-m	+= aquantia.o

aquantia-objs := aquantia_main.o

# Enable MACsec offload when kernel MACsec support is present.
ifdef CONFIG_MACSEC
CONFIG_AQUANTIA_MACSEC := y
ccflags-y += -DCONFIG_AQUANTIA_MACSEC=1
endif

ifdef CONFIG_HWMON
aquantia-objs			+= aquantia_hwmon.o
endif

ifdef CONFIG_AQUANTIA_MACSEC
aquantia-objs			+= macsec/macsec.o
aquantia-objs			+= aqr_macsec/aqr_macsec.o
endif

KERNEL_SRC ?= /lib/modules/$(shell uname -r)/build

all:
	$(MAKE) -C $(KERNEL_SRC) M=$(shell pwd)

modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(shell pwd) modules_install
