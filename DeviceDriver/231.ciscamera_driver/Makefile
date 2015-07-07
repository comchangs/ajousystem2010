#
# Makefile for CIS Camera Device Driver 
# Hanback Electronics Co.,ltd
# Date : January, 2010
#

CC     = arm-linux-gcc

obj-m := camera.o

KDIR := /Android/linux-2.6.29.6-hbep320

PWD := $(shell pwd)

default:
	$(MAKE) -C $(KDIR) SUBDIRS=$(PWD) modules
	rm -f default

clean:
	rm -f *.ko *.o *.mod.* .*.cmd Module.symvers modules.order

