package com.yy.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:42 2019/4/28
 */
public class TestOOM {
    static class OOMObject {

    }
    //JVM 程序启动的时候通过JVM参数指定最大最小的内容
    //-Xmx20m -Xms20m -XX:+HeapDumpOnOutOfMemoryError
    //该参数设置点击右上角的TestOOM的Edit configurations进去设置，将上面这段复制到VM options中就完成了设置

    //该程序执行后会抛出OutOfMemoryError，并且会在这个项目下自动生成一个Dump文件，用来存放已经加载过的内容
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while(true) {
            list.add(new OOMObject());
        }
    }
}
