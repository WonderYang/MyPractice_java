package www.yy.exer.day1;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/9d1559511b3849deaa71b576fa7009dc
 * “回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。花花非常喜欢这种拥有对称美
 * 的回文串，生日的时候她得到两个礼物分别是字符串A和字符串B。现在她非常好奇有没有办法将字符串B插入字符串A使产生
 * 的字符串是一个回文串。你接受花花的请求，帮助她寻找有多少种插入办法可以使新串是一个回文串。如果字符串B插入的
 * 位置不同就考虑为不一样的办法。
 * 例如：
 * A = “aba”，B = “b”。这里有4种把B插入A的办法：
 * * 在A的第一个字母之前: "baba" 不是回文
 * * 在第一个字母‘a’之后: "abba" 是回文
 * * 在字母‘b’之后: "abba" 是回文
 * * 在第二个字母'a'之后 "abab" 不是回文
 * 所以满足条件的答案为2
 * @Time : Created in 20:13 2019/5/23
 */
public class HuiWen{
public static boolean isHuiWen(String s) {
    int i = 0;
    int j = s.length() - 1;     //注意字符串是length（）方法来求长度，不是数组那样length属性；
    while(i < j) {
        if(s.charAt(i) != s.charAt(j)) {
            return false;
        }
        i++;
        j--;
    }
    return true;
}
public static void main(String[] args) {
    int count = 0;
    Scanner scanner = new Scanner(System.in);
    String A = scanner.nextLine();
    String B = scanner.nextLine();
    for(int i=0; i<=A.length(); i++) {   //注意这里是小于等于
        //可不敢把这一行放到循环外面去，这样一来，StringBuffer一直在插入。。。
        StringBuffer stringBufferA = new StringBuffer(A);
        //只有StringBuffer才有insert方法，String类没有
        if( isHuiWen( stringBufferA.insert(i,B).toString() ) ) {
            count++;
        }
    }
    System.out.println(count);
}
}
