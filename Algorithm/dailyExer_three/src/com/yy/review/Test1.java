package com.yy.review;

import java.io.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:22 2019/9/10
 */
class Classes implements Serializable{
    public int id;
}
class Stu implements Serializable{
    public Classes classes;

}
public class Test1 {
    public static void main(String[] args) throws Exception{
        Stu stu1 = new Stu();
        stu1.classes = new Classes();
        stu1.classes.id = 10;
        Stu stu2 = CloneUtils.clone(stu1);
        stu2.classes.id = 20;
        System.out.println("stu1：" +stu1.classes.id);
        System.out.println("stu2：" +stu2.classes.id);
    }
}
class CloneUtils {
    public static <T extends Serializable> T clone(T obj) {
        T resClone = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bo);
            oos.writeObject(obj);
            oos.close();
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bi);
            resClone = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resClone;
    }
}
