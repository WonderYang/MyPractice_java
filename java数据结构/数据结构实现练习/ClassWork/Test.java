package www.ClassWork;

/**
 * @Author : YangY
 * @Description :  单链表简单功能的实现
 * @Time : Created in 19:30 2019/3/3
 */
public class Test {

    public static void main(String[] args) {
        Sequence sequence = new LinkedList();
       ((LinkedList) sequence).addFirst(8);
       ((LinkedList) sequence).addFirst(4);

        ((LinkedList) sequence).add(1,2);  //这个方法接口中没有，所以要强转
        //打印数据数组
        ((LinkedList) sequence).printList();
        //获取指定节点数据
        System.out.println("获取第一个节点数据："+sequence.get(1));
        //移除数据
        if (sequence.remove(2)) {
            ((LinkedList) sequence).printList();
        }
        //查元素是否存在
        if (sequence.contains(2)) {
            System.out.println("包含这个元素");
        }else {
            System.out.println("不包含这个元素");
        }

    }
}
