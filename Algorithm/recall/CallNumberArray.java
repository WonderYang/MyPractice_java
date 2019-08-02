package www.yy.recall;

import sun.awt.geom.AreaOp;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * @Time : Created in 12:10 2019/8/2
 */
public class CallNumberArray {
    public static void main(String[] args) {
        CallNumberArray call = new CallNumberArray();
        Scanner scanner = new Scanner(System.in);
        String sca = scanner.nextLine();
        List<String> res = call.letterCombinations(sca);
        System.out.println(res);
    }
    public List<String> letterCombinations(String digits) {
        String[] str = new String[digits.length()];
        String[] phoneNum = {"abc","def","ghi","jkl","mno","pqrs",
                "tuv","wxyz"};
        for(int i=0; i<digits.length(); i++) {
            char s = digits.charAt(i);
            str[i] = phoneNum[s-'0'-2];
        }
        List<String> list = new ArrayList<>();
        recall(str, 0, list, "");
        return list;
    }
    //回溯方法
    public List<String> recall(String[] str, int i, List<String> list, String temp) {
        //当前组合的字符串长度还不够
        if(i < str.length-1) {
            for(int j=0; j<str[i].length(); j++) {
                list = recall(str, i+1, list,temp+str[i].charAt(j));
            }
        }else {
            for(int j=0; j<str[i].length(); j++) {
                list.add(temp+str[i].charAt(j));
            }
        }
        return list;
    }
}
