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

// argc : �ܺ��Է� �ܾ� ����, argv: �ܺ��Է� ������
int main(int argc, char **argv)
{
	int dev;

	char buff;

	//�ܺ��Է� �ܾ���� 2���� �ƴ϶�� �޼��� ����� ����
	if(argc <= 1) {
		printf("application: please input the parameter! ex)./buz_test 1(0)\n");
		exit(1);
	}

	// /dev/buzzer ������ ���� ���� �׼��� ���� ����
	// dev ������ ���� ����ڸ� �����Ѵ�.
	dev = open("/dev/buzzer", O_WRONLY);

	if (dev != -1) {        // ������ ���������� ������ ��
		// �ܺ��Է� �ܾ� ���ڿ��� 16�������� Ȯ���� ���������� ��ȯ
		// strtol() : Ư�� ���� ������ ���ڿ��� ���������� ��ȯ
		if(argv[1][0] == '0' && (argv[1][1] == 'x' || argv[1][1] == 'X'))
			buff  = (unsigned short)strtol(&argv[1][2],NULL,16);
		else
			buff = atoi(argv[1]);

		// ����̹� ���� buzzer_write_byte �� ����
		write(dev,&buff,2);

		// ������ �ݴ´�
		close(dev);
	}
	else {  // ���� ���⸦ ���� ���� ��
		printf("application: Device Open ERROR!\n");
		return -1;
	}

	return 0;
}


