LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := MediStoUser

LOCAL_SRC_FILES := ciscamera.c\
		   buzzer.c


include $(BUILD_SHARED_LIBRARY)

