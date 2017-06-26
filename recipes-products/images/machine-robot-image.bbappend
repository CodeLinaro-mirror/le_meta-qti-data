IMAGE_INSTALL_apq8009 += "${@base_conditional('PRODUCT','robot-rome', 'iputils', '', d)}"
