LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := MediSto

LOCAL_SRC_FILES := 7segment.c\
		   buzzer.c\
		   ciscamera.c\
		   dipsw.c\
		   dotmatrix.c\
		   led.c\
		   textlcd.c\
		   USNdata.c\

include $(BUILD_SHARED_LIBRARY)

