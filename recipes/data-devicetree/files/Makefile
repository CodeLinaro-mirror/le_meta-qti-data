# Yocto-only Makefile (copied into ${S}/Makefile by data-devicetree.bb)
# The recipe passes DTC and KERNEL_INCLUDE explicitly.

KERNEL_INCLUDE ?= $(LINUX_DIR)/include/
DTC ?= $(LINUX_DIR)/scripts/dtc/dtc

DTBO += ipa/echo-ipa
DTBO += eth/echo-idp
DTBO += eth/echo-idp-qmp5
DTBO += eth/echo-idp-qmp5-v2
DTBO += eth/echo-idp-qmp5-v3

.PHONY: dtbs clean
dtbs: $(DTBO)

%:
	${CC} -undef -x assembler-with-cpp $(shell pwd)/$@.dts -I ${KERNEL_INCLUDE} -E -o $(shell pwd)/$@.dts.preprocessed
	${DTC} -@ -O dtb -o $(shell pwd)/$@.dtbo $(shell pwd)/$@.dts.preprocessed

clean:
	rm -rf $(shell find . -name "*.dts.preprocessed" -o -name "*.dtbo")
