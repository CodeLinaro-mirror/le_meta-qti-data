
do_configure:prepend:sdxprairie() {
	# Bring in Aquantia IPA Offload Driver that resides in data-kernel project
	AQO_DIR="drivers/platform/msm/ipa/ipa_v3/ethernet/aquantia"
	if [ -d "${S}/${AQO_DIR}" ] && [ -d "${WORKSPACE}/data-kernel/${AQO_DIR}" ]; then
		cp -f "${WORKSPACE}/data-kernel/${AQO_DIR}"/* "${S}/${AQO_DIR}"/
	fi
}

