package www.yy.exer.day13;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:01 2019/6/4
 */
public class AddLongNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String A = scanner.nextLine();
        String B = scanner.nextLine();
        int lenthRes = A.length()>B.length()?A.length():B.length();
        int[] res = new int[lenthRes];
        int lengthA = A.length()-1;
        int lengthB = B.length()-1;
        int j = 0;
        for(;lengthA>=0||lengthB>=0;lengthA--,lengthB--) {
            char a,b;
            if(lengthA<0) {
                a = '0';
                b = B.charAt(lengthB);
            }else if(lengthB<0) {
                a = A.charAt(lengthA);
                b = '0';
            }else {
                a = A.charAt(lengthA);
                b = B.charAt(lengthB);
            }
            int c = a+b-'0'*2;
            if(c+res[j]>9) {
                res[j] = c+res[j] - 10;
                res[j+1] = 1;
            }else {
                res[j] = c+res[j];
            }
            j++;
        }

        StringBuilder s = new StringBuilder();
        for(int i=res.length-1; i>=0;i--) {
            s.append(res[i]);
        }
        System.out.println(s.toString());



    }
}
