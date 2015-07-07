/*
 * CIS Camera Device Driver
 *  Hanback Electronics Co.,ltd
 * File : ledioport.c
 * Date : January, 2010
 */ 

// 모듈의 헤더파일 선언
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/types.h>
#include <linux/ioport.h>
#include <linux/interrupt.h>
#include <linux/irq.h>
#include <linux/errno.h>
#include <linux/wait.h>

#include <asm/io.h>
#include <asm/irq.h>
#include <asm/uaccess.h>
#include <mach/pxa-regs.h>

#define DRIVER_AUTHOR		"Hanback Electronics"		// 모듈의 저작자
#define DRIVER_DESC		"SMII-P320 CIS Camera Program"	// 모듈에 대한 설명
#define CIS_MAJOR		231				// 디바이스 주번호
#define CIS_NAME		"CAMERA"			// 디바이스 이름

#define DATA_ADDRESS 		0x16900000	// RAM Access address
#define FLAG_ADDRESS 		0x16980060	// CMOS Control address
#define SUBSAMPLING_ADDRESS	0x16980066	// 0:320*240,	1:160*120
#define MODE_ADDRESS		0x16980076	// 0:FPGA/INPUT,1:INPUT
#define FLAG_ADDRESS_RANGE 	0x1000
#define DATA_ADDRESS_RANGE 	0x258000
#define IRQ_CMOS		IRQ_GPIO(58)	// Camera Interrupt

#define VIEW_IMAGE_WIDTH0    	320
#define VIEW_IMAGE_HEIGHT0   	240
#define VIEW_IMAGE_SIZE0	VIEW_IMAGE_WIDTH0*VIEW_IMAGE_HEIGHT0*2

#define VIEW_IMAGE_WIDTH1       160
#define VIEW_IMAGE_HEIGHT1      120
#define VIEW_IMAGE_SIZE1        VIEW_IMAGE_WIDTH1*VIEW_IMAGE_HEIGHT1*2

#define SRAM_ADDRESS_RANGE0 	(VIEW_IMAGE_WIDTH0*VIEW_IMAGE_HEIGHT0*2)
#define SRAM_ADDRESS_RANGE1 	(VIEW_IMAGE_WIDTH1*VIEW_IMAGE_HEIGHT1*2)

#define SIZE_320240		0
#define SIZE_160120		1
#define MODE_0_FPGA_INPUT	2
#define MODE_1_INPUT		3

bool f_read=false;
static unsigned short *flag_ioremap,  *mode_select, *sub_sampling;
static unsigned short *data_ioremap;
static unsigned short rgb0[VIEW_IMAGE_WIDTH0*VIEW_IMAGE_HEIGHT0];
static unsigned short rgb1[VIEW_IMAGE_WIDTH1*VIEW_IMAGE_HEIGHT1];
static int view_image_width=320, view_image_height=240, view_image_size=320*240*2; // View Image SIZE
static int cis_set_val=0x0;
static DECLARE_WAIT_QUEUE_HEAD(wait_queue);

static irqreturn_t  cis_interrupt(int irq, void *dev_id) {
	int i,j;
	unsigned short k=0;

	if(f_read) {
		f_read=false;
		for(i=0;i<view_image_height;i++) {
			for(j=0;j<view_image_width;j++) {
				k = (i*view_image_width)+j;
				if(cis_set_val==0x00) {
					rgb0[k] = *(data_ioremap + k);
#if 0
					if(j<106) rgb0[k] = 0xf800;
					else if(j<212) rgb0[k] = 0x07e0;
					else rgb0[k] = 0x001f;
#endif
				} else if(cis_set_val==0x01) {
					rgb1[k] = *(data_ioremap + k);
				}
			}
		}

		f_read=true;
	}
	wake_up_interruptible(&wait_queue);

	return IRQ_HANDLED; 
}

// 디바이스 드라이버의 쓰기를 구현하는 함수
ssize_t cis_write(struct file *minode, const char *gdata, size_t length, loff_t *off_what) 
{
	*flag_ioremap = 0x1;
	f_read=true;

	return length;
}

