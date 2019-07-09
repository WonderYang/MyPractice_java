package www.yy.exer.day17;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :  判断一个序列是否为所给定序列的合法出栈顺序（从一个题截取出来的）
 * @Time : Created in 22:18 2019/7/8
 */
public class SortStack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] in = {1,2,3,4};
        int[] out = {4,3,2,1};
        System.out.println(is_leagle(in, out, in.length));
    }
    public static boolean is_leagle(int[] in, int[] out, int n) {
        int i = 0;   //标识in
        int j = 0;   //标识out
        LinkedList<Integer> stack = new LinkedList();
        while(i < n) {
            if(in[i] == out[j]) {
                i++;
                j++;
            }else {
                if(stack.isEmpty()) {
                    stack.push(in[i]);
                    i++;
                }else {
                    int top = stack.peek();
                    if(out[j] == top) {
                        j++;
                        stack.pop();   //栈顶元素出栈
                    }else {
                        stack.push(in[i]);  //还有待入栈的数据
                        i++;
                    }
                }
            }
        }
        while(!stack.isEmpty()&&j<n) {   //out中余下的数顺序必须与stack中剩余元素的出栈顺序一样，才代表true
            if(stack.pop()!=out[j]) {
                return false;
            }else {
                j++;
            }
        }
        return true;
    }
}
