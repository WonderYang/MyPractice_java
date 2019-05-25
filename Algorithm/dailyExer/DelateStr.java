package www.yy.exer.day4;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 14:36 2019/5/25
 */
public class DelateStr {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.nextLine();
        String str2 = scanner.nextLine();
        System.out.println(delate(str1,str2));

    }
    public static String delate(String str1, String str2) {
        int[] arr = new int[256];
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<str2.length(); i++) {
            char s = str2.charAt(i);
            arr[s] = 1;
        }
        for(int j=0; j<str1.length(); j++) {
            char s= str1.charAt(j);
            if(arr[s] != 1) {
                stringBuilder.append(s);
            }
        }
        return stringBuilder.toString();
    }
}


//利用API方法
//import java.util.Scanner;
//public class Main{
//    public static void main(String[] args){
//        Scanner sc = new Scanner(System.in);
//        while(sc.hasNext()){
//            char[] ch = sc.nextLine().toCharArray();
//            String str = sc.nextLine();
//            for(int i=0;i<ch.length;i++){
//                if(!str.contains(String.valueOf(ch[i]))){
//                    System.out.print(ch[i]);
//                }
//            }
//        }
//    }
//}