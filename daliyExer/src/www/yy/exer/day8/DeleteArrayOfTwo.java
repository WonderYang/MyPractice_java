package www.yy.exer.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/f9533a71aada4f35867008be22be5b6e
 * 有一个数组a[N]顺序存放0~N-1，要求每隔两个数删掉一个数，到末尾时循环至开头继续进行，求最后一个被删掉的数
 * 的原始下标位置。以8个数(N=7)为例:｛0，1，2，3，4，5，6，7｝，0->1->2(删除)->3->4->5(删除)->6->7->0
 * (删除),如此循环直到最后一个数被删除。
 * 输入描述:
 * 每组数据为一行一个整数n(小于等于1000)，为数组成员数,如果大于1000，则对a[999]进行计算。
 * 输出描述:
 * 一行输出最后一个被删掉的数的原始下标位置。
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
