package com.yy.review.decorate;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:00 2019/9/11
 */
public class Test {
    public static void main(String[] args) {
        Phone iphoneXR = new IphoneXR();
        Phone musicPhone = new MusicPhone(iphoneXR);
        Phone printTimePhone = new PrintTimePhone(musicPhone);
        printTimePhone.call();
    }
}
