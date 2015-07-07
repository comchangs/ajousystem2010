/*
 * DotMatrix Device Driver
 *  Hanback Electronics Co.,ltd
 * File : dotmatrix.c
 * Date : January, 2010
 */ 

#include <linux/init.h>
#include <linux/module.h>
#include <asm/uaccess.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/types.h>
#include <asm/ioctl.h>
#include <linux/ioport.h>
#include <linux/delay.h>
#include <asm/io.h>

#define DRIVER_AUTHOR			"Hanback Electronics"	// 모듈의 저작자
#define DRIVER_DESC			"Dotmatrix Test Program"// 모듈에 대한 설명
#define DOT_MAJOR			244			// 디바이스 주번호
#define DOT_NAME 			"DOT"			// 디바이스 이름
#define DOT_MODULE_VERSION 		"DOT V0.1"		// 디바이스 버전
#define DOT_PHY_ADDR			0x1480A000		// buzzer의 물리 주소
#define DOT_ADDR_RANGE 			0x3000			// I/O 영역의 크기
#define NUMSIZE				4

//Global variable
static int dot_usage = 0;		// 드라이버 사용여부를 확인하는 값
static unsigned long dot_ioremap;	// IO 주소 공간 저장
static unsigned short *dot_row_addr, *dot_col_addr;

// Number value
unsigned short font_num[40]= {
	0x7f, 0x41, 0x41, 0x7f,  // 0
	0x40, 0x7f, 0x42, 0x00,  // 1
	0x4f, 0x49, 0x49, 0x79,  // 2
	0x7f, 0x49, 0x49, 0x49,  // 3
	0x10, 0x7f, 0x10, 0x1f,  // 4
	0x79, 0x49, 0x49, 0x4f,  // 5
	0x79, 0x49, 0x49, 0x7f,  // 6
	0x7f, 0x01, 0x01, 0x01,  // 7
	0x7f, 0x49, 0x49, 0x7f,  // 8
	0x7f, 0x49, 0x49, 0x4f,  // 9
};

// define functions...
// 응용 프로그램에서 디바이스를 처음 사용하는 경우를 처리하는 함수
int dot_open(struct inode *minode, struct file *mfile) 
{
	// 디바이스가 열려 있는지 확인.
	if(dot_usage != 0) return -EBUSY;
	
	// dot의 가상 주소 매핑
	dot_ioremap = (unsigned long)ioremap(DOT_PHY_ADDR,DOT_ADDR_RANGE);

	// 등록할 수 있는 I/O 영역인지 확인
	if(!check_mem_region(dot_ioremap, DOT_ADDR_RANGE))
		// I/O 메모리 영역을 등록
		request_mem_region(dot_ioremap, DOT_ADDR_RANGE, DOT_NAME);
	else	printk("driver: unable to register this!\n");
	
	// row, col의 주소 설정
	dot_col_addr = (unsigned short *)dot_ioremap;
	dot_row_addr = (unsigned short *)(dot_ioremap+0x2000);

	dot_usage = 1;
	return 0;
}

// 응용 프로그램에서 디바이스를 더이상 사용하지 않아서 닫기를 구현하는 함수
int dot_release(struct inode *minode, struct file *mfile) 
{
	// 매핑된 가상주소를 해제
	iounmap((unsigned long *)dot_ioremap);

	// 등록된 I/O 메모리 영역을 해제
	release_mem_region(dot_ioremap, DOT_ADDR_RANGE);
	dot_usage = 0;
	return 0;
}

