package com.yy.review.decorate;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:46 2019/9/11
 */
public class MusicPhone extends PhoneDecorate {
    public MusicPhone(Phone phone) {
        super(phone);
    }
    public void music() {
        System.out.println("BGM is running!!!!!!");
    }

    @Override
    public void call() {
        super.call();
        music();
    }
}
