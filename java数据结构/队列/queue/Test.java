package www.yy.queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:45 2019/5/22
 */
public class Test {
    public static void main(String[] args) {
//        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>(4);
//        arrayQueue.enQueue(2);
//        arrayQueue.enQueue(4);
//        arrayQueue.enQueue(5);
//        arrayQueue.enQueue(1);
//        System.out.println( arrayQueue.getSize());
//        System.out.println(arrayQueue.deQueue());
//        System.out.println(arrayQueue.getSize());
//        System.out.println("--------------------------");
//        ArrayLoopQueue<Integer> arrayLoopQueue = new ArrayLoopQueue<>(3);
//        arrayLoopQueue.enQueue(2);
//        arrayLoopQueue.enQueue(4);
//        arrayLoopQueue.enQueue(5);
//        System.out.println(arrayLoopQueue.deQueue());
//        System.out.println(arrayLoopQueue.deQueue());
//        System.out.println(arrayLoopQueue.deQueue());
//        arrayLoopQueue.enQueue(54);
//        arrayLoopQueue.enQueue(22);
//        System.out.println(arrayLoopQueue.getSize());
//        System.out.println(arrayLoopQueue.deQueue());

        System.out.println("-------------------");
        LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
        linkedQueue.enQueue(12);
        linkedQueue.enQueue(11);
        System.out.println(linkedQueue.deQueue());
        System.out.println(linkedQueue.peek());
        System.out.println(linkedQueue.getSize());

    }
}
