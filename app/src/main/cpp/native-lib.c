#include <jni.h>
#include <unistd.h>
#include <fcntl.h>
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

//can be separated to textlcd.h
#ifndef TEXTLCD_H_
#define TEXTLCD_H_

#define TEXTLCD_ON        1
#define TEXTLCD_OFF    2
#define TEXTLCD_INIT   3
#define TEXTLCD_CLEAR  4

#define TEXTLCD_LINE1  5
#define TEXTLCD_LINE2  6

#endif

static int fd;
static int fdd;


//textLCD
JNIEXPORT void JNICALL Java_com_example_rpstest2_TextLCD_on
        (JNIEnv * env, jobject obj){
    if (fd == 0)
        fd = open("/dev/fpga_textlcd", O_WRONLY);
    assert(fd != 0);

    ioctl(fd, TEXTLCD_ON);
}

JNIEXPORT void JNICALL Java_com_example_rpstest2_TextLCD_off
        (JNIEnv * env, jobject obj){
    if (fd )
    {
        ioctl(fd, TEXTLCD_OFF);
        close(fd);
    }

    fd = 0;
}

JNIEXPORT void JNICALL Java_com_example_rpstest2_TextLCD_initialize
        (JNIEnv * env, jobject obj){
    if (fd == 0)
        fd = open("/dev/fpga_textlcd", O_WRONLY);
    assert(fd != -1);

    ioctl(fd, TEXTLCD_INIT);
}

JNIEXPORT void JNICALL Java_com_example_rpstest2_TextLCD_clear
        (JNIEnv * env, jobject obj){
    //if (fd )
    ioctl(fd, TEXTLCD_CLEAR);
}

JNIEXPORT void JNICALL Java_com_example_rpstest2_TextLCD_print1Line
        (JNIEnv * env, jobject obj, jstring msg){
    const char *str;

    if (fd )
    {
        str = (*env)->GetStringUTFChars(env, msg, 0);
        ioctl(fd, TEXTLCD_LINE1);
        write(fd, str, strlen(str));
        (*env)->ReleaseStringUTFChars(env, msg, str);
    }

}

JNIEXPORT void JNICALL Java_com_example_rpstest2_TextLCD_print2Line
(JNIEnv * env, jobject obj, jstring msg){
    const char *str;

    if (fd )
    {
        str = (*env)->GetStringUTFChars(env, msg, 0);
        ioctl(fd, TEXTLCD_LINE2);
        write(fd, str, strlen(str));
        (*env)->ReleaseStringUTFChars(env, msg, str);
    }
}

//------------------------FULL LED----------------------------
#define FULL_LED1	9
#define	FULL_LED2	8
#define FULL_LED3	7
#define FULL_LED4	6
#define ALL_LED		5

JNIEXPORT jint JNICALL
Java_com_example_rpstest2_FullLED_FLEDCONTROL(JNIEnv *env, jobject thiz, jint led_num, jint red,
                                              jint green, jint blue) {
    int fd,ret;
    char buf[3];

    fd = open("/dev/fpga_fullcolorled", O_WRONLY);
    if (fd < 0)
    {
        exit(-1);
    }
    ret = (int)led_num;
    switch(ret)
    {
        case FULL_LED1:
            ioctl(fd,FULL_LED1);
            break;
        case FULL_LED2:
            ioctl(fd,FULL_LED2);
            break;
        case FULL_LED3:
            ioctl(fd,FULL_LED3);
            break;
        case FULL_LED4:
            ioctl(fd,FULL_LED4);
            break;
        case ALL_LED:
            ioctl(fd,ALL_LED);
            break;
    }
    buf[0] = red;
    buf[1] = green;
    buf[2] = blue;

    write(fd,buf,3);

    close(fd);
}

//-----------------------DOT MATRIX----------------------

JNIEXPORT void JNICALL
Java_com_example_rpstest2_DotMatrix_DotMatrixControl(JNIEnv *env, jobject thiz, jstring str) {
    const char *pStr;
    int fd, len;

    pStr = (*env)->GetStringUTFChars(env, str, 0);
    len = (*env)->GetStringLength(env, str);

    fd = open("/dev/fpga_dotmatrix", O_RDWR | O_SYNC);

    write(fd, pStr, len);
    close(fd);

    (*env)->ReleaseStringUTFChars(env, str, pStr);
}