inherit update-rc.d

# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ed1a118f474eea5e159b560c339329b"

# DONE: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-amasini0.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
# DONE: set to reference a specific commit hash in your assignment repo
SRCREV = "a06929268ec7e56912fb4967ed1a68e92160119f"

# Add script for launch at system startup
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} += "aesdsocketd"
INITSCRIPT_PARAMS = "defaults 99"

# Build & install package
S = "${WORKDIR}/git"

TARGET_CFLAGS += "-DUSE_AESD_CHAR_DEVICE"
EXTRA_OEMAKE:task-compile += "-C ${S}/server"
RDEPENDS:${PN} += "libgcc"

do_compile () {
    oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/server/aesdsocket ${D}${bindir}/
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${S}/server/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocketd
}


# Files installed by this package
FILES:${PN} += "\
    ${bindir}/aesdsocket \
    ${sysconfdir}/init.d/aesdsocketd \
"
