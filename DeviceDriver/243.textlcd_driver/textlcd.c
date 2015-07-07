/*
 * TextLCD Device Driver
 *  Hanback Electronics Co.,ltd
 * File : textlcd.c
 * Date : January, 2010
 */ 

// ����� ������� ����
#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/errno.h>
#include <linux/types.h>
#include <linux/ioport.h>
#include <asm/io.h>
#include <asm/ioctl.h>
#include <asm/uaccess.h>
#include <linux/delay.h>

#include "textlcd.h"

// ����̽� ����̹� ���ο��� ����ϴ� �Լ�
// �� �Լ��� ��ɰ� command�� ���ڷ� �����ϸ� TextLCD�� ����� ����ȴ�.
void setcommand(unsigned short command)
{
	command &= 0x00FF;

	*textlcd_ioremap = command | 0x0000;
	mdelay(1);
	*textlcd_ioremap = command | 0x0100;
	mdelay(1);
	*textlcd_ioremap = command | 0x0000;
	mdelay(1);
}

// TextLCD�� ���ڸ� ����ϴ� �Լ�
// ch�� ǥ���ϰ��� �ϴ� ���ڰ��� �Է��ؼ� �� �Լ��� �����ϸ� TextLCD�� ��Ÿ����.
void writebyte(char ch)
{
        unsigned short data;
        data = ch & 0x00FF;

	*textlcd_ioremap = data & 0x400;
	mdelay(1);
	*textlcd_ioremap = data | 0x500;
	mdelay(1);
	*textlcd_ioremap = data | 0x400;
	mdelay(5);
}

// TextLCD�� �ʱ�ȭ �ϴ� �Լ�
void initialize_textlcd()
{
	function_set(2,0);	//Function Set:8bit,display 2l ines,5x7 mod
	display_control(1,0,0); // Display on, Cursor off
	clear_display(); 	// Display clear
	entry_mode_set(1,0); 	// Entry Mode Set : shift right cursor
	return_home();		// go home
}

// TextLCD�� �� �ٷ� ǥ�������� ��Ʈ ũ�⸦ ���ϴ� �Լ�
int function_set(int rows, int nfonts)
{
        unsigned short command = 0x30;

        if(rows == 2) command |= 0x08;
        else if(rows == 1) command &= 0xf7;
        else return -1;

        command = nfonts ? (command | 0x04) : command;
        setcommand(command);

        return 1;
}

// TextLCD�� ǥ�ù���� Ŀ���� ���̰� �� �������� ���� �����Լ�
int display_control(int display_enable, int cursor_enable, int nblink)
{
        unsigned short command = 0x08;
        command = display_enable ? (command | 0x04) : command;
        command = cursor_enable ? (command | 0x02) : command;
        command = nblink ? (command | 0x01) : command;
        setcommand(command);

        return 1;
}

// ǥ�õ� ���ڵ��� ����Ʈ�ϴ� �Լ�
int cursor_shift(int set_screen, int set_rightshit)
{
        unsigned short command = 0x10;
        command = set_screen ? (command | 0x08) : command;
        command = set_rightshit ? (command | 0x04) : command;
        setcommand(command);

        return 1;
}

// TextLCD�� ��带 �����ϴ� �Լ�
int entry_mode_set(int increase, int nshift)
{
        unsigned short command = 0x04;
        command = increase ? (command | 0x2) : command;
        command = nshift ? ( command | 0x1) : command;
        setcommand(command);
        return 1;
}

// TextLCD�� Ŀ���� Ȩ���� �ű�� �Լ�
int return_home()
{
	unsigned short command = 0x02;
	setcommand(command);
	return 1;
}

// TextLCD�� ȭ���� ����� �Լ�
int clear_display()
{
        unsigned short command = 0x01;
        setcommand(command);
        return 1;
}

// TextLCD�� ddram�� �ּҸ� �����ϴ� �Լ�
int set_ddram_address(int pos)
{
        unsigned short command = 0x80;
        command += pos;
        setcommand(command);
        return 1;
}

// open() �Լ��� �̿��Ͽ� ����̽� ����̹��� ���� ��� ȣ��Ǵ� �Լ�
static int textlcd_open(struct inode *minode, struct file *mfile) 
{
	// ����̽��� ���� �ִ��� Ȯ��.
        if(textlcd_usage != 0) return -EBUSY;

	// �����ּ� ����
	textlcd_ioremap= (unsigned long *)ioremap(TEXTLCD_ADDRESS,TEXTLCD_ADDRESS_RANGE);
	
	// ����� �� �ִ� I/O �������� Ȯ��
        if(!check_mem_region((unsigned long)textlcd_ioremap,TEXTLCD_ADDRESS_RANGE))
	{
		// I/O �޸� ������ ���
	        request_mem_region((unsigned long)textlcd_ioremap,TEXTLCD_ADDRESS_RANGE,TEXTLCD_NAME);
	}
        else printk(KERN_WARNING"driver: Can't get IO Region 0x%x\n",TEXTLCD_ADDRESS);
        
	textlcd_usage = 1;
        initialize_textlcd();
        return 0;
}

// ���� ���α׷����� ����̽��� ���̻� ������� �ʾƼ� �ݱ⸦ �����ϴ� �Լ�
static int textlcd_release(struct inode *minode, struct file *mfile) 
{
	// ���ε� �����ּҸ� ����
        iounmap(textlcd_ioremap);

	// ��ϵ� I/O �޸� ������ ����
        release_mem_region((unsigned long)textlcd_ioremap,TEXTLCD_ADDRESS_RANGE);

	textlcd_usage = 0;
        return 0;
}

