load("//build/bazel_common_rules/dist:dist.bzl", "copy_to_dist_dir")
load("//msm-kernel:target_variants.bzl", "get_all_variants")
load("//build/kernel/kleaf:kernel.bzl", "ddk_module")

def define_modules(target, variant):
    kernel_build_variant = "{}_{}".format(target, variant)

    mod_list = []

    ddk_module(
		name = "{}-defconfig_aquantia".format(kernel_build_variant),
		out = "aquantia.ko",
		srcs = [
			"aquantia_hwmon.c",
			"aquantia_main.c",
			"aquantia.h"
		],
		conditional_srcs = {
			"CONFIG_MACSEC": {
				True: [
					"aqr_macsec/aqr_macsec.c",
					"aqr_macsec/aqr_macsec.h",
					"aqr_macsec/MSS_Ingress_registers.h",
					"aqr_macsec/MSS_Egress_registers.h",
				],
			},
		},
		kernel_build = "//msm-kernel:{}-defconfig".format(kernel_build_variant),
		deps = [
			"//msm-kernel:all_headers",
		],
		copts = [
			"-Wno-error",
		],
	)
    mod_list.append("{}-defconfig_aquantia".format(kernel_build_variant))
    copy_to_dist_dir(
		name = "{}-defconfig_aquantia_dist".format(kernel_build_variant),
		data = mod_list,
		dist_dir = "out/target/product/{}/dlkm/lib/modules/".format(target),
		flat = True,
		wipe_dist_dir = False,
		allow_duplicate_filenames = False,
		mode_overrides = {"**/*": "644"},
		log = "info",
	)
