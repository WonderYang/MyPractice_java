package com.bitten.file;
import java.lang.reflect.Field;

/**
 * @Author : YangY
 * @Description : 将源对象中与目的对象相同属性，源对象的属性值copy赋值给目的对象
 * @Time : Created in 22:12 2019/4/2
 */
public class BeanCopy {
    public static void copy(Object souce,Object target) {
        //1.创建它们的Class对象
        Class classSouce = souce.getClass();
        Class classTarget = target.getClass();
        //2.根据Class对象获得他们的所有属性,注意是Field，不是File，File是IO操作中的类
        Field[] arrSouce = classSouce.getDeclaredFields();
        Field[] arrTargrt = classTarget.getDeclaredFields();
        //遍历所有属性，查找相同部分
        for(Field sou: arrSouce) {
            for(Field tar: arrTargrt) {
                //这里不能是sou.equals(tar),因为这对象的String方法没有被覆写，下面的getName方法时返回String
                //类对象，String类的equals方法是被覆写的
                if(sou.getName().equals(tar.getName())) {
                    try {
                        //由于属性基本都是private，不能直接访问的，所以得使用setAccessible方法开启权限；
                        sou.setAccessible(true);
                        tar.setAccessible(true);
                        Object obj = sou.get(souce);
                        tar.set(target,obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.setAge(18);
        a.setHobby("basketball");
        a.setJob("boss");
        System.out.println(a.toString());
        b.setAge(22);
        b.setHobby("PingPangBall");
        b.setSkill("eat");
        System.out.println(b.toString());
        System.out.println("--------------------------------------------");
        copy(a,b);
        System.out.println(a.toString());
        System.out.println(b.toString());

        /**
         * 最终结果：
         * A{age=18, job='boss', hobby='basketball'}
         * B{age=22, hobby='PingPangBall', skill='eat'}
         * --------------------------------------------
         * A{age=18, job='boss', hobby='basketball'}
         * B{age=18, hobby='basketball', skill='eat'}
         */
    }
}

class A {
    private int age;
    private String job;
    private String hobby;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "A{" +
                "age=" + age +
                ", job='" + job + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
class B {
    private int age;
    private String hobby;
    private String skill;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "B{" +
                "age=" + age +
                ", hobby='" + hobby + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}