// ����̽� ����̹��� ���⸦ �����ϴ� �Լ�
static ssize_t textlcd_write(struct file *inode, const char*gdata, size_t length, loff_t *off_what) 
{
	int i,ret;
	char buf[100];

	// ����� �޸� gdata�� Ŀ�� �޸� buf�� length��ŭ ����
	ret=copy_from_user(buf,gdata,length);

	if(ret < 0) return -1;

	for(i=0;i<length;i++) {
		writebyte(buf[i]);
	}
	
	return length;
}

//read()�� write()�� �����ϱ� ����� ����̽� ����̹��� ����� ó���� �����ϴ� �Լ�
static int textlcd_ioctl(struct inode *inode, struct file *file,unsigned int cmd,unsigned long gdata) 
{
        struct strcommand_varible strcommand;
	int ret;
	
	// ����� �޸� gdata�� Ŀ�� �޸� strcommand�� 32����Ʈ ����
	ret=copy_from_user(&strcommand,(char *)gdata,32);
	if(ret<0) return -1;
	
        switch(cmd){
        case TEXTLCD_COMMAND_SET:
                setcommand(strcommand.command);
                break;
        case TEXTLCD_FUNCTION_SET:
                function_set((int)(strcommand.rows+1),(int)(strcommand.nfonts));
                break;
        case TEXTLCD_DISPLAY_CONTROL:
                display_control((int)strcommand.display_enable,
		(int)strcommand.cursor_enable,(int)strcommand.nblink);
                break;
        case TEXTLCD_CURSOR_SHIFT:
                cursor_shift((int)strcommand.set_screen,(int)strcommand.set_rightshit);
                break;
        case TEXTLCD_ENTRY_MODE_SET:
		entry_mode_set((int)strcommand.increase,(int)strcommand.nshift);
                break;
        case TEXTLCD_RETURN_HOME:
                return_home();
                break;
        case TEXTLCD_CLEAR:
                clear_display();
                break;
        case TEXTLCD_DD_ADDRESS:
                set_ddram_address((int)strcommand.pos);
                break;
        case TEXTLCD_WRITE_BYTE:
                writebyte(strcommand.buf[0]);
                break;
        default:
                printk("driver: no such command!\n");
                return -ENOTTY;
        }
        return 0;
}

// ���� ���۷��̼� ����ü
// ������ ���� open()�� ����Ѵ�. open()�� �ý��� ���� ȣ���Ͽ� Ŀ�� ���η� ����.
// �ش� �ý��� �ݰ� ���õ� ���� ������ ����ü ������ open�� �ش��ϴ� �ʵ尡 ����̹� ������
// textlcd_open()���� ���ǵǾ� �����Ƿ� textlcd_open()�� ȣ��ȴ�.
// ���� ��ϵ��� ���� ���ۿ� ���ؼ��� Ŀ�ο��� ������ ���� default ������ �ϵ��� �Ǿ� �ִ�.
static struct file_operations textlcd_fops = {
	.owner		= THIS_MODULE,
        .open		= textlcd_open,
        .write		= textlcd_write,
        .ioctl		= textlcd_ioctl,
        .release	= textlcd_release,
};

// ����� Ŀ�� ���η� ����
// ��� ���α׷��� �ٽ����� ������ Ŀ�� ���η� ���� ���񽺸� �����޴� ���̹Ƿ�
// Ŀ�� ���η� ���� init()�� ���� �����Ѵ�.
// ���� ���α׷��� �ҽ� ���ο��� ���ǵ��� ���� ���� �Լ��� ����Ѵ�. �װ��� �ܺ�
// ���̺귯���� ������ �������� ��ũ�Ǿ� ���Ǳ� �����̴�. ��� ���α׷��� Ŀ��
// �����ϰ� ��ũ�Ǳ� ������ Ŀ�ο��� �����ϰ� ����ϴ� �Լ����� ����� �� �ִ�.
int textlcd_init(void)
{
        int result;

	// ���� ����̽� ����̹��� ����Ѵ�.
        result = register_chrdev(TEXTLCD_MAJOR,TEXTLCD_NAME,&textlcd_fops);

        if(result < 0) {
                printk(KERN_WARNING"driver: Can't get any major\n");
                return result;
        }

	// major ��ȣ�� ����Ѵ�.
        printk(KERN_INFO"driver: Init Module, TextLCD Major Number : %d\n",TEXTLCD_MAJOR);
        return 0;
}

// ����� Ŀ�ο��� ����
void textlcd_exit(void) 
{
	// ���� ����̽� ����̹��� �����Ѵ�.
        unregister_chrdev(TEXTLCD_MAJOR,TEXTLCD_NAME);

	printk("driver: %s DRIVER EXIT\n", TEXTLCD_NAME);	
}

module_init(textlcd_init);	// ��� ���� �� ȣ��Ǵ� �Լ�
module_exit(textlcd_exit);	// ��� ���� �� ȣ��Ǵ� �Լ�

MODULE_AUTHOR(DRIVER_AUTHOR);	// ����� ������
MODULE_DESCRIPTION(DRIVER_DESC);// ��⿡ ���� ����
MODULE_LICENSE("Dual BSD/GPL");	// ����� ���̼��� ���

