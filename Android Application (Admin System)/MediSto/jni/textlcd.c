/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
#include <android/log.h>

#include "textlcd.h"

static struct strcommand_varible strcommand;
static int initialized = 0;

void initialize()
{
	if(!initialized)
	{
		//__android_log_print(ANDROID_LOG_INFO, "Java_com_JavaComm","JavaComm_open()\n");
		//__android_log_print(ANDROID_LOG_DEBUG, "TEXTLCD_JNI","initialize()\n");
		strcommand.rows = 0;
		strcommand.nfonts = 0;
		strcommand.display_enable = 1;
		strcommand.cursor_enable = 0;
		strcommand.nblink = 0;
		strcommand.set_screen = 0;
		strcommand.set_rightshit = 1;
		strcommand.increase = 1;
		strcommand.nshift = 0;
		strcommand.pos = 10;
		strcommand.command = 1;
		strcommand.strlength = 16;
		initialized = 1;
	}
}

int TextLCDIoctol(int cmd, char *buf)
{
	int fd,ret,i;

	fd = open("/dev/textlcd",O_WRONLY | O_NDELAY);
	if(fd < 0) return -errno;

	if(cmd == TEXTLCD_WRITE_BYTE) {
		ioctl(fd,TEXTLCD_DD_ADDRESS,&strcommand,32);
		for(i=0;i<strlen(buf);i++)
		{
			strcommand.buf[0] = buf[i];
			ret = ioctl(fd, cmd, &strcommand, 32);		
		}
	} else {
		ret = ioctl(fd, cmd, &strcommand, 32);
	}

	close(fd);

	//__android_log_print(ANDROID_LOG_DEBUG, "TEXTLCD_JNI","TextLCDIoctol() TEXTLCD_WRITE_BYTE:%d cmd: %d pos:%d\n"
	//,TEXTLCD_WRITE_BYTE, cmd, strcommand.pos);

	return ret;
}


jint
Java_ajou_sigma_medisto_hwcontrol_TextLCDOut( JNIEnv* env,
					jobject thiz,  jstring data0, jstring data1)
{
	jboolean iscopy;
	char *buf0, *buf1;
	int fd,ret;
	//__android_log_print(ANDROID_LOG_DEBUG, "TEXTLCD_JNI", "TextLCDOut()\n");

	fd = open("/dev/textlcd",O_WRONLY | O_NDELAY);
	if(fd < 0) return -errno;

	initialize();

	buf0 = (char *)(*env)->GetStringUTFChars(env,data0,&iscopy);
	buf1 = (char *)(*env)->GetStringUTFChars(env,data1,&iscopy);

	strcommand.pos = 0;
	ioctl(fd,TEXTLCD_DD_ADDRESS,&strcommand,32);
	ret = write(fd,buf0,strlen(buf0));

	strcommand.pos = 40;
	ioctl(fd,TEXTLCD_DD_ADDRESS,&strcommand,32);
	ret = write(fd,buf1,strlen(buf1));

	close(fd);

	return ret;
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlWirteByte( JNIEnv* env,
					jobject thiz,  jstring data )
{
	jboolean iscopy;
	char *buf;
	int i,ret, dev;

	//__android_log_print(ANDROID_LOG_DEBUG, "TEXTLCD_JNI", "IOCtlWirteByte()\n");
	buf = (char *)(*env)->GetStringUTFChars(env,data,&iscopy);
	
	initialize();

	ret = TextLCDIoctol( TEXTLCD_WRITE_BYTE,buf);

	return ret;
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlPos( JNIEnv* env,
					jobject thiz,  jint pos )
{
	initialize();
	strcommand.pos = pos;
	return TextLCDIoctol(TEXTLCD_DD_ADDRESS,NULL);
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlClear( JNIEnv* env,
						jobject thiz )
{
	initialize();
	return TextLCDIoctol(TEXTLCD_CLEAR,NULL);
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlReturnHome( JNIEnv* env,
						jobject thiz)
{
	initialize();
	return TextLCDIoctol(TEXTLCD_RETURN_HOME,NULL);
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlDisplay( JNIEnv* env,
					jobject thiz, jboolean bOn)
{
	initialize();
	if(bOn) {
		strcommand.display_enable =  0x01;
	} else {
		strcommand.display_enable =  0x00;
	}
	return TextLCDIoctol(TEXTLCD_DISPLAY_CONTROL,NULL);
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlCursor( JNIEnv* env,
					jobject thiz , jboolean bOn)
{
	initialize();
	if(bOn) {
		strcommand.cursor_enable = 0x01;
	} else  {
		strcommand.cursor_enable = 0x00;
	}

	return TextLCDIoctol(TEXTLCD_DISPLAY_CONTROL,NULL);
}

jint
Java_ajou_sigma_medisto_hwcontrol_IOCtlBlink( JNIEnv* env,
					jobject thiz, jboolean bOn )
{
	initialize();
	if(bOn) {
		strcommand.nblink = 0x01;
	} else {
		strcommand.nblink = 0x00;
	}
	return TextLCDIoctol(TEXTLCD_DISPLAY_CONTROL,NULL);
}

