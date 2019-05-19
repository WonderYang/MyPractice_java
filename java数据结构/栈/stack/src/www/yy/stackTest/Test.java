package www.yy.stackTest;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 17:17 2019/5/19
 */
public class Test {
    public static void main(String[] args) {
        ArrayStack<Integer> arrayStack = new ArrayStack<>(5);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(6);
        arrayStack.push(8);
        arrayStack.push(10);
        System.out.println("size: "+arrayStack.size());
        System.out.println("栈顶元素为："+arrayStack.peek());
        System.out.println("出栈："+arrayStack.top());
        System.out.println("出栈："+arrayStack.top());
        System.out.println("出栈："+arrayStack.top());
        System.out.println("size: "+arrayStack.size());
        System.out.println("出栈："+arrayStack.top());
        System.out.println("出栈："+arrayStack.top());
        System.out.println("出栈："+arrayStack.top());

        System.out.println("---------------------------------------");
        LinkedListStack<Integer> linkedListStack = new LinkedListStack();
        linkedListStack.push(3);
        linkedListStack.push(4);
        linkedListStack.push(6);
        linkedListStack.push(8);
        linkedListStack.push(10);
        linkedListStack.push(19);
        System.out.println("size: "+linkedListStack.size()); //6
        System.out.println("栈顶元素为："+linkedListStack.peek());   //19
        System.out.println("出栈："+linkedListStack.top());//19
        System.out.println("出栈："+linkedListStack.top());//10
        System.out.println("出栈："+linkedListStack.top());//8
        System.out.println("size: "+linkedListStack.size());  //3
        System.out.println("出栈："+linkedListStack.top());//6
        System.out.println("出栈："+linkedListStack.top());//4
        System.out.println("出栈："+linkedListStack.top());//3
        System.out.println("出栈："+linkedListStack.top());//null








    }
}
