package www.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:23 2019/3/20
 */
public class Test {
    public static void main(String[] args) {
        Sequence<Integer> sequence = new <Integer>DoubleLinkedListImpl();
        sequence.add(20);
        sequence.add(10);
        sequence.add(30);
        sequence.remove(2);
        //sequence.set(2,1000);
       // sequence.insertNode(2,100);
        //System.out.println(sequence.size());
        //sequence.clear();
        //System.out.println(sequence.contains(100));
        for(Object x: sequence.toArray()) {
            System.out.println(x);
        }
        //System.out.println(sequence.get(4));
    }

}
