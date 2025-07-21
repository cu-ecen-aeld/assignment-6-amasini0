inherit module
inherit update-rc.d

# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "\
    git://git@github.com/cu-ecen-aeld/assignment-7-amasini0.git;protocol=ssh;branch=main \
    file://miscdev \
    file://module_load.sh \
    file://module_unload.sh \
"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "677b5cfdddd42b61cb5850e4e3c220af5db63e91"

# Startup/shutdown scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} += "miscdev"
INITSCRIPT_PARAMS += "defaults 98"

# Package build/install
S = "${WORKDIR}/git"

EXTRA_OEMAKE:task-compile += "-C ${S}/misc-modules KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:task-install += "-C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"

do_install:append() {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/miscdev ${D}${sysconfdir}/init.d/
    install -m 0755 -d ${D}${sysconfdir}/lddmodules
    install -m 0755 ${WORKDIR}/module_*.sh ${D}${sysconfdir}/lddmodules/
}

# Package files
FILES:${PN} += "\
    ${libdir}/modules/${KERNEL_VERSION}/extra/hello.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/hellop.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/seq.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/jiq.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/sleepy.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/complete.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/silly.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/kdatasize.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/kdataalign.ko \
    ${libdir}/modules/${KERNEL_VERSION}/extra/jit.ko \
    ${sysconfdir}/init.d/miscdev \
    ${sysconfdir}/lddmodules/module_load.sh \
    ${sysconfdir}/lddmodules/module_unload.sh \
"
