require ianatimezone.inc

SUMMARY = "tzcode, timezone zoneinfo utils -- zic, zdump, tzselect"

S = "${UNPACKDIR}"

inherit native

EXTRA_OEMAKE += "cc='${CC}'"

do_install () {
  install -d ${D}${bindir}/
  install -m 755 zic ${D}${bindir}/
  install -m 755 zdump ${D}${bindir}/
  install -m 755 tzselect ${D}${bindir}/
}

