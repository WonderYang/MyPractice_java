package www.yy.exer.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:25 2019/5/29
 */
public class DeleteArrayOfTwo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {   //进行多个案例测试
            int N = scanner.nextInt();
            int[] arr = new int[N];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            myFun(arr, N);   //我的方法
            answer(N);       //示例方法
        }
    }

    public static void myFun(int[] arr, int N) {
        int pointer = -1;
        for(int j=0; j<N-1; j++) {
            int flag = 0;
            while(true) {
                if(arr[(++pointer)%N] != -1) {
                    flag++;
                    if(flag == 3) {
                        arr[pointer%N] = -1;
                        break;
                    }

                }
            }
        }
        while(true) {
            if(arr[(++pointer)%N] != -1) {
                break;
            }
        }
        System.out.println(arr[pointer%N]);
    }

    public static void answer(int N) {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<N; i++) {
            list.add(i);
        }
        int temp = 0;
        while(list.size() > 1) {
            temp = (temp+2)%list.size();   //每当删除元素时，size和下标也在对应改变
            list.remove(temp);
        }
        System.out.println(list.get(0));


    }

}
