SUMMARY = "Cypress Backport tool"
LICENSE = "GPLv2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"


inherit systemd
inherit native

SYSTEMD_AUTO_ENABLE = "disable"

SRC_URI =  "https://github.com/murata-wireless/cyw-fmac/raw/imx-morty-mothra/imx-morty-mothra_r${PV}.tar.gz;name=archive1"
SRC_URI += "https://github.com/murata-wireless/meta-murata-wireless/raw/imx-morty-mothra/LICENSE;name=archive99"

SRC_URI[archive1.md5sum] = "ece3be38ad0519e0ab7490adbc14f411"
SRC_URI[archive1.sha256sum] = "cd23242e06aa80a3a4104172884c5671bfd58baf34e12b25652f5c80fd1396ad"

#LICENSE
SRC_URI[archive99.md5sum] = "b234ee4d69f5fce4486a80fdaf4a4263"
SRC_URI[archive99.sha256sum] = "8177f97513213526df2cf6184d8ff986c675afb514d4e68a404010521b880643"

S = "${WORKDIR}/backporttool-native-${PV}"
B = "${WORKDIR}/backporttool-native-${PV}/"

DEPENDS = "linux-imx"

do_configure () {
	echo "Configuring"
        echo "KLIB:       ${KLIB}"
        echo "KLIB_BUILD: ${KLIB_BUILD} "
}

export EXTRA_CFLAGS = "${CFLAGS}"
export BINDIR = "${sbindir}"

do_compile () {
	echo "Compiling: "
        #echo "Testing Make Display:: ${MAKE}"
        pwd

        echo "KLIB:       ${KLIB}"
        echo "KLIB_BUILD: ${KLIB_BUILD} "
        echo "KBUILD_OUTPUT: ${KBUILD_OUTPUT}"

	rm -rf .git 
	cp ${STAGING_KERNEL_BUILDDIR}/.config ${STAGING_KERNEL_DIR}/.config
	cp ${STAGING_KERNEL_BUILDDIR}/kernel-abiversion ${STAGING_KERNEL_DIR}/kernel-abiversion

        cp -a ${TMPDIR}/work/x86_64-linux/backporttool-native/${PV}-r0/imx-morty-mothra_r${PV}/. .

        oe_runmake KLIB="${STAGING_KERNEL_DIR}" KLIB_BUILD="${STAGING_KERNEL_DIR}" defconfig-brcmfmac
}

do_install () {
	echo "Installing: "

        install -d ${D}${sbindir}

#       Copies .config into /murata folder
#        install -m 0644 ${S}/.config ${D}${sbindir}
}

FILES_${PN} += "${sbindir}"
PACKAGES += "FILES-${PN}"

