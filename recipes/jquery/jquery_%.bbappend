
do_install:append() {
        install -m 0755 -d ${D}/WEBSERVER/www/js/
        install -m 0644 ${WORKDIR}/jquery-${PV}.min.js ${D}/WEBSERVER/www/js/jquery.js
}

FILES:${PN} += "/WEBSERVER/www/js/jquery.js"