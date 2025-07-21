inherit packagegroup

DESCRIPTION = "Packages created for ECEN/AELD course assignments"
LICENSE = "MIT"

# Package groups
PACKAGES = "\
    ${PN} \
    ldd3-modules \
"

# Packages in aesd-assignments group
RDEPENDS:${PN} = "\
    aesdsocket \
"

# Packages in ldd3 group
RDEPENDS:ldd3-modules = "\
    misc-modules \
    scull \
"

