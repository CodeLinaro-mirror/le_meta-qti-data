
R8125_OBJDIR ?= $(shell pwd)
R8125_SRCDIR ?= $(R8125_OBJDIR)

all:
	$(MAKE) -C $(KERNEL_SRC) src=$(R8125_SRCDIR) M=$(R8125_OBJDIR)

modules_install:
	$(MAKE) -C $(KERNEL_SRC) src=$(R8125_SRCDIR) M=$(R8125_OBJDIR) modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
