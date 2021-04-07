
R8168_OBJDIR ?= $(shell pwd)
R8168_SRCDIR ?= $(R8168_OBJDIR)

all:
	$(MAKE) -C $(KERNEL_SRC) src=$(R8168_SRCDIR) M=$(R8168_OBJDIR)

modules_install:
	$(MAKE) -C $(KERNEL_SRC) src=$(R8168_SRCDIR) M=$(R8168_OBJDIR) modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
