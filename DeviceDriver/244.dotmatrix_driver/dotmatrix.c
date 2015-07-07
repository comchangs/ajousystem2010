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

#define DRIVER_AUTHOR			"Hanback Electronics"	// ����� ������
#define DRIVER_DESC			"Dotmatrix Test Program"// ��⿡ ���� ����
#define DOT_MAJOR			244			// ����̽� �ֹ�ȣ
#define DOT_NAME 			"DOT"			// ����̽� �̸�
#define DOT_MODULE_VERSION 		"DOT V0.1"		// ����̽� ����
#define DOT_PHY_ADDR			0x1480A000		// buzzer�� ���� �ּ�
#define DOT_ADDR_RANGE 			0x3000			// I/O ������ ũ��
#define NUMSIZE				4

//Global variable
static int dot_usage = 0;		// ����̹� ��뿩�θ� Ȯ���ϴ� ��
static unsigned long dot_ioremap;	// IO �ּ� ���� ����
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
// ���� ���α׷����� ����̽��� ó�� ����ϴ� ��츦 ó���ϴ� �Լ�
int dot_open(struct inode *minode, struct file *mfile) 
{
	// ����̽��� ���� �ִ��� Ȯ��.
	if(dot_usage != 0) return -EBUSY;
	
	// dot�� ���� �ּ� ����
	dot_ioremap = (unsigned long)ioremap(DOT_PHY_ADDR,DOT_ADDR_RANGE);

	// ����� �� �ִ� I/O �������� Ȯ��
	if(!check_mem_region(dot_ioremap, DOT_ADDR_RANGE))
		// I/O �޸� ������ ���
		request_mem_region(dot_ioremap, DOT_ADDR_RANGE, DOT_NAME);
	else	printk("driver: unable to register this!\n");
	
	// row, col�� �ּ� ����
	dot_col_addr = (unsigned short *)dot_ioremap;
	dot_row_addr = (unsigned short *)(dot_ioremap+0x2000);

	dot_usage = 1;
	return 0;
}

// ���� ���α׷����� ����̽��� ���̻� ������� �ʾƼ� �ݱ⸦ �����ϴ� �Լ�
int dot_release(struct inode *minode, struct file *mfile) 
{
	// ���ε� �����ּҸ� ����
	iounmap((unsigned long *)dot_ioremap);

	// ��ϵ� I/O �޸� ������ ����
	release_mem_region(dot_ioremap, DOT_ADDR_RANGE);
	dot_usage = 0;
	return 0;
}

// ����̽� ����̹��� ���⸦ �����ϴ� �Լ�
ssize_t dot_write(struct file *inode, const char *gdata, size_t length, loff_t *off_what) 
{
	volatile int i,j,k;
	int size=0,cnt=0,ret, up_digit=0,quit=1,counter;
	unsigned int second_data, dispdata[8];
	unsigned int init_col1=0x100, init_col2=0x008; // Scan value

	// ����� �޸� gdata�� Ŀ�� �޸� counter�� n��ŭ ����
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

// ���� ���۷��̼� ����ü
// ������ ���� open()�� ����Ѵ�. open()�� �ý��� ���� ȣ���Ͽ� Ŀ�� ���η� ����.
// �ش� �ý��� �ݰ� ���õ� ���� ������ ����ü ������ open�� �ش��ϴ� �ʵ尡 ����̹� ������
// dot_open()���� ���ǵǾ� �����Ƿ� dot_open()�� ȣ��ȴ�.
// write�� release�� ���������� �����Ѵ�. ���� ��ϵ��� ���� ���ۿ� ���ؼ��� Ŀ�ο��� ������
// ���� default ������ �ϵ��� �Ǿ� �ִ�.
struct file_operations dot_fops = {
	.owner		= THIS_MODULE,
	.write		= dot_write,
	.open		= dot_open,
	.release	= dot_release,
};

// ����� Ŀ�� ���η� ����
// ��� ���α׷��� �ٽ����� ������ Ŀ�� ���η� ���� ���񽺸� �����޴� ���̹Ƿ�
// Ŀ�� ���η� ���� init()�� ���� �����Ѵ�.
// ���� ���α׷��� �ҽ� ���ο��� ���ǵ��� ���� ���� �Լ��� ����Ѵ�. �װ��� �ܺ�
// ���̺귯���� ������ �������� ��ũ�Ǿ� ���Ǳ� �����̴�. ��� ���α׷��� Ŀ��
// �����ϰ� ��ũ�Ǳ� ������ Ŀ�ο��� �����ϰ� ����ϴ� �Լ����� ����� �� �ִ�.
int dot_init(void) 
{
	int result;

	// ���� ����̽� ����̹��� ����Ѵ�.
	result = register_chrdev(DOT_MAJOR, DOT_NAME, &dot_fops);
	
	if(result < 0) {	// ��Ͻ���
		printk(KERN_WARNING"driver: Can't get any major\n");
		return result;
	}
	
	// major ��ȣ�� ����Ѵ�.
	printk("driver: Init Module, Dotmatrix Major Number : %d\n", DOT_MAJOR);

	return 0;
}

// ����� Ŀ�ο��� ����
void dot_exit(void) 
{
	// ���� ����̽� ����̹��� �����Ѵ�.
	unregister_chrdev(DOT_MAJOR, DOT_NAME);

	printk("driver: %s DRIVER EXIT\n", DOT_NAME);
}

module_init(dot_init);		// ��� ���� �� ȣ��Ǵ� �Լ�
module_exit(dot_exit);		// ��� ���� �� ȣ��Ǵ� �Լ�

MODULE_AUTHOR(DRIVER_AUTHOR); 	 // ����� ������
MODULE_DESCRIPTION(DRIVER_DESC); // ��⿡ ���� ����
MODULE_LICENSE("Dual BSD/GPL");	 // ����� ���̼��� ���
