/*
 * 7-Segment Device Driver
 *  Hanback Electronics Co.,ltd
 * File : segment.c
 * Date : January, 2010
 */ 

// ����� ������� ����
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/types.h>
#include <linux/ioport.h>
#include <linux/errno.h>
#include <linux/delay.h>

#include <asm/io.h>
#include <asm/ioctl.h>
#include <asm/uaccess.h>
#include <asm/fcntl.h>

#define DRIVER_AUTHOR			"Hanback Electronics"	// ����� ������
#define DRIVER_DESC			"7-Segment Program"
#define	SEGMENT_MAJOR			242			// ����̽� �ֹ�ȣ
#define	SEGMENT_NAME			"7-SEGMENT"		// ����̽� �̸�
#define SEGMENT_MODULE_VERSION		"7-SEGMENT PORT V0.1"	// ����̽� ����

#define SEGMENT_ADDRESS_GRID		0x14800000	// 7-seg�� Digit �� �����ϱ� ���� �������� 
#define SEGMENT_ADDRESS_DATA		0x14801000	// 7-seg�� ���÷��� �ϱ� ���� ��������
#define SEGMENT_ADDRESS_RANGE		0x1000		// I/O ������ ũ��

//Global variable
static unsigned int segment_usage = 0;
static unsigned char *segment_data;
static unsigned char *segment_grid;

// define functions...
// ���� ���α׷����� ����̽��� ó�� ����ϴ� ��츦 ó���ϴ� �Լ�
int segment_open (struct inode *inode, struct file *filp)
{
	// ����̽��� ���� �ִ��� Ȯ��.
	if(segment_usage != 0) return -EBUSY;
	
	// GRID�� DATA�� ���� �ּ� ����
	segment_grid =  ioremap(SEGMENT_ADDRESS_GRID, SEGMENT_ADDRESS_RANGE);
	segment_data =  ioremap(SEGMENT_ADDRESS_DATA, SEGMENT_ADDRESS_RANGE);
	
	// ����� �� �ִ� I/O �������� Ȯ��
	if(!check_mem_region((unsigned long)segment_data,SEGMENT_ADDRESS_RANGE) && !check_mem_region((unsigned long)segment_grid, SEGMENT_ADDRESS_RANGE))
	{
		// I/O �޸� ������ ���
		request_region((unsigned long)segment_grid, SEGMENT_ADDRESS_RANGE, SEGMENT_NAME);
		request_region((unsigned long)segment_data, SEGMENT_ADDRESS_RANGE, SEGMENT_NAME);
	}
	else printk("driver: unable to register this!\n");

	segment_usage = 1;	
	return 0; 

}

// ���� ���α׷����� ����̽��� ���̻� ������� �ʾƼ� �ݱ⸦ �����ϴ� �Լ�
int segment_release (struct inode *inode, struct file *filp)
{
	// ���ε� �����ּҸ� ����
	iounmap(segment_grid);
	iounmap(segment_data);

	// ��ϵ� I/O �޸� ������ ����
	release_region((unsigned long)segment_data, SEGMENT_ADDRESS_RANGE);
	release_region((unsigned long)segment_grid, SEGMENT_ADDRESS_RANGE);

	segment_usage = 0;
	return 0;
}


// x�� ���� LED�� �ڵ�� ��ȯ�Ͽ� ��ȯ
unsigned char Getsegmentcode (char x)
{
	unsigned char code;
	switch (x) {
		case 0x0 : code = 0x3f; break;
		case 0x1 : code = 0x06; break;
		case 0x2 : code = 0x5b; break;
		case 0x3 : code = 0x4f; break;
		case 0x4 : code = 0x66; break;
		case 0x5 : code = 0x6d; break;
		case 0x6 : code = 0x7d; break;
		case 0x7 : code = 0x07; break;
		case 0x8 : code = 0x7f; break;										
		case 0x9 : code = 0x6f; break;		
		case 0xA : code = 0x77; break;
		case 0xB : code = 0x7c; break;
		case 0xC : code = 0x39; break;
		case 0xD : code = 0x5e; break;						
		case 0xE : code = 0x79; break;
		case 0xF : code = 0x71; break;				
		default : code = 0; break;
	}
	return code;
}						

