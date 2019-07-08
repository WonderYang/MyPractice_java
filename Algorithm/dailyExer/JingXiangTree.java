package www.yy.exer.day17;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/564f4c26aa584921bc75623e48ca3011
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
 *
 * 输入描述:
 * 二叉树的镜像定义：源二叉树
 *     	    8
 *     	   /  \
 *     	  6   10
 *     	 / \  / \
 *     	5  7 9 11
 *     	镜像二叉树
 *     	    8
 *     	   /  \
 *     	  10   6
 *     	 / \  / \
 *     	11 9 7  5
 * @Time : Created in 21:33 2019/7/8
 */
 class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
public class JingXiangTree {

        public void Mirror(TreeNode root) {
            if(root == null) {
                return;
            }else {
                TreeNode temp = root.left;   //注意必须交换节点，交换值是不行的
                root.left = root.right;
                root.right = temp;
                Mirror(root.left);
                Mirror(root.right);
            }
        }

}