// 디바이스 드라이버의 쓰기를 구현하는 함수
ssize_t dot_write(struct file *inode, const char *gdata, size_t length, loff_t *off_what) 
{
	volatile int i,j,k;
	int size=0,cnt=0,ret, up_digit=0,quit=1,counter;
	unsigned int second_data, dispdata[8];
	unsigned int init_col1=0x100, init_col2=0x008; // Scan value

	// 사용자 메모리 gdata를 커널 메모리 counter에 n만큼 복사
	ret = copy_from_user(&counter, gdata, 4);
	if(ret < 0) return -1;

	while(quit) {
		if(cnt == 10) {
			cnt = 0;
			up_digit++;
		}

		if(up_digit == 10) up_digit=0;

		if(up_digit == 0) {
			size = 4;
			for(k=0;k<4;k++) {
				dispdata[k] = font_num[cnt*NUMSIZE+k];
			}
		} else {
			size = 8;
			second_data = up_digit%10;
			for(k=0;k<4;k++) {
				dispdata[k] = font_num[cnt*NUMSIZE+k];
				dispdata[k+4] = font_num[second_data*NUMSIZE+k];
			}
		}
		
		for(i=0;i<40;i++) {
			for(j=0;j<size;j++) {
				if(j<4)	{ // 1*x
					*dot_col_addr = init_col1 >> j;
					*dot_row_addr = dispdata[j];
					if(cnt<10) mdelay(5);
					else mdelay(2);
				} else {
					*dot_col_addr = init_col2 >> (j-4);
					*dot_row_addr = dispdata[j];
					mdelay(2);
				}
			}
		}
	
		if(counter == (up_digit*10 + cnt)) {
			quit = 0;
			*dot_col_addr = 0x0;
			break;
		}
		cnt++;
	}

	return length;
}

// 파일 오퍼레이션 구조체
// 파일을 열때 open()을 사용한다. open()는 시스템 콜을 호출하여 커널 내부로 들어간다.
// 해당 시스템 콜과 관련된 파일 연산자 구조체 내부의 open에 해당하는 필드가 드라이버 내에서
// dot_open()으로 정의되어 있으므로 dot_open()가 호출된다.
// write와 release도 마찬가지로 동작한다. 만약 등록되지 않은 동작에 대해서는 커널에서 정의해
// 놓은 default 동작을 하도록 되어 있다.
struct file_operations dot_fops = {
	.owner		= THIS_MODULE,
	.write		= dot_write,
	.open		= dot_open,
	.release	= dot_release,
};

// 모듈을 커널 내부로 삽입
// 모듈 프로그램의 핵심적인 목적은 커널 내부로 들어가서 서비스를 제공받는 것이므로
// 커널 내부로 들어가는 init()을 먼저 시작한다.
// 응용 프로그램은 소스 내부에서 정의되지 않은 많은 함수를 사용한다. 그것은 외부
// 라이브러리가 컴파일 과정에서 링크되어 사용되기 때문이다. 모듈 프로그램은 커널
// 내부하고만 링크되기 때문에 커널에서 정의하고 허용하는 함수만을 사용할 수 있다.
int dot_init(void) 
{
	int result;

	// 문자 디바이스 드라이버를 등록한다.
	result = register_chrdev(DOT_MAJOR, DOT_NAME, &dot_fops);
	
	if(result < 0) {	// 등록실패
		printk(KERN_WARNING"driver: Can't get any major\n");
		return result;
	}
	
	// major 번호를 출력한다.
	printk("driver: Init Module, Dotmatrix Major Number : %d\n", DOT_MAJOR);

	return 0;
}

// 모듈을 커널에서 제거
void dot_exit(void) 
{
	// 문자 디바이스 드라이버를 제거한다.
	unregister_chrdev(DOT_MAJOR, DOT_NAME);

	printk("driver: %s DRIVER EXIT\n", DOT_NAME);
}

module_init(dot_init);		// 모듈 적재 시 호출되는 함수
module_exit(dot_exit);		// 모듈 제거 시 호출되는 함수

MODULE_AUTHOR(DRIVER_AUTHOR); 	 // 모듈의 저작자
MODULE_DESCRIPTION(DRIVER_DESC); // 모듈에 대한 설명
MODULE_LICENSE("Dual BSD/GPL");	 // 모듈의 라이선스 등록
