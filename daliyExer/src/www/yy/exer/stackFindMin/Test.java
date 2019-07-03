package www.yy.exer.stackFindMin;

import java.util.Stack;

/**
 * @Author : YangY
 * @Description :随时获取栈内的最小值，要求时间复杂度为O（n）；
 * @Time : Created in 19:12 2019/5/22
 */
public class Test {
    private Stack<Integer> stack = new Stack<>();
    public void push(Integer x) {
        if(stack.isEmpty()) {
            stack.push(x);
            stack.push(x);
        }else {
            int temp = stack.peek();
            stack.push(x);
            if(x < temp) {
                stack.push(x);
            }else {
                stack.push(temp);
            }
        }
    }

    public void pop() {
        stack.pop();
        stack.pop();
    }
    public  int top() {
        int x = stack.pop();
        int res = stack.peek();
        stack.push(x);
        return res;
    }
    public int getMin() {
        return stack.peek();
    }
    public static void main(String[] args) {
        Test test = new Test();
        test.push(3);
        test.push(-1);
        test.push(4);
        test.push(0);
        System.out.println(test.getMin());
        test.pop();
        System.out.println(test.top());
    }
}
