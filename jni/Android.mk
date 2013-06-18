LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := LogLib
LOCAL_SRC_FILES := LogLib.c

LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)
