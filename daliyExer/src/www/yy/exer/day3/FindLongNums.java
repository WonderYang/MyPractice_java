package www.yy.exer.day3;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :找出一个字符串中最长的数字串
 * @Time : Created in 21:57 2019/5/23
 */
public class FindLongNums {
    public static String find(String s) {
        char[] arr = s.toCharArray();
        int max = 0;
        int j = 0;
        int endFinal = 0;
        int startFinal = 0;
        int end = 0;
        for(int i=0; i<arr.length; i++) {
            if(arr[i]>='0' && arr[i]<='9') {
                int start = i;
                for(j=i+1; j<arr.length; j++) {
                    if(arr[j]>='0' && arr[j]<='9') {
                        continue;
                    }else {
                        break;
                    }
                }
                if(j == arr.length-1) {   //当最常数字串是最末尾的那一串时，代表上面的循环不是因为break跳出的，
                    end = j;              //此时j没有执行j++，所以不需要减一；
                }
                end = j-1;
                //新的起点
                i = j;
                if((end-start)>max) {
                    max = end-start;
                    endFinal = end;
                    startFinal = start;
                }
            }
        }
        int y = 0;
        char[] newArr = new char[endFinal-startFinal+1];
        for(int x = startFinal; x<=endFinal;x++) {
            newArr[y++] = arr[x];
        }
        return new String(newArr);
    }
    public static void main(String[] args) {
        Scanner scannner =new Scanner(System.in);
        String s = scannner.nextLine();
        System.out.println(find(s));

    }
}