// 디바이스 드라이버의 읽기를 구현하는 함수
ssize_t cis_read(struct file *mfile, char *buf, size_t count, loff_t *l) 
{
	int ret=0;

	interruptible_sleep_on_timeout(&wait_queue, 50);

	if(cis_set_val==0x00)
		ret=copy_to_user(buf, rgb0, view_image_size);
	else if(cis_set_val==0x01)
		ret=copy_to_user(buf, rgb1, view_image_size);

	if(ret<0) return -1;

	*flag_ioremap = 0x0;

	if(f_read) {
		f_read=false;
		return view_image_size;
	}
	else return 0;
}

// open() 함수를 이용하여 디바이스 드라이버가 열린 경우 호출되는 함수
static int cis_open(struct inode *minode, struct file *mfile) 
{
	int res;

	GPDR(IRQ_TO_GPIO(IRQ_CMOS)) &= ~GPIO_bit(IRQ_TO_GPIO(IRQ_CMOS));
	set_irq_type(IRQ_CMOS, IRQ_TYPE_EDGE_RISING);

	res = request_irq(IRQ_CMOS,cis_interrupt,IRQF_DISABLED,"FPGA CAMERA",NULL);

	if(res < 0)
		printk(KERN_ERR "%s: Request for IRQ %d failed\n",__FUNCTION__,IRQ_CMOS);

	*mode_select = 0x00;
	*sub_sampling = 0x00;

	return 0;
}

//read()와 write()로 구현하기 곤란한 디바이스 드라이버의 입출력 처리를 구현하는 함수
static int cis_ioctl(struct inode *inode, struct file *flip, unsigned int cmd, unsigned long arg)
{
	switch(cmd) {
		case SIZE_320240:
			printk("\n\tSIZE 320x240\n");
			view_image_width=320; view_image_height=240;
			view_image_size = view_image_width*view_image_height*2;
			cis_set_val=0x00;
			*sub_sampling = cis_set_val;
			break;
		case SIZE_160120:
			printk("\n\tSIZE 160x120\n");
			view_image_width=160; view_image_height=120;
			view_image_size = view_image_width*view_image_height*2;
			cis_set_val=0x01;
			*sub_sampling = cis_set_val;
			break;
		case MODE_0_FPGA_INPUT:
			printk("\n\tMODE_0_FPGA_INPUT\n");
			*mode_select=0x00;
			break;
		case MODE_1_INPUT:
			printk("\n\tMODE_1_INPUT\n");
			*mode_select=0x01;
			break;
		default:
			return -EINVAL;
	}

	return 0;
}

// 응용 프로그램에서 디바이스를 더이상 사용하지 않아서 닫기를 구현하는 함수
static int cis_release(struct inode *minode, struct file *mfile) 
{
	*flag_ioremap = 0x0;
	*mode_select = 0x00;
	*sub_sampling = 0x00;

	free_irq(IRQ_CMOS,NULL);
	return 0;
}

// 파일 오퍼레이션 구조체
// 파일을 열때 open()을 사용한다. open()는 시스템 콜을 호출하여 커널 내부로 들어간다.
// 해당 시스템 콜과 관련된 파일 연산자 구조체 내부의 open에 해당하는 필드가 드라이버 내에서
// cis_open()으로 정의되어 있으므로 cis_open()가 호출된다.
// read, write, release, ioctl 도 마찬가지로 동작한다. 만약 등록되지 않은 동작에 대해서는 커널에서 정의해
// 놓은 default 동작을 하도록 되어 있다.
static struct file_operations cis_fops = {
	.owner		= THIS_MODULE,
	.read		= cis_read,
	.write		= cis_write,
	.open		= cis_open,
	.release	= cis_release,
	.ioctl          = cis_ioctl,
};

