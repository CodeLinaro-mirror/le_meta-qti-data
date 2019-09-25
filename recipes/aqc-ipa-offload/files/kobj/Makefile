
AQO_OBJDIR ?= $(shell pwd)
AQO_SRCDIR ?= $(AQO_OBJDIR)

all:
	$(MAKE) -C $(KERNEL_SRC) src=$(AQO_SRCDIR) M=$(AQO_OBJDIR)

modules_install:
	$(MAKE) -C $(KERNEL_SRC) src=$(AQO_SRCDIR) M=$(AQO_OBJDIR) modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
