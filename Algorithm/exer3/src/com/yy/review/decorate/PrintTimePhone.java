package com.yy.review.decorate;

import java.util.Date;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:55 2019/9/11
 */
public class PrintTimePhone extends PhoneDecorate {

    public PrintTimePhone(Phone phone) {
        super(phone);
    }
    public void printCurrentTime() {
        System.out.println("当前时间是：" + new Date().toString());
    }

    @Override
    public void call() {
        printCurrentTime();
        super.call();
    }
}
