package www.bitten.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author : YangY
 * @Description :  通过反射来实现参数设置
 * @Time : Created in 17:54 2019/3/30
 */
public class TestEmp {

    public static void main(String[] args) {
        String line = "ename:Jack|job:SoftDev|skill:java,c++";
        Emp emp = new Emp();
        String[] attributes = line.split("\\|");
        for (String attribute : attributes) {
            setXxx(emp, attribute);
        }
        System.out.println(emp);

    }

    //Setter赋值
    public static void setXxx(Emp emp, String kv) {
        //set + 属性名的首字母大写
        Class classz = emp.getClass();
        String[] segments = kv.split(":");
        String attribute = segments[0];
        String attributeMethodName =
                "set" + attribute.substring(0, 1).toUpperCase() +
                        ((attribute.length() > 1) ? attribute.substring(1) : "");
        try {
            Method method = classz.getDeclaredMethod(attributeMethodName, String.class);
            method.invoke(emp, segments[1]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}

class Emp {

    private String ename;
    private String job;
    private String skill;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }


}
