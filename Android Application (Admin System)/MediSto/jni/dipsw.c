/*
 * Copyright (C) 2009 Hanback Electronics Inc.
 *
 */
#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <errno.h>

static int fd;

jint
Java_ajou_sigma_medisto_hwcontrol_Open( JNIEnv* env,
                                                  jobject thiz )
{
	int ret;

	fd = open("/dev/dipsw",O_RDONLY);

	if(fd <= 0) return -errno;

	return fd;
}

jint
Java_ajou_sigma_medisto_hwcontrol_Close( JNIEnv* env,
                                                  jobject thiz )
{
	if(fd > 0) {
		close(fd);
	}

	return 0;
}

jint
Java_ajou_sigma_medisto_hwcontrol_GetValue( JNIEnv* env,
                                                  jobject thiz )
{
	int ret;
	int data;

	if(fd < 0) 
		fd = open("/dev/dipsw",O_RDONLY);

	if(fd < 0) return -errno;

	ret = read(fd, &data, 4);

	if(ret == 4) return data;

	return -1;
}

