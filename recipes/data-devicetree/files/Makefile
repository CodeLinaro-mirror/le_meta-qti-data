# Yocto-only Makefile (copied into ${S}/Makefile by data-devicetree.bb)
# The recipe passes DTC, KERNEL_INCLUDE and TARGET explicitly.

KERNEL_INCLUDE ?= $(LINUX_DIR)/include/
DTC ?= $(LINUX_DIR)/scripts/dtc/dtc

ifeq ($(TARGET_SUPPORT), sa535m)
DTBO += eth/sa535m-eth
DTBO += ipa/sa535m-ipa
else ifeq ($(TARGET_SUPPORT), echo)
DTBO += ipa/echo-ipa
DTBO += eth/echo-idp
DTBO += eth/echo-idp-qmp5
DTBO += eth/echo-idp-qmp5-v2
DTBO += eth/echo-idp-qmp5-v3
endif

.PHONY: dtbs clean
dtbs: $(DTBO)

%:
	${CC} -undef -x assembler-with-cpp $(shell pwd)/$@.dts -I ${KERNEL_INCLUDE} -E -o $(shell pwd)/$@.dts.preprocessed
	${DTC} -@ -O dtb -o $(shell pwd)/$@.dtbo $(shell pwd)/$@.dts.preprocessed

clean:
	rm -rf $(shell find . -name "*.dts.preprocessed" -o -name "*.dtbo")
