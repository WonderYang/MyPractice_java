package www.yy.exer.StackApplication;

import java.util.Stack;

/**
 * @Author : YangY
 * @Description : 表达式求值，利用栈来解决
 * //4+2*8-5
 * @Time : Created in 19:46 2019/5/22
 */
public class Expression {
    //用来保存数值的栈
    Stack<Integer> stackValue = new Stack<>();
    //用来保存符号的栈
    Stack<Character> stackMark = new Stack<>();

    public int calculate(String s) {
        char[] arr =  s.toCharArray();
        for(int i=0; i<arr.length; i++) {
            if(arr[i]>'0' && arr[i]<'9') {
                //stackValue.push(arr[i]);
            }
        }
        return 0;
    }

}
