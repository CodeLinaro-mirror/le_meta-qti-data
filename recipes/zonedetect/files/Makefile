ifndef CC
	CC = arm-linux-gnueabi-gcc -march=armv7-a --static
endif

ifndef CFLAGS
	CFLAGS  = -g -Wall
endif

TZDETECT_SRC = demo.c zonedetect.c zonedetect.h

TZDETECT_TARGET = tzdetect

all:
	$(CC) -o $(TZDETECT_TARGET) $(CFLAGS) $(TZDETECT_SRC) -lm

clean:
	rm -f tzdetect

