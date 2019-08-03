package www.yy.recall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : YangY
 * @Description :输入: candidates = [2,3,5], target = 8,
 * 所求解集为:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 * @Time : Created in 18:52 2019/8/3
 */
public class ArrayTheSum {
    public static void main(String[] args) {
        ArrayTheSum arrayTheSum = new ArrayTheSum();
        int[] arr = new int[]{2,3,5};
        List<List<Integer>> lists = arrayTheSum.combinationSum(arr,8);
        System.out.println(lists);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> listAll=new ArrayList<List<Integer>>();
        List<Integer> list=new ArrayList<Integer>();
        //排序
        Arrays.sort(candidates);
        find(listAll,list,candidates,target,0);
        return listAll;
    }
    public void find(List<List<Integer>> listAll,List<Integer> tmp,int[] candidates, int target,int num){
        //递归的终点
        if(target==0){
            listAll.add(tmp);
            return;
        }
        if(target<candidates[0]) return;
        for(int i=num;i<candidates.length&&candidates[i]<=target;i++){
            List<Integer> list=new ArrayList<>(tmp);
            list.add(candidates[i]);
            //System.out.println(i+":"+list);  打印观察
            //递归运算，将i传递至下一次运算是为了避免结果重复。
            find(listAll,list,candidates,target-candidates[i],i);
        }
    }

    /**
     * 2
     * 2,2
     * 2,2,2
     * 2,2,2,2(存储)
     * 2,2,3
     * 2,2,5(这种情况不符合 candidates[i]<=target 即 ：5 <=4 ；所以回退)
     * 2,3
     * 2,3,3(存储)
     * 2,5
     * 3,3
     * 3,5
     * 5
     * 规律：每次加最后一个的同一个数，当加到超过或等于目标值时，去掉所有这个数，开始加数组的下一个数，以此类推
     */
}
