SUMMARY = "Avahi IPv4LL network address configuration daemon"
DESCRIPTION = "Avahi is a fully LGPL framework for Multicast DNS Service Discovery. It \
allows programs to publish and discover services and hosts running on a local network \
with no specific configuration. This tool implements IPv4LL, "Dynamic Configuration of \
IPv4 Link-Local Addresses" (IETF RFC3927), a protocol for automatic IP address \
configuration from the link-local 169.254.0.0/16 range without the need for a central \
server."
AUTHOR = "Lennart Poettering <lennart@poettering.net>"
HOMEPAGE = "http://avahi.org"
BUGTRACKER = "http://avahi.org/report"
SECTION = "network"

# major part is under LGPLv2.1+, but several .dtd, .xsl, initscripts and
# python scripts are under GPLv2+
LICENSE = "GPL-2.0-or-later & LGPL-2.1-or-later"

INC_PR = "r12"

DEPENDS = "expat libcap libdaemon dbus glib-2.0 glib-2.0-native"

SRC_URI = "https://github.com/lathiat/avahi/releases/download/v${PV}/avahi-${PV}.tar.gz \
          file://00avahi-autoipd \
          file://99avahi-autoipd \
          file://99avahi_multi.service \
          file://99avahi-daemon.conf \
          file://99avahi-daemon \
          file://initscript.patch \
          file://avahi_daemon.service"

USERADD_PACKAGES = "avahi-daemon"
USERADD_PARAM:avahi-daemon = "--system --home /var/run/avahi-daemon \
                              --no-create-home --shell /bin/false \
                              --user-group avahi"

inherit autotools pkgconfig update-rc.d gettext useradd

EXTRA_OECONF = "--with-distro=debian \
             --disable-introspection \
             --with-avahi-priv-access-group=adm \
             --disable-stack-protector \
             --disable-gdbm \
             --disable-mono \
             --disable-monodoc \
             --disable-qt3 \
             --disable-qt4 \
             --disable-python \
             --disable-doxygen-doc \
             --disable-manpages \
             --with-systemdsystemunitdir=${base_libdir}/systemd/system/ \
             ${AVAHI_GTK} \
           "

AVAHI_GTK ?= "--disable-gtk --disable-gtk3"

LDFLAGS:append:libc-uclibc = " -lintl"
LDFLAGS:append:uclinux-uclibc = " -lintl"

do_configure:prepend() {
    sed 's:AM_CHECK_PYMOD:echo "no pymod" #AM_CHECK_PYMOD:g' -i ${WORKDIR}/avahi-${PV}/configure.ac
}


PACKAGES =+ "avahi-systemd avahi-daemon libavahi-common libavahi-core libavahi-client avahi-dnsconfd libavahi-glib libavahi-ui avahi-autoipd avahi-utils"

FILES:avahi-systemd = "${base_libdir}/systemd"
FILES:avahi-autoipd = "${sbindir}/avahi-autoipd \
                       ${sysconfdir}/avahi/avahi-autoipd.action \
                       ${sysconfdir}/dhcp3/*/avahi-autoipd"
FILES:libavahi-common = "${libdir}/libavahi-common.so.*"
FILES:libavahi-core = "${libdir}/libavahi-core.so.*"
FILES:avahi-daemon = "${sbindir}/avahi-daemon \
                      ${sysconfdir}/avahi/avahi-daemon.conf \
                      ${sysconfdir}/avahi/hosts \
                      ${sysconfdir}/avahi/services \
                      ${sysconfdir}/dbus-1 \
                      ${sysconfdir}/init.d/avahi-daemon \
                      ${sysconfdir}/initscripts/avahi-daemon \
                      ${datadir}/avahi/introspection/*.introspect \
                      ${datadir}/avahi/avahi-service.dtd \
                      ${datadir}/avahi/service-types \
                      ${datadir}/dbus-1/interfaces \
                      ${datadir}/dbus-1/system-services"
FILES:libavahi-client = "${libdir}/libavahi-client.so.*"
FILES:libavahi-ui = "${libdir}/libavahi-ui.so.*"
FILES:avahi-dnsconfd = "${sbindir}/avahi-dnsconfd \
                        ${sysconfdir}/avahi/avahi-dnsconfd.action \
                        ${sysconfdir}/init.d/avahi-dnsconfd"
FILES:libavahi-glib = "${libdir}/libavahi-glib.so.*"
FILES:libavahi-gobject = "${libdir}/libavahi-gobject.so.*"
FILES:avahi-utils = "${bindir}/avahi-*"

RDEPENDS:avahi-systemd = "avahi-daemon"

