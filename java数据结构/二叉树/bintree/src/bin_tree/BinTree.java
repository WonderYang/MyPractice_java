package bin_tree;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:03 2019/5/28
 */
public interface BinTree<E> {
    void add(E e);
    boolean contains(E e);

    E getMin();
    E getMax();
    E removeMin();
    E removeMax();
    void remove(E e);
    int size();

    //前序遍历
    void preOrder();
    //中序
    void inOrder();
    //后序
    void postOrder();
    //层序遍历
    void levelOrder();
}
