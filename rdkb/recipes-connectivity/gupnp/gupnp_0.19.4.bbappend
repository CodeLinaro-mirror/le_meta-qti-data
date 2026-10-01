inherit autotools

python meson_do_qa_configure() {
    pass
}

EXTRA_OECONF += "--enable-introspection=no"

do_configure:prepend() {
    cp -f ${S}/m4/vapigen.m4 ${S}/m4/vapigen-custom.m4 || true
}

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-gupnp-binding-tool-use-python3.patch"
SRC_URI:append = " file://libgupnp-devprotection.patch"

INSANE_SKIP:${PN}-dev += "file-rdeps"
