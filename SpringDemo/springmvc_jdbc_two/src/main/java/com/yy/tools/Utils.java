package com.yy.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:18 2019/8/8
 */
public class Utils {

    //获取指定名字的属性文件的properties对象
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(fileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
