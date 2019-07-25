package www.yy.day23;

import sun.reflect.generics.tree.Tree;

/**
 * @Author : YangY
 * @Description : 判断一个二叉树是否为平衡二叉树；
 * 平衡二叉树的定义：任何一个节点的左右子树的高度差不大于1
 * @Time : Created in 13:11 2019/7/25
 */


class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {
        this.val = val;
    }
}
public class BalanceTree {
    public boolean isBalance(TreeNode root) {
        if(root == null) {
            return true;
        }else {
            int leftDeep = deep(root.left);
            int rightDeep = deep(root.right);
            if(Math.abs(leftDeep-rightDeep) > 1) {
                return false;
            }else {
                return isBalance(root.left) && isBalance(root.right);
            }
        }
    }
    //求该节点的最大深度
    public static int deep(TreeNode treeNode) {
        return treeNode==null?0:Math.max(deep(treeNode.left),deep(treeNode.right))+1;
    }
}
