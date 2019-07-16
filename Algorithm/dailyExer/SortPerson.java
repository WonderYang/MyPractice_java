package www.yy.exer.day19;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/0383714a1bb749499050d2e0610418b1
 * 查找和排序
 * 题目：输入任意（用户，成绩）序列，可以获得成绩从高到低或从低到高的排列,相同成绩
 * 都按先录入排列在前的规则处理。
 *
 * 示例：
 * jack      70
 * peter     96
 * Tom       70
 * smith     67
 *
 * 从高到低  成绩
 * peter     96
 * jack      70
 * Tom       70
 * smith     67
 *
 * 从低到高
 *
 * smith     67
 *
 * jack      70
 * Tom      70
 * peter     96
 *
 *
 * 输入描述:
 * 输入多行，先输入要排序的人的个数，然后输入排序方法0（降序）或者1（升序）再分别输入他们的名字和成绩，以一个空格隔开

 * 输出描述:
 * 按照指定方式输出名字和成绩，名字和成绩之间以一个空格隔开
 *
 * 示例1
 * 输入
 * 3
 * 0
 * fang 90
 * yang 50
 * ning 70
 * 输出
 * fang 90
 * ning 70
 * yang 50
 * @Time : Created in 23:15 2019/7/16
 */
class Student {
    String name;
    int score;  //成绩

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
//实现升序
class Compare implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.score-o2.score;
    }
}

public class SortPerson {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stuNum = scanner.nextInt();
        int sortWay = scanner.nextInt();
        List<Student> list = new ArrayList<>();
        for(int i=0; i<stuNum; i++) {
            Student stu = new Student(scanner.next(), scanner.nextInt());
            list.add(stu);
        }
        if(sortWay == 0) {
            list.sort(((o1, o2) -> o2.score-o1.score));   //朗达表达式
        }else if(sortWay == 1) {
            //上面是朗达表达式，下面为了回忆Comparator的使用，我就这样写的
            Compare compare = new Compare();
            list.sort(compare);
        }
        for(Student stu : list) {
            System.out.print(stu.name);
            System.out.println(" "+stu.score);
        }
    }
}
