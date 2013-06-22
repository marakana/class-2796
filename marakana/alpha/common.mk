MY_PATH := $(LOCAL_PATH)/../alpha

# Include all makefiles in sub-directories (one level deep)
include $(call all-subdir-makefiles)

# Include all packages from this file
include $(MY_PATH)/packages.mk

# Enable overlays
DEVICE_PACKAGE_OVERLAYS := $(MY_PATH)/overlay

# Enable our custom kernel
LOCAL_KERNEL := $(MY_PATH)/kernel
PRODUCT_COPY_FILES += $(LOCAL_KERNEL):kernel

PRODUCT_COPY_FILES += $(MY_PATH)/init.marakanaalphaboard.rc:root/init.marakanaalphaboard.rc
PRODUCT_COPY_FILES += $(MY_PATH)/ueventd.marakanaalphaboard.rc:root/ueventd.marakanaalphaboard.rc

# Get the emulator-specific support for vold.fstab, which mounts the external storage (sdcard)
PRODUCT_COPY_FILES += system/core/rootdir/etc/vold.fstab:system/etc/vold.fstab

PRODUCT_COPY_FILES += frameworks/native/data/etc/handheld_core_hardware.xml:system/etc/permissions/handheld_core_hardware.xml
