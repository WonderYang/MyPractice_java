package www.yy.testjdbc;

import www.yy.message.Student;
import www.yy.message.Teacher;
import www.yy.message.Title;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 23:43 2019/7/3
 */
public class Function {
    Connection con = DbCollect.getMyConnection();
    public void insertTea(Teacher teacher) {
        String sql = "insert into teacher values('"+ teacher.getTea_id()+"','" + teacher.getTea_name()+
                "','"+teacher.getTea_degree()+"')";
        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            int res = pre.executeUpdate();
            System.out.println("有"+res+"行数据受到影响！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTitle(Title title) {
        String sql = "insert into title values('"+title.getTit_id()+"','"+title.getTit_name()+"','"+
                title.getTea_id()+"','"+title.getTit_mean()+"','"+title.getTit_index()+"',"+
                title.getZd_grade()+","+title.getDb_grade()+","+title.getPy_grade()+")";
        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            int res = pre.executeUpdate();
            System.out.println("有"+res+"行数据受到影响！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //学生表的信息插入
    public void insertStudent(Student student) {
        String sql = "insert into student values('"+student.getStu_id()+"','"+student.getStu_name()+
                "','"+student.getTit_id()+"','"+student.getTea_id()+"',"+student.getGrade()+")";
        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            int res = pre.executeUpdate();
            System.out.println("有"+res+"行数据受到影响！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTea(String id) {
        String sql = "delete from teacher where tea_id=?";
        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            pre.setString(1,id);
            int res = pre.executeUpdate();
            System.out.println("有"+res+"行数据受到影响！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //删除题目表数据
    public void deleteTit(String id) {
        String sql = "delete from title where tit_id=?";
        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            pre.setString(1,id);
            int res = pre.executeUpdate();
            System.out.println("有"+res+"行数据受到影响！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除学生表数据
    public void deleteStu(String id) {
        String sql = "delete from student where stu_id=?";
        PreparedStatement pre = null;
        try {
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            pre.setString(1,id);
            int res = pre.executeUpdate();
            System.out.println("有"+res+"行数据受到影响！！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //打印教师表
    public void printTeacher() {
        //产生执行对象
        PreparedStatement pre = null;
        try {
            String sql = "select * from teacher";
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            ResultSet res = pre.executeQuery();
            System.out.printf("%-10s","tea_id");
            System.out.printf("%-10s","tea_name");
            System.out.printf("%-10s","tea_degree");
            System.out.println();
            System.out.println("------------------------------");
            while(res.next()) {
                String id = res.getString(1);
                String name = res.getString(2);
                String degree = res.getString(3);
                System.out.printf("%-10s",id);
                System.out.printf("%-10s",name);
                System.out.printf("%-10s",degree);
                System.out.printf("\n");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printTitle() {
        PreparedStatement pre = null;
        String sql = "select * from title";
        System.out.printf("%-10s","tit_id");
        System.out.printf("%-10s","tit_name");
        System.out.printf("%-10s","tea_id");
        System.out.printf("%-10s","tit_mean");
        System.out.printf("%-10s","tit_index");
        System.out.printf("%-10s","zd_grade");   //这里的实质是字符，用%s
        System.out.printf("%-10s","db_grade");
        System.out.printf("%-10s","py_grade");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        try {
            pre = con.prepareStatement(sql);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String tit_id = res.getString(1);
                String tit_name = res.getString(2);
                String tea_id = res.getString(3);
                String tit_mean = res.getString(4);
                String tit_index = res.getString(5);
                int zd_grade = res.getInt(6);
                int db_grade = res.getInt(7);
                int py_grade = res.getInt(8);
                System.out.printf("%-10s",tit_id);
                System.out.printf("%-10s",tit_name);
                System.out.printf("%-10s",tea_id);
                System.out.printf("%-10s",tit_mean);
                System.out.printf("%-10s",tit_index);
                System.out.printf("%-10d",zd_grade);
                System.out.printf("%-10d",db_grade);
                System.out.printf("%-10d",py_grade);
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printStudent() {
        PreparedStatement pre = null;
        String sql = "select * from student";
        System.out.printf("%-10s","stu_id");
        System.out.printf("%-10s","stu_name");
        System.out.printf("%-10s","tit_id");
        System.out.printf("%-10s","tea_id");
        System.out.printf("%-10s","grade");
        System.out.println();
        System.out.println("---------------------------------------------------");
        try {
            pre = con.prepareStatement(sql);
            ResultSet res = pre.executeQuery();
            while(res.next()) {
                String stu_id = res.getString(1);
                String stu_name = res.getString(2);
                String tit_id = res.getString(3);
                String tea_id = res.getString(4);
                int grade = res.getInt(5);
                System.out.printf("%-10s",stu_id);
                System.out.printf("%-10s",stu_name);
                System.out.printf("%-10s",tit_id);
                System.out.printf("%-10s",tea_id);
                System.out.printf("%-10d",grade);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //实现访问存储过程
    public void save() {
        PreparedStatement pre = null;
        String sql = "CALL proc_2();";
        System.out.printf("%-10s","stu_id");
        System.out.printf("%-10s","stu_name");
        System.out.printf("%-10s","grade");
        System.out.println();
        System.out.println("-------------------------");
        try {
            pre = con.prepareStatement(sql);
            ResultSet res = pre.executeQuery();
            while (res.next()) {
                String stu_id = res.getString(1);
                String stu_name = res.getString(2);
                int grade = res.getInt(3);
                System.out.printf("%-10s",stu_id);
                System.out.printf("%-10s",stu_name);
                System.out.printf("%-10d",grade);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printView1() {
        PreparedStatement pre = null;
        try {
            String sql = "select * from view_1";
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            ResultSet res = pre.executeQuery();
            System.out.printf("%-10s","tea_id");
            System.out.printf("%-10s","tea_name");
            System.out.printf("%-10s","tea_degree");
            System.out.println();
            System.out.println("------------------------------");
            while(res.next()) {
                String id = res.getString(1);
                String name = res.getString(2);
                String degree = res.getString(3);
                System.out.printf("%-10s",id);
                System.out.printf("%-10s",name);
                System.out.printf("%-10s",degree);
                System.out.printf("\n");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
