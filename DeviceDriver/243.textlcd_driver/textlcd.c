/*
 * TextLCD Device Driver
 *  Hanback Electronics Co.,ltd
 * File : textlcd.c
 * Date : January, 2010
 */ 

// 모듈의 헤더파일 선언
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

// 디바이스 드라이버 내부에서 사용하는 함수
// 이 함수에 명령값 command를 인자로 실행하면 TextLCD에 명령이 실행된다.
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

// TextLCD에 문자를 출력하는 함수
// ch에 표시하고자 하는 문자값을 입력해서 이 함수를 실행하면 TextLCD에 나타난다.
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

// TextLCD를 초기화 하는 함수
void initialize_textlcd()
{
	function_set(2,0);	//Function Set:8bit,display 2l ines,5x7 mod
	display_control(1,0,0); // Display on, Cursor off
	clear_display(); 	// Display clear
	entry_mode_set(1,0); 	// Entry Mode Set : shift right cursor
	return_home();		// go home
}

// TextLCD를 두 줄로 표시할지와 폰트 크기를 정하는 함수
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

// TextLCD의 표시방법과 커서를 보이게 할 것인지에 대한 설정함수
int display_control(int display_enable, int cursor_enable, int nblink)
{
        unsigned short command = 0x08;
        command = display_enable ? (command | 0x04) : command;
        command = cursor_enable ? (command | 0x02) : command;
        command = nblink ? (command | 0x01) : command;
        setcommand(command);

        return 1;
}

// 표시된 문자들을 쉬프트하는 함수
int cursor_shift(int set_screen, int set_rightshit)
{
        unsigned short command = 0x10;
        command = set_screen ? (command | 0x08) : command;
        command = set_rightshit ? (command | 0x04) : command;
        setcommand(command);

        return 1;
}

// TextLCD의 모드를 지정하는 함수
int entry_mode_set(int increase, int nshift)
{
        unsigned short command = 0x04;
        command = increase ? (command | 0x2) : command;
        command = nshift ? ( command | 0x1) : command;
        setcommand(command);
        return 1;
}

// TextLCD의 커서를 홈으로 옮기는 함수
int return_home()
{
	unsigned short command = 0x02;
	setcommand(command);
	return 1;
}

// TextLCD의 화면을 지우는 함수
int clear_display()
{
        unsigned short command = 0x01;
        setcommand(command);
        return 1;
}

// TextLCD의 ddram의 주소를 설정하는 함수
int set_ddram_address(int pos)
{
        unsigned short command = 0x80;
        command += pos;
        setcommand(command);
        return 1;
}

// open() 함수를 이용하여 디바이스 드라이버가 열린 경우 호출되는 함수
static int textlcd_open(struct inode *minode, struct file *mfile) 
{
	// 디바이스가 열려 있는지 확인.
        if(textlcd_usage != 0) return -EBUSY;

	// 가상주소 매핑
	textlcd_ioremap= (unsigned long *)ioremap(TEXTLCD_ADDRESS,TEXTLCD_ADDRESS_RANGE);
	
	// 등록할 수 있는 I/O 영역인지 확인
        if(!check_mem_region((unsigned long)textlcd_ioremap,TEXTLCD_ADDRESS_RANGE))
	{
		// I/O 메모리 영역을 등록
	        request_mem_region((unsigned long)textlcd_ioremap,TEXTLCD_ADDRESS_RANGE,TEXTLCD_NAME);
	}
        else printk(KERN_WARNING"driver: Can't get IO Region 0x%x\n",TEXTLCD_ADDRESS);
        
	textlcd_usage = 1;
        initialize_textlcd();
        return 0;
}

// 응용 프로그램에서 디바이스를 더이상 사용하지 않아서 닫기를 구현하는 함수
static int textlcd_release(struct inode *minode, struct file *mfile) 
{
	// 매핑된 가상주소를 해제
        iounmap(textlcd_ioremap);

	// 등록된 I/O 메모리 영역을 해제
        release_mem_region((unsigned long)textlcd_ioremap,TEXTLCD_ADDRESS_RANGE);

	textlcd_usage = 0;
        return 0;
}

// 디바이스 드라이버의 쓰기를 구현하는 함수
static ssize_t textlcd_write(struct file *inode, const char*gdata, size_t length, loff_t *off_what) 
{
	int i,ret;
	char buf[100];

	// 사용자 메모리 gdata를 커널 메모리 buf에 length만큼 복사
	ret=copy_from_user(buf,gdata,length);

	if(ret < 0) return -1;

	for(i=0;i<length;i++) {
		writebyte(buf[i]);
	}
	
	return length;
}

//read()와 write()로 구현하기 곤란한 디바이스 드라이버의 입출력 처리를 구현하는 함수
static int textlcd_ioctl(struct inode *inode, struct file *file,unsigned int cmd,unsigned long gdata) 
{
        struct strcommand_varible strcommand;
	int ret;
	
	// 사용자 메모리 gdata를 커널 메모리 strcommand로 32바이트 복사
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

// 파일 오퍼레이션 구조체
// 파일을 열때 open()을 사용한다. open()는 시스템 콜을 호출하여 커널 내부로 들어간다.
// 해당 시스템 콜과 관련된 파일 연산자 구조체 내부의 open에 해당하는 필드가 드라이버 내에서
// textlcd_open()으로 정의되어 있으므로 textlcd_open()가 호출된다.
// 만약 등록되지 않은 동작에 대해서는 커널에서 정의해 놓은 default 동작을 하도록 되어 있다.
static struct file_operations textlcd_fops = {
	.owner		= THIS_MODULE,
        .open		= textlcd_open,
        .write		= textlcd_write,
        .ioctl		= textlcd_ioctl,
        .release	= textlcd_release,
};

// 모듈을 커널 내부로 삽입
// 모듈 프로그램의 핵심적인 목적은 커널 내부로 들어가서 서비스를 제공받는 것이므로
// 커널 내부로 들어가는 init()을 먼저 시작한다.
// 응용 프로그램은 소스 내부에서 정의되지 않은 많은 함수를 사용한다. 그것은 외부
// 라이브러리가 컴파일 과정에서 링크되어 사용되기 때문이다. 모듈 프로그램은 커널
// 내부하고만 링크되기 때문에 커널에서 정의하고 허용하는 함수만을 사용할 수 있다.
int textlcd_init(void)
{
        int result;

	// 문자 디바이스 드라이버를 등록한다.
        result = register_chrdev(TEXTLCD_MAJOR,TEXTLCD_NAME,&textlcd_fops);

        if(result < 0) {
                printk(KERN_WARNING"driver: Can't get any major\n");
                return result;
        }

	// major 번호를 출력한다.
        printk(KERN_INFO"driver: Init Module, TextLCD Major Number : %d\n",TEXTLCD_MAJOR);
        return 0;
}

// 모듈을 커널에서 제거
void textlcd_exit(void) 
{
	// 문자 디바이스 드라이버를 제거한다.
        unregister_chrdev(TEXTLCD_MAJOR,TEXTLCD_NAME);

	printk("driver: %s DRIVER EXIT\n", TEXTLCD_NAME);	
}

module_init(textlcd_init);	// 모듈 적재 시 호출되는 함수
module_exit(textlcd_exit);	// 모듈 제거 시 호출되는 함수

MODULE_AUTHOR(DRIVER_AUTHOR);	// 모듈의 저작자
MODULE_DESCRIPTION(DRIVER_DESC);// 모듈에 대한 설명
MODULE_LICENSE("Dual BSD/GPL");	// 모듈의 라이선스 등록

