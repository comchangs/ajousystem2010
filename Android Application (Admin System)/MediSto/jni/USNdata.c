//#include <string.h>
#include <jni.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <stdio.h>
  
#define BAUDRATE B57600
#define MODEMDEVICE "/dev/ttyUSB0"
#define _POSIX_SOURCE 1
#define FALSE 0
#define TRUE 1
  
volatile int STOP=FALSE; 
char buf[255];
    
JNIEXPORT jbyteArray JNICALL
Java_ajou_sigma_medisto_hwcontrol_USNdataJNI( JNIEnv* env,
                                                  jobject thiz )
{
    int fd,c, res, data, fd_debug, ret, i;
    struct termios oldtio,newtio;

     
    fd = open(MODEMDEVICE, O_RDWR | O_NOCTTY ); 
      
      tcgetattr(fd,&oldtio);

      bzero(&newtio, sizeof(newtio));
      newtio.c_cflag = BAUDRATE | CRTSCTS | CS8 | CLOCAL | CREAD;
      newtio.c_iflag = IGNPAR;
      newtio.c_oflag = 0;
      
      newtio.c_lflag = 0;
       
      newtio.c_cc[VTIME]    = 0;   
      newtio.c_cc[VMIN]     = 104;   
      
      tcflush(fd, TCIFLUSH);
      tcsetattr(fd,TCSANOW,&newtio);
     
      res = read(fd,buf,255);   
      buf[res]=0;                     

	  int size = 43;
	  jbyteArray result; 
	  result = (*env)->NewByteArray(env, size); 
	  if (result == NULL) 
	   	return NULL; 
     	
	  (*env)->SetByteArrayRegion(env, result, 0, size, buf); 
 	  return result;
}
