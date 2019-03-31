package com.bitten.file;
/**
 * @Author : YangY
 * @Description :  打印出对应目下所有目录与文件
 * @Time : Created in 11:19 2019/3/31
 */

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 *
 * 科普：
 *      windows：文件名不区分大小写    分隔符：\(反斜杠)  ，所以得加反斜杠，“\\”  路径分隔符：；
 *      或者用：File.separator
 *      Linux：文件名区分大小写         分隔符：/(正斜杠)    路径分隔符：：
 *
 * File对象中mkdir 创建当前目录（如果父目录不存在则创建失败）
 * File对象中mkdirs 创建当前目录（如果父目录不存在则一并创建）
 */
public class TestFile {
    public static void main(String[] args) {
        File ffile = new File("E:\\文件测试");
        System.out.println(ffile.getName());
        listFiles(ffile,1);

    }
    public static void addblank(int level) {
        int i = 0;
        System.out.print("|");
        for(;i<level;i++) {
            System.out.print("----");
        }
    }
    public static void listFiles(File file,int level) {
        //System.out.println(file.getName());
        if(file.isDirectory()) {
            //如果为目录，则创建一个目录数组，一一遍历再判断是否为目录
            File[] files = file.listFiles();
            if(files != null) {
                for(File f:files) {
                    addblank(level);
                    System.out.println(f.getName());
                    if(f.isDirectory()) {
                        listFiles(f,level+1);
                    }
                }
            }
        }else {
            System.out.println(file.getName());
        }
    }
}
