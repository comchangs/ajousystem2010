
#define DATA_TIME 1000//Default 3000 ms <- modified
  // Data Frame Structure for RF transmission //
  typedef struct {
	uint16_t Temp;
	uint16_t Humi;
	uint16_t Photo;
	uint16_t Ultrared;
	uint16_t group;			//그룹 정보
  } __attribute__ ((packed)) DataFrameStruct;
  //////////////////////////////

  // Data Frame Structure for UART transmission //
  typedef struct {
	uint8_t  addr[2];
	uint8_t  type;
	uint8_t  group;
	uint8_t  length; // 32bytes
	uint16_t src;
	uint16_t dst1;
	uint16_t dst2;
	uint16_t dst3;
	uint32_t seq;
	uint16_t Temp;
	uint16_t Humi;
	uint16_t Photo;
	uint16_t Ultrared;
	uint32_t Totalduration;
	uint32_t TXduration;
	uint32_t Sleepduration;
	uint16_t CRC;
  } __attribute__ ((packed)) UartFrameStruct;
  //////////////////////////////

enum {
  AM_TREEMSG = 8
};