// 모듈을 커널 내부로 삽입
// 모듈 프로그램의 핵심적인 목적은 커널 내부로 들어가서 서비스를 제공받는 것이므로
// 커널 내부로 들어가는 init()을 먼저 시작한다.
// 응용 프로그램은 소스 내부에서 정의되지 않은 많은 함수를 사용한다. 그것은 외부
// 라이브러리가 컴파일 과정에서 링크되어 사용되기 때문이다. 모듈 프로그램은 커널
// 내부하고만 링크되기 때문에 커널에서 정의하고 허용하는 함수만을 사용할 수 있다.
int cis_init(void) 
{
	int result;

	result = register_chrdev(CIS_MAJOR,CIS_NAME,&cis_fops);

	if(result < 0) {
		printk(KERN_WARNING"Can't get major %d\n",CIS_MAJOR);
		return result;
	} 

	/* FPGA MEMORY MAPPING */
	flag_ioremap = ioremap(FLAG_ADDRESS, FLAG_ADDRESS_RANGE);
	if(!check_mem_region((unsigned long)flag_ioremap, FLAG_ADDRESS_RANGE))
		request_mem_region((unsigned long)flag_ioremap, FLAG_ADDRESS_RANGE,CIS_NAME);
	else {
		printk("driver : unable to use the FLAG address\n");
		return 0;
	}

	// DATA Mapping
	data_ioremap = ioremap(DATA_ADDRESS, DATA_ADDRESS_RANGE);
	if(!check_mem_region((unsigned long)data_ioremap, DATA_ADDRESS_RANGE))
		request_mem_region((unsigned long)data_ioremap, DATA_ADDRESS_RANGE,CIS_NAME);
	else {
		printk("driver: unable to use the DATA Address\n");
		return 0;
	}

	//SUB Sampling Memory Mapping
	sub_sampling = ioremap(SUBSAMPLING_ADDRESS,FLAG_ADDRESS_RANGE);
	if(!check_mem_region((unsigned long)sub_sampling, FLAG_ADDRESS_RANGE))
		request_mem_region((unsigned long)sub_sampling, FLAG_ADDRESS_RANGE,CIS_NAME);
	else {
		printk("driver : unable to use the SUBSAMPLING_ADDRESS\n");
		return 0;
	}

	//MODE Memory Mapping
	mode_select = ioremap(MODE_ADDRESS,FLAG_ADDRESS_RANGE);
	if(!check_mem_region((unsigned long)mode_select, FLAG_ADDRESS_RANGE))
		request_mem_region((unsigned long)mode_select, FLAG_ADDRESS_RANGE,CIS_NAME);
	else {
		printk("driver : unable to use the MODE address\n");
		return 0;
	}

	printk("driver: Insert CIS module succeed Major Number:%d\n",CIS_MAJOR);

	return 0;
}

// 모듈을 커널에서 제거
void cis_exit(void)
{
	// 매핑된 가상주소를 해제
	iounmap(flag_ioremap);
	iounmap(data_ioremap);
	iounmap(sub_sampling);
	iounmap(mode_select);

	// 등록된 I/O 메모리 영역을 해제
	release_mem_region((unsigned long)flag_ioremap,FLAG_ADDRESS_RANGE);
	release_mem_region((unsigned long)data_ioremap,DATA_ADDRESS_RANGE);
	release_mem_region((unsigned long)sub_sampling,FLAG_ADDRESS_RANGE);
	release_mem_region((unsigned long)mode_select,FLAG_ADDRESS_RANGE);

	unregister_chrdev(CIS_MAJOR ,CIS_NAME);
	printk("driver: DRIVER EXIT\n");
}

module_init(cis_init);		// 모듈 적재 시 호출되는 함수
module_exit(cis_exit);		// 모듈 제거 시 호출되는 함수

MODULE_AUTHOR(DRIVER_AUTHOR);	 // 모듈의 저작자
MODULE_DESCRIPTION(DRIVER_DESC); // 모듈에 대한 설명
MODULE_LICENSE("Dual BSD/GPL");	 // 모듈의 라이선스 등록

