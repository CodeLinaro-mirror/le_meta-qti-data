
ETH_OBJDIR ?= $(shell pwd)
ETH_SRCDIR ?= $(ETH_OBJDIR)

DRIVERS = drivers/qps615_ioss drivers/qps615/src drivers/ioss drivers/r8125/src drivers/aqc_ioss


all: $(DRIVERS)

$(DRIVERS):
	$(MAKE) -C $(KERNEL_SRC) src=$(ETH_SRCDIR)/$@ M=$(ETH_OBJDIR)

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers

.PHONY: all clean $(DRIVERS)