RDEPENDS:avahi-daemon = "${@bb.utils.contains('DISTRO_FEATURES','systemd', '', 'sysvinit-pidof', d)}"

# uclibc has no nss
RRECOMMENDS:avahi-daemon:append:libc-glibc = " libnss-mdns"
RRECOMMENDS:${PN}:append:libc-glibc = "libnss-mdns"

RRECOMMENDS:avahi-dev = "expat-dev libcap-dev libdaemon-dev dbus-dev glib-2.0-dev update-rc.d-dev"
RRECOMMENDS:avahi-dev:append:libc-glibc = " gettext-dev"

RRECOMMENDS:avahi-dev[nodeprrecs] = "1"

CONFFILES:avahi-daemon = "${sysconfdir}/avahi/avahi-daemon.conf"

INITSCRIPT_PACKAGES = "avahi-daemon avahi-dnsconfd"
INITSCRIPT_NAME:avahi-daemon = "avahi-daemon"
INITSCRIPT_PARAMS:avahi-daemon = "defaults 99 01"
INITSCRIPT_NAME:avahi-dnsconfd = "avahi-dnsconfd"
INITSCRIPT_PARAMS:avahi-dnsconfd = "defaults 99 01"

do_install() {
        bbnote install -d ${D}${bindir}
        install -d ${D}${bindir}

	autotools_do_install

	# don't install /var/run when populating rootfs. Do it through volatile
	# /var/run of current version is empty, so just remove it.
	# if /var/run become non-empty in the future, need to install it via volatile
        rm -rf ${D}/run
        rm -rf ${D}/var/run
        rm -rf ${D}/${libdir}/avahi
	rm -rf ${D}${datadir}/dbus-1/interfaces
	rm -rf ${D}${sysconfdir}/avahi/services/* 
	install ${WORKDIR}/99avahi_multi.service ${D}${sysconfdir}/avahi/services/multi.service
	rm -rf ${D}${sysconfdir}/avahi/avahi-daemon.conf 
	install ${WORKDIR}/99avahi-daemon.conf ${D}${sysconfdir}/avahi/avahi-daemon.conf
        install ${WORKDIR}/99avahi-daemon ${D}${sysconfdir}/init.d/avahi-daemon
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system/
        install -d ${D}${sysconfdir}/initscripts
        mv ${D}${sysconfdir}/init.d/avahi-dnsconfd ${D}${sysconfdir}/initscripts/avahi-dnsconfd
        mv ${D}${sysconfdir}/init.d/avahi-daemon ${D}${sysconfdir}/initscripts/avahi-daemon
        install -m 0644 ${WORKDIR}/avahi_daemon.service -D ${D}${systemd_unitdir}/system/avahi_daemon.service
    fi
}

do_install_avahi-autoipd() {
	autotools_do_install
	install -d ${D}${sysconfdir}/udhcpc.d
	install ${WORKDIR}/00avahi-autoipd ${D}${sysconfdir}/udhcpc.d
	install ${WORKDIR}/99avahi-autoipd ${D}${sysconfdir}/udhcpc.d
}

# At the time the postinst runs, dbus might not be setup so only restart if running 

pkg_postinst:avahi-daemon () {
	if [ "x$D" != "x" ]; then
		echo "do nothing during build"
	fi

	if [ command -v systemctl >/dev/null ]
	then
		DBUSPID=`pidof dbus-daemon`

		if [ "x$DBUSPID" != "x" ]; then
			/etc/init.d/dbus-1 force-reload
		fi
	else
		killall -q -HUP dbus-daemon || true
	fi
}

# can't do this offline
pkg_postinst:avahi-autoipd () {
	if [ "x$D" != "x" ]; then
		exit 0
	fi
	grep "^avahi-autoipd:" /etc/group > /dev/null || addgroup avahi-autoipd
	grep "^avahi-autoipd:" /etc/passwd > /dev/null || adduser --disabled-password --system --home /var/lib/avahi-autoipd --no-create-home avahi-autoipd --ingroup avahi-autoipd -g "Avahi autoip daemon"
}

pkg_postrm:avahi-daemon () {
	deluser avahi || true
	delgroup avahi || true
}

pkg_postrm:avahi-autoipd () {
	deluser avahi-autoipd || true
	delgroup avahi-autoipd || true
}

# can't do this offline
pkg_postinst:avahi-systemd() {
	if [ "x$D" != "x" ]; then
		exit 0
	fi
	
	systemctl enable avahi-daemon.service
}

pkg_postrm:avahi-systemd() {
	# can't do this offline
	if [ "x$D" != "x" ]; then
		exit 0
	fi

	systemctl disable avahi-daemon.service
}

