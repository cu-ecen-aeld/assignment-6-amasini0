inherit module
inherit update-rc.d

# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ed1a118f474eea5e159b560c339329b"

SRC_URI = "\
    git://git@github.com/cu-ecen-aeld/assignments-3-and-later-amasini0.git;protocol=ssh;branch=main \
    file://aesdchardev \
"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "a06929268ec7e56912fb4967ed1a68e92160119f"

# Startup/shutdown scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} += "aesdchardev"
INITSCRIPT_PARAMS = "defaults 98"

# Package build/install
S = "${WORKDIR}/git"

EXTRA_OEMAKE:task-compile += "-C ${S}/aesd-char-driver KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:task-install += "-C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
MODULES_MODULE_SYMVERS_LOCATION = "aesd-char-driver"

do_install:append() {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdchardev ${D}${sysconfdir}/init.d/aesdchardev
    install -m 0755 -d ${D}${sysconfdir}/aesdchar.d
    install -m 0755 ${S}/aesd-char-driver/aesdchar_* ${D}${sysconfdir}/aesdchar.d/
}

# Files provided by this recipe
FILES:${PN} += "\
    ${libdir}/modules/${KERNEL_VERSION}/extra/aesdchar \
    ${sysconfdir}/init.d/aesdchardev \
    ${sysconfdir}/aesdchar.d/aesdchar_load \
    ${sysconfdir}/aesdchar.d/aesdchar_unload \
"

