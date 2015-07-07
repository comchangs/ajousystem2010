/*
 * TextLCD Device Driver
 *  Hanback Electronics Co.,ltd
 * File : textlcd.h
 * Date : April,2009
 */ 

#define DRIVER_AUTHOR           "hanback"		// 모듈의 저작자
#define DRIVER_DESC             "textlcd test program"	// 모듈에 대한 설명

#define TEXTLCD_MAJOR   	243			// 디바이스 주번호
#define TEXTLCD_NAME   		"TEXT LCD PORT"		// 디바이스 이름
#define TEXTLCD_MODULE_VERSION  "TEXT LCD PORT V0.1"	// 디바이스 버전
#define TEXTLCD_ADDRESS 	0x14809000  //Physical Address
#define TEXTLCD_ADDRESS_RANGE	0x1000			// I/O 영역의 크기

#define TEXTLCD_BASE		0xbc
#define TEXTLCD_COMMAND_SET     _IOW(TEXTLCD_BASE,0,int)
#define TEXTLCD_FUNCTION_SET    _IOW(TEXTLCD_BASE,1,int)
#define TEXTLCD_DISPLAY_CONTROL _IOW(TEXTLCD_BASE,2,int)
#define TEXTLCD_CURSOR_SHIFT    _IOW(TEXTLCD_BASE,3,int)
#define TEXTLCD_ENTRY_MODE_SET  _IOW(TEXTLCD_BASE,4,int)
#define TEXTLCD_RETURN_HOME     _IOW(TEXTLCD_BASE,5,int)
#define TEXTLCD_CLEAR           _IOW(TEXTLCD_BASE,6,int)
#define TEXTLCD_DD_ADDRESS	_IOW(TEXTLCD_BASE,7,int)
#define TEXTLCD_WRITE_BYTE      _IOW(TEXTLCD_BASE,8,int)

//Global variable
static int textlcd_usage = 0;
static unsigned long *textlcd_ioremap;

// TextLCD에 표시되는 값들에 관한 정보 저장 구조체
struct strcommand_varible {
	char rows;
	char nfonts;
	char display_enable;
	char cursor_enable;
	
	char nblink;
	char set_screen;
	char set_rightshit;
	char increase;
	char nshift;
	char pos;
	char command;
	char strlength;
	char buf[16];
};

// functions...
void setcommand(unsigned short command);
void writebyte(char ch);
void initialize_textlcd(void);
int function_set(int rows, int nfonts);
int display_control(int display_enable, int cursor_enable, int nblink);
int cusrsor_shit(int set_screen, int set_rightshit);
int entry_mode_set(int increase, int nshift);
int return_home(void);
int clear_display(void);
int set_ddram_address(int pos);
