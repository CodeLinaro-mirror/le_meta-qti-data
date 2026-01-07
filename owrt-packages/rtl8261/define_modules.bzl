load("//build/bazel_common_rules/dist:dist.bzl", "copy_to_dist_dir")
load("//msm-kernel:target_variants.bzl", "get_all_variants")
load("//build/kernel/kleaf:kernel.bzl", "ddk_module")

def define_modules(target, variant):
    kernel_build_variant = "{}_{}".format(target, variant)

    #The below will take care of the defconfig
    include_defconfig = ":{}_defconfig".format(variant)

    mod_list = []

    ddk_module(
        name = "{}-defconfig_rtl8261".format(kernel_build_variant),
        out = "rtl8261.ko",
        srcs = [
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phy.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phy.c",
            "linux-5.10.146/drivers/net/phy/rtk/phy_patch.h",
            "linux-5.10.146/drivers/net/phy/rtk/phy_patch.c",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_osal.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_osal.c",
            "linux-5.10.146/drivers/net/phy/rtk/phy_rtl826xb_patch.h",
            "linux-5.10.146/drivers/net/phy/rtk/phy_rtl826xb_patch.c",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib.c",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_rtl826xb.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_rtl826xb.c",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_macsec.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_def.h",
            "linux-5.10.146/drivers/net/phy/rtk/type.h",
            "linux-5.10.146/drivers/net/phy/rtk/error.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_rtl8224.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_rtl8224.c",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phy_rtl8224.h",
            "linux-5.10.146/drivers/net/phy/rtk/rtk_phy_rtl8224.c",
            "linux-5.10.146/drivers/net/phy/rtk/phy_rtl8224_patch.h",
            "linux-5.10.146/drivers/net/phy/rtk/phy_rtl8224_patch.c",
        ],
        textual_hdrs = [
                "linux-5.10.146/drivers/net/phy/rtk/construct/conf_rtl8261n_c.c",
                "linux-5.10.146/drivers/net/phy/rtk/construct/conf_rtl8261n_c_lp.c",
                "linux-5.10.146/drivers/net/phy/rtk/construct/conf_rtl8264b.c",
				"linux-5.10.146/drivers/net/phy/rtk/construct/conf_rtl8224.c",
         ],
        conditional_srcs = {
            "CONFIG_MACSEC": {
                True: [
                    "linux-5.10.146/drivers/net/phy/rtk/rtk_macsec.c",
                    "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_macsec.c",
                    "linux-5.10.146/drivers/net/phy/rtk/rtk_phylib_macsec.h",
                ],
            },
        },
        kernel_build = "//msm-kernel:{}-defconfig".format(kernel_build_variant),
        deps = [
            "//msm-kernel:all_headers",
        ],
        copts = [
            "-Wno-error",
            "-DRTK_PHYDRV_IN_LINUX",
        ],
    )
    mod_list.append("{}-defconfig_rtl8261".format(kernel_build_variant))
    copy_to_dist_dir(
        name = "{}-defconfig_rtl8261_dist".format(kernel_build_variant),
        data = mod_list,
        dist_dir = "out/target/product/{}/dlkm/lib/modules/".format(target),
        flat = True,
        wipe_dist_dir = False,
        allow_duplicate_filenames = False,
        mode_overrides = {"**/*": "644"},
        log = "info",
    )
