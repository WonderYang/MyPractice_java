package bin_search_tree;

import bin_tree.BinTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:01 2019/5/30
 */
public class Test {
    public static void main(String[] args) {
        BinTree<Integer> binTree = new BinSearchTree<>();
        int[] arr = new int[]{5,1,8,3,4,7,9,-2};
        for(int i=0; i<arr.length; i++) {
            binTree.add(arr[i]);
        }
//        System.out.println(binTree.contains(-2));
//        System.out.println(binTree.contains(6));
//        System.out.println(binTree.getMin());
//        System.out.println(binTree.getMax());
        binTree.inOrder();
        System.out.println("---------------");
        //binTree.removeMin();
        binTree.remove(8);
        binTree.inOrder();
        //System.out.println();

    }
}
