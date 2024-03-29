/*
 * Buzzer Device Driver test Program
 *  Hanback Electronics Co.,ltd
 * File : buz_test.c
 * Date : January, 2010
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

// argc : 외부입력 단어 개수, argv: 외부입력 데이터
int main(int argc, char **argv)
{
	int dev;

	char buff;

	//외부입력 단어수가 2개가 아니라면 메세지 출력후 종료
	if(argc <= 1) {
		printf("application: please input the parameter! ex)./buz_test 1(0)\n");
		exit(1);
	}

	// /dev/buzzer 파일을 쓰기 전용 액세스 모드로 열어
	// dev 변수에 파일 기술자를 저장한다.
	dev = open("/dev/buzzer", O_WRONLY);

	if (dev != -1) {        // 파일을 성공적으로 열었을 때
		// 외부입력 단어 문자열을 16진수인지 확인후 정수형으로 변환
		// strtol() : 특정 진수 형태의 문자열을 정수형으로 변환
		if(argv[1][0] == '0' && (argv[1][1] == 'x' || argv[1][1] == 'X'))
			buff  = (unsigned short)strtol(&argv[1][2],NULL,16);
		else
			buff = atoi(argv[1]);

		// 드라이버 내의 buzzer_write_byte 가 실행
		write(dev,&buff,2);

		// 파일을 닫는다
		close(dev);
	}
	else {  // 파일 열기를 실패 했을 때
		printf("application: Device Open ERROR!\n");
		return -1;
	}

	return 0;
}


