package bin_search_tree;

import bin_tree.BinTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:07 2019/5/28
 */
public class BinSearchTree<E extends Comparable<E>> implements BinTree<E> {
    //根节点
    private Node root;
    //大小
    private int size;
    private class Node{
        E data;
        Node left;
        Node right;

        public Node(E data) {
            this.data = data;
        }
    }
    @Override
    public void add(E e) {
        if(root == null) {
            Node node = new Node(e);
            root = node;
            size++;
        }else {
            add(root,e);
        }
    }
    //以指定的node为根节点，插入元素e
    private void add(Node node, E e) {
        //插入的值重复了，搜索二叉树不允许重复的值；
        if(node.data.compareTo(e) == 0) {
            return;
        }
        //找到插入位置，在左子树做插入
        else if(e.compareTo(node.data) < 0 && node.left==null) {
            Node newNode = new Node(e);
            node.left = newNode;
            size++;
        }
        else if(e.compareTo(node.data) > 0 && node.right==null) {
            Node newNode = new Node(e);
            node.right = newNode;
            size++;
        }
        //如果要插入的节点值小于当前节点的值并且当前节点的左子树不为空，就得递归寻找左树插入位置
        else if(e.compareTo(node.data) < 0) {
            add(node.left,e);
        }
        //如果要插入的节点值大于当前节点的值并且当前节点的右子树不为空，就得递归寻找右树插入位置
        else if(e.compareTo(node.data) > 0) {
            add(node.right,e);
        }

    }

    @Override
    public boolean contains(E e) {
        if(root == null) {
            return false;
        }
        //以根节点开始递归查找
        return contains(root,e);
    }
    private boolean contains(Node node, E e) {
        if(node.data.compareTo(e) == 0) {
            return true;
        }
        //一定要加上判空的条件，否则如果找不到会出现空指针异常；
        else if(node.left!=null && node.data.compareTo(e) > 0) {
            //当初的超级坑，没写return
            return contains(node.left, e);
        }
        else if(node.right!=null && node.data.compareTo(e) < 0) {
            return contains(node.right, e);
        }

        return false;
    }

    @Override
    public E getMin() {
        //递归方法
        if(root == null) {
            throw new IllegalArgumentException();
        }
        return  getMin(root).data;
        //迭代方法
//        Node node = root;
//        if(root != null) {
//            while(node.left != null) {
//                node = node.left;
//            }
//        }
//        return node.data;
    }
    private Node getMin(Node node) {
        if(node.left == null) {
            return node;
        }
        return getMin(node.left);
    }

    @Override
    public E getMax() {
        Node node = root;
        if(root != null) {
            while(node.right != null) {
                node = node.right;
            }
        }
        return node.data;
    }

    @Override
    public E removeMin() {
        if(root == null) {
            throw new IllegalArgumentException();
        }
        return removeMin(root).data;
    }
    private Node removeMin(Node node) {
        if(node.left == null) {
            Node rightNode = node.right;
            node.left = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public E removeMax() {
        return null;
    }

    @Override
    public void remove(E e) {
        remove(root,e);
    }

    /**
     *
     * @param node
     * @param e
     * @return
     */
    private Node remove(Node node, E e) {
        if (node == null)
            return null;
        if (e.compareTo(node.data) < 0) {
            node.left = remove(node.left,e);
        }
        if (e.compareTo(node.data) > 0) {
            node.right = remove(node.right,e);
        }
        // 此时node就为需要删除的节点
        else {
            // 若此时节点只有一边孩子
            if (node.left != null && node.right == null) {
                Node leftNode = node.left;
                size --;
                node.left = null;
                return leftNode;
            }
            if (node.right != null && node.left == null) {
                Node rightNode = node.right;
                size --;
                node.right = null;
                return rightNode;
            }
            if (node.left != null && node.right != null) {
                // 找到前驱或后继节点
                Node successor = getMin(node.right);
                successor.left = node.left;
                successor.right = removeMin(node.right);
                node.left = node.right = null;
                return successor;
            }
        }
        return node;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void preOrder() {
        preOrder(root);
    }
    private void preOrder(Node node) {
        if(node == null) {
            return;
        }
        System.out.println(node.data);
        preOrder(node.left);
        preOrder(node.right);
    }

    @Override
    public void inOrder() {
        inOrder(root);
    }
    private void inOrder(Node node) {
        if(node == null) {
            return;
        }
        System.out.println(node.data);
        inOrder(node.left);
        inOrder(node.right);
    }
    @Override
    public void postOrder() {

    }

    @Override
    public void levelOrder() {
        if(root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.println(temp.data);
            if(temp.left != null) {
                queue.add(temp.left);
            }
            if(temp.right != null) {
                queue.add(temp.right);
            }
        }
    }

}
