package bin_search_tree;

import java.sql.CallableStatement;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:43 2019/5/30
 */
public class Test2 {
    public static void main(String[] args) {


    }
}


//  class TreeNode {
//      int val;
//      TreeNode left;
//      TreeNode right;
//      TreeNode(int x) { val = x; }
//  }
//
//class Test2 {
//    public String tree2str(TreeNode t) {
//        StringBuilder s = new StringBuilder();
//        fun(t,s);
//        return s.toString();
//    }
//    public static void fun(TreeNode node, StringBuilder s) {
//        if(node == null) {
//            return ;
//        }
//        s.append(node.val);
//        if(node.left != null) {
//            s.append("(");
//            //s.append(node.val);
//            fun(node.left, s);
//            s.append(")");
//        }else {
//            //此时左子树为空且右子树不为空
//            if (node.right != null)
//            s.append("()");
//        }
//        if(node.right != null) {
//            s.append("(");
//            fun(node.right, s);
//            s.append(")");
//        }
//
//    }
//}
