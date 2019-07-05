package www.yy.testjdbc;

import www.yy.message.Student;
import www.yy.message.Teacher;
import www.yy.message.Title;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description : 专门用来数据的插入时的控制台输入数据代码
 * @Time : Created in 11:07 2019/7/4
 */
public class ImportData {
    //教师表的插入数据操作
    public static void teacherImport() {
        Teacher teacher = new Teacher();
        System.out.println("请输入要插入的数据：(依次输入id、name、degree)");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        teacher.setTea_id(id);
        String name = scanner.nextLine();
        teacher.setTea_name(name);
        String degree = scanner.nextLine();
        teacher.setTea_degree(degree);

        Function function = new Function();
        function.insertTea(teacher);
    }
    public static void titleImport() {
        Title title = new Title();
        System.out.println("请输入要插入的数据：(依次输入题目号、题目名、教师号（外键）、选题意义、技术指标" +
                "、指导成绩、答辩成绩、评阅成绩)");
        Scanner scanner = new Scanner(System.in);
        String tit_id = scanner.nextLine();
        title.setTit_id(tit_id);
        String tit_name = scanner.nextLine();
        title.setTit_name(tit_name);
        String tea_id = scanner.nextLine();
        title.setTea_id(tea_id);
        String tit_mean = scanner.nextLine();
        title.setTit_mean(tit_mean);
        String tit_index = scanner.nextLine();
        title.setTit_index(tit_index);
        int zd_grade = scanner.nextInt();
        int db_grade = scanner.nextInt();
        int py_grade = scanner.nextInt();

        title.setZd_grade(zd_grade);
        title.setDb_grade(db_grade);
        title.setPy_grade(py_grade);
        Function function = new Function();
        function.insertTitle(title);
    }

    public static void studentImport() {
        Connection con = DbCollect.getMyConnection();
        Student student = new Student();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要插入的数据：（依次输入学生号、学生名、题目号(外键)、教师号（外键））");
        String stu_id = scanner.nextLine();
        student.setStu_id(stu_id);
        String stu_name = scanner.nextLine();
        student.setStu_name(stu_name);
        String tit_id = scanner.nextLine();
        student.setTit_id(tit_id);
        String tea_id = scanner.nextLine();
        student.setTea_id(tea_id);
        PreparedStatement pre = null;
        try {
            String titId = student.getTit_id();
            String sql = "select (zd_grade*0.4+db_grade*0.3+py_grade*0.3) from title  " +
                    "where title.tit_id="+titId;
            pre = con.prepareStatement(sql);
            //获取到查询结果的虚拟表
            ResultSet resultSet = pre.executeQuery();
            int x = 0;
            while(resultSet.next()) {
                x = resultSet.getInt(1);
            }
            student.setGrade(x);
            Function function = new Function();
            function.insertStudent(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void teacherDelete() {
        System.out.println("请输入你要删除老师的id号：");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        Function function = new Function();
        function.deleteTea(id);
    }

    public static void titleDelete() {
        System.out.println("请输入你要删除题目的id号：");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        Function function = new Function();
        function.deleteTit(id);
    }

    public static void studentDelete() {
        System.out.println("请输入你要删除学生的id号：");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        Function function = new Function();
        function.deleteStu(id);
    }


    //打印教师表
    public static void printTea() {
        Function function = new Function();
        function.printTeacher();
    }
//打印题目表
    public static void printTit() {
        Function function = new Function();
        function.printTitle();
    }
    public static void printStu() {
        Function function = new Function();
        function.printStudent();
    }

    public static void my_save() {
        Function function = new Function();
        function.save();
    }

    public static void view1Print() {
        Function function = new Function();
        function.printView1();
    }
}
