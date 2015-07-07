/*
 * DipSW Device Driver
 *  Hanback Electronics Co.,ltd
 * File : dipsw.c
 * Date : January, 2010
 */ 

#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/errno.h>
#include <linux/types.h>
#include <asm/fcntl.h>
#include <linux/ioport.h>

#include <asm/ioctl.h>
#include <asm/uaccess.h>
#include <asm/io.h>

#define DRIVER_AUTHOR        	"Hanback Electronics"	// ����� ������
#define DRIVER_DESC		"DipSW Test Program"	// ��⿡ ���� ����

#define	DIP_MAJOR_NUMBER	246			// ����̽� �ֹ�ȣ
#define	DIP_NAME		"DIPSW"			// ����̽� �̸�
#define DIP_MODULE_VERSION	"DIPSW V0.1"		// ����̽� ����

#define ADDR_DIP_PHY		0x14803000		// dipsw�� �����ּ�
#define DIP_ADDRESS_RANGE	0x2000			// I/O ������ ũ��

//Global variable
static unsigned int dipsw_usage = 0;
static unsigned long dipsw_ioremap;
static unsigned char *dipswS6, *dipswS19;
static unsigned char value[4];

// define functions...
// ���� ���α׷����� ����̽��� ó�� ����ϴ� ��츦 ó���ϴ� �Լ�
int dipsw_open (struct inode *inode, struct file *filp)
{
	// ����̽��� ���� �ִ��� Ȯ��.
	if(dipsw_usage != 0) return -EBUSY;

	// dipsw�� ���� �ּ� ����
	dipsw_ioremap = (unsigned long)ioremap(ADDR_DIP_PHY, DIP_ADDRESS_RANGE);

	// ����� �� �ִ� I/O �������� Ȯ��
	if(!check_mem_region(dipsw_ioremap,DIP_ADDRESS_RANGE)) {
		// I/O �޸� ������ ���
		request_mem_region(dipsw_ioremap, DIP_ADDRESS_RANGE, DIP_NAME);
	}
	else	printk("driver: unable to register 0x%x\n", (unsigned int)dipsw_ioremap);
	
	// dipsw �� ù��°�ٰ� �ι�°�ٿ� ���� �ּ� ����
	dipswS6 =(unsigned char*)dipsw_ioremap;
	dipswS19 =(unsigned char*)(dipsw_ioremap+0x1000);
	
	dipsw_usage = 1;
	return 0;
}

// ���� ���α׷����� ����̽��� ���̻� ������� �ʾƼ� �ݱ⸦ �����ϴ� �Լ�
int dipsw_release (struct inode *inode, struct file *filp)
{
	// ���ε� �����ּҸ� ����
	iounmap((unsigned long*)dipsw_ioremap);

	// ��ϵ� I/O �޸� ������ ����
	release_mem_region(dipsw_ioremap, DIP_ADDRESS_RANGE);
	dipsw_usage = 0;
	return 0;
}

// ����̽� ����̹��� �б⸦ �����ϴ� �Լ�
ssize_t dipsw_read(struct file *inode, char *gdata, size_t length, loff_t *off_what)
{
	int ret;

	//*dipaddr0=0x01;	// dipsw�� ù��° 
	//value[0]=*(dipaddr1);

	//*dipaddr0=0x00;	// dipsw�� �ι�°
	//value[1]=*(dipaddr1);
	
	value[0] = (*(dipswS6) & 0x0f);
	value[1] = (*(dipswS6) >> 4);
	value[2] = (*(dipswS19) & 0x0f);
	value[3] = (*(dipswS19) >> 4);

	// value�� ����Ű�� Ŀ�� �޸� �����͸� gdata�� ����Ű�� �����
	// �޸� �����Ϳ� n ����Ʈ ��ŭ ��ִ´�.
	ret=copy_to_user(gdata,value,4);
	if(ret<0) return -1;

	return length;
}

// ���� ���۷��̼� ����ü
// ������ ���� open()�� ����Ѵ�. open()�� �ý��� ���� ȣ���Ͽ� Ŀ�� ���η� ����.
// �ش� �ý��� �ݰ� ���õ� ���� ������ ����ü ������ open�� �ش��ϴ� �ʵ尡 ����̹� ������
// dipsw_open()���� ���ǵǾ� �����Ƿ� dipsw_open()�� ȣ��ȴ�.
// read, release�� ���������� �����Ѵ�.
struct file_operations dipsw_fops = 
{
	.owner		= THIS_MODULE,
	.open		= dipsw_open,
	.read		= dipsw_read,
	.release	= dipsw_release,
};

// ����� Ŀ�� ���η� ����
// ��� ���α׷��� �ٽ����� ������ Ŀ�� ���η� ���� ���񽺸� �����޴� ���̹Ƿ�
// Ŀ�� ���η� ���� init()�� ���� �����Ѵ�.
// ���� ���α׷��� �ҽ� ���ο��� ���ǵ��� ���� ���� �Լ��� ����Ѵ�. �װ��� �ܺ�
// ���̺귯���� ������ �������� ��ũ�Ǿ� ���Ǳ� �����̴�. ��� ���α׷��� Ŀ��
// �����ϰ� ��ũ�Ǳ� ������ Ŀ�ο��� �����ϰ� ����ϴ� �Լ����� ����� �� �ִ�.
int dipsw_init(void)
{
	int result;

	// ���� ����̽� ����̹��� ����Ѵ�.
	result = register_chrdev(DIP_MAJOR_NUMBER, DIP_NAME, &dipsw_fops);
	if (result < 0) {  // ��Ͻ���
		printk(KERN_WARNING"driver: Can't get any major\n");
		return result;
	}
	
	// major ��ȣ�� ����Ѵ�.
	printk("driver: Init Module, Dipsw Major Number : %d\n", DIP_MAJOR_NUMBER);
	return 0;
}

// ����� Ŀ�ο��� ����	
void dipsw_exit(void)
{
	// ���� ����̽� ����̹��� �����Ѵ�.
	unregister_chrdev(DIP_MAJOR_NUMBER,DIP_NAME);

	printk("driver: %s DRIVER EXIT\n", DIP_NAME);
}

module_init(dipsw_init);	// ��� ���� �� ȣ��Ǵ� �Լ�
module_exit(dipsw_exit);	// ��� ���� �� ȣ��Ǵ� �Լ�

MODULE_AUTHOR(DRIVER_AUTHOR);	// ����� ������
MODULE_DESCRIPTION(DRIVER_DESC); // ��⿡ ���� ����
MODULE_LICENSE("Dual BSD/GPL");	 // ����� ���̼��� ���

