package www.yy.testjdbc;

import www.yy.message.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:36 2019/7/3
 */
public class Test {
    public static void main(String[] args) {

        Connection connection =  DbCollect.getMyConnection();
        fun();
    }
    public static void fun() {
        boolean start = true;
        menu();
        while(start) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请选择：(1~5)");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    System.out.println("输入你要增加数据的表：（1.教师 2.题目 3.学生）");
                    int in0 = scanner.nextInt();
                    switch (in0) {
                        case 1:ImportData.teacherImport();
                        break;
                        case 2:ImportData.titleImport();
                            break;
                        case 3:ImportData.studentImport();
                            break;
                    }
                    break;
                case 2:
                    System.out.println("输入你要删除数据的表：（1.教师 2.题目 3.学生)");
                    int in1 = scanner.nextInt();
                    switch (in1) {
                        case 1: ImportData.teacherDelete();
                        break;
                        case 2: ImportData.titleDelete();
                        case 3:ImportData.studentDelete();
                            break;
                    }
                    break;
                case 3:
                    System.out.println("输入你要查看的表：（1.教师 2.题目 3.学生");
                    int in3 = scanner.nextInt();
                    switch (in3) {
                        case 1:ImportData.printTea();
                        break;
                        case 2:ImportData.printTit();
                            break;
                        case 3:ImportData.printStu();
                        break;
                    }
                    break;
                case 4:
                    ImportData.my_save();
                    break;
                case 5:
                    ImportData.view1Print();
                    break;
                case 0:
                    start = false;
                    break;
            }
        }
    }
    public static void menu() {
        System.out.println("***************   0.退出    *******************");
        System.out.println("***************1.增加数据**********************");
        System.out.println("***************2.删除数据**********************");
        System.out.println("***************3.查看数据**********************");
        System.out.println("**********4.存储过程(学生成绩实现降序)***********");
        System.out.println("***************5.查看视图(教师表)****************");



    }

}
