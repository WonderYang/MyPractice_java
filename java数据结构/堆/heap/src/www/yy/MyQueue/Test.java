package www.yy.MyQueue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 17:31 2019/10/9
 */
public class Test {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.enqueue(4);
        priorityQueue.enqueue(6);
        priorityQueue.enqueue(2);
        priorityQueue.enqueue(29);
        priorityQueue.enqueue(-12);
        System.out.println(priorityQueue.peek()); //29
        System.out.println(priorityQueue.dequeue()); //29
        System.out.println(priorityQueue.size());  //4
        System.out.println(priorityQueue.dequeue());  //6
    }

}