// ����̽� ����̹��� ���⸦ �����ϴ� �Լ�
ssize_t segment_write(struct file *inode, const char *gdata, size_t length, loff_t *off_what)
{

	unsigned char data[6];
	unsigned char digit[6]={0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
	unsigned int i,j,num,ret;
	unsigned int temp1,temp2;

	// ����� �޸� gdata�� Ŀ�� �޸� num���� n��ŭ ���� 
	ret = copy_from_user(&num,gdata,4);
	//if (num/100000>0) data[5]=0x40;
	//else data[5]=0x00;
		//Section Number
		data[5]=Getsegmentcode(num/100000);
		temp1=num%100000;

		//�µ� ��ȣ (100�������� ǥ��)
		if (temp1/10000>1) data[4]=0x40;
		else if (temp1/10000==1) data[4]=0x06;
		else data[4]=0x00;
		temp2=temp1%10000;

		//�µ�, ������
		data[3]=Getsegmentcode(temp2/1000);
		temp1=temp2%1000;
		data[2]=Getsegmentcode(temp1/100)+0x80;
		temp2=temp1%100;

		//����
		data[1]=Getsegmentcode(temp2/10);
		data[0]=Getsegmentcode(temp2%10);

		//ǥ��
		for(j=0;j<150;j++) 
		{
			for(i=0;i<6;i++) 
			{ 
				*segment_grid = ~digit[i];
				*segment_data = data[i];
				mdelay(1);
			}
		}

	*segment_grid = 0x00;
	*segment_data = 0;

	return length;
}

// ���� ���۷��̼� ����ü
// ������ ���� open()�� ����Ѵ�. open()�� �ý��� ���� ȣ���Ͽ� Ŀ�� ���η� ����.
// �ش� �ý��� �ݰ� ���õ� ���� ������ ����ü ������ open�� �ش��ϴ� �ʵ尡 ����̹� ������
// segment_open()���� ���ǵǾ� �����Ƿ� segment_open()�� ȣ��ȴ�.
// write�� release�� ���������� �����Ѵ�. ���� ��ϵ��� ���� ���ۿ� ���ؼ��� Ŀ�ο��� ������
// ���� default ������ �ϵ��� �Ǿ� �ִ�.
struct file_operations segment_fops = 
{
	.owner		= THIS_MODULE,
	.open		= segment_open,
	.write		= segment_write,
	.release	= segment_release,
};

// ����� Ŀ�� ���η� ����
// ��� ���α׷��� �ٽ����� ������ Ŀ�� ���η� ���� ���񽺸� �����޴� ���̹Ƿ�
// Ŀ�� ���η� ���� init()�� ���� �����Ѵ�.
// ���� ���α׷��� �ҽ� ���ο��� ���ǵ��� ���� ���� �Լ��� ����Ѵ�. �װ��� �ܺ�
// ���̺귯���� ������ �������� ��ũ�Ǿ� ���Ǳ� �����̴�. ��� ���α׷��� Ŀ��
// �����ϰ��� ��ũ�Ǳ� ������ Ŀ�ο��� �����ϰ� ����ϴ� �Լ����� ����� �� �ִ�.
int segment_init(void)
{
	int result;

	// ���� ����̽� ����̹��� ����Ѵ�.
	result = register_chrdev(SEGMENT_MAJOR, SEGMENT_NAME, &segment_fops);

	if (result < 0) {
		printk(KERN_WARNING"driver: Can't get any major\n");
		return result;
	}

	// major ��ȣ�� ����Ѵ�.
	printk(KERN_INFO"driver: Init Module, 7-Segment Major Number : %d\n", SEGMENT_MAJOR);
	return 0;
}
		
// ����� Ŀ�ο��� ����
void segment_exit(void)
{
	// ���� ����̽� ����̹��� �����Ѵ�.
	unregister_chrdev(SEGMENT_MAJOR,SEGMENT_NAME);

	printk("driver: %s DRIVER EXIT\n", SEGMENT_NAME);
}

module_init(segment_init);	// ��� ���� �� ȣ��Ǵ� �Լ�
module_exit(segment_exit);	// ��� ���� �� ȣ��Ǵ� �Լ�

MODULE_AUTHOR(DRIVER_AUTHOR);	// ����� ������
MODULE_DESCRIPTION(DRIVER_DESC);// ��⿡ ���� ����
MODULE_LICENSE("Dual BSD/GPL");	// ����� ���̼��� ���

