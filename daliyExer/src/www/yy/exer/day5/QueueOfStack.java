package www.yy.exer.day5;

/**
 * @Author : YangY
 * @Description : 用两个栈实现一个队列
 * @Time : Created in 22:04 2019/5/25
 */
import java.util.Stack;

public class QueueOfStack {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.isEmpty()) {    //stack2为空才将stack1中的数据倒转过来
            while(!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
