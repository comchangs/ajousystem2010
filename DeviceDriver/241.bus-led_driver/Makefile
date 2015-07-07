#
# Makefile for LED Device Driver 
# Hanback Electronics Co.,ltd
# Date : January, 2010
#

CC     = arm-linux-gcc

obj-m := ledioport.o

KDIR := /Android/linux-2.6.29.6-hbep320

PWD := $(shell pwd)

TEST_TARGET = led_test
TEST_SRCS = $(TEST_TARGET).c

default:$(TEST_TARGET)
	$(MAKE) -C $(KDIR) SUBDIRS=$(PWD) modules
	$(CC) $(TEST_SRCS) -static -o $(TEST_TARGET)
	rm -f default

clean:
	rm -f *.ko *.o *.mod.* .*.cmd Module.symvers modules.order
	rm -f $(TEST_TARGET)
