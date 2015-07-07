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

#define IMAGE_SIZE0 320*240*2
#define IMAGE_SIZE1 160*120*2

static int cis_setting = 0;

jint
Java_ajou_sigma_medistouser_hwcontrol_CISCameraIOctl( JNIEnv* env,
		jobject thiz, jint cmd)
{
	int fd;

	fd = open("/dev/camera",O_RDWR | O_SYNC);

	if(fd < 0) return -errno;

	cis_setting = cmd;

	if (ioctl(fd, cis_setting, NULL)<0) {
		return -1;
	}

	close(fd);

	return 0;
}

jint
Java_ajou_sigma_medistouser_hwcontrol_CISCameraControl( JNIEnv* env,
		jobject thiz, jintArray image,jint width, jint height)
{
	int fd,ret;
	unsigned short rgb0[IMAGE_SIZE0];
	unsigned short rgb1[IMAGE_SIZE1];
	int *data;	
	int r,g,b,i;

	fd = open("/dev/camera",O_RDWR | O_SYNC);

	if(fd < 0) return -errno;
	
	ret = write(fd, NULL, 1);

	if(cis_setting==0) 
		ret = read(fd, rgb0, IMAGE_SIZE0);
	else if(cis_setting==1) 
		ret = read(fd, rgb1, IMAGE_SIZE1);

	close(fd);

	if(ret == 0) return -1;

	data =(int *) (*env)->GetIntArrayElements(env, image, 0) ;

	if(cis_setting==0)  {
		for(i=0;i<IMAGE_SIZE0;i++) {
			r = ((rgb0[i] & 0xF800) << 8) | 0xff070000;
			g = ((rgb0[i] & 0x07E0) << 5) | 0xff000700;
			b = ((rgb0[i] & 0x001F) << 3) | 0xff000007;
			data[i]= (r|g|b);
		}
	}
	else if(cis_setting==0)  {
		for(i=0;i<IMAGE_SIZE1;i++) {
			r = ((rgb1[i] & 0xF800) << 8) | 0xff070000;
			g = ((rgb1[i] & 0x07E0) << 5) | 0xff000700;
			b = ((rgb1[i] & 0x001F) << 3) | 0xff000007;
			data[i]= (r|g|b);
		}
	}

	(*env)->ReleaseIntArrayElements(env, image , data,  0) ;

	return 0;
}

