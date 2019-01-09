/**
 * 给定一个英文字符串，
 * 写一段代码找出字符串中首先出现三次的英文字母。
 * 输入描述：输入数据一个
 * 字符串，
 * 包括字母，数字 输出描述：
 * 输出首先出现三次的那个英文字符 示例：
 * 输入： Have you ever gone shopping
 * and 输出： e
 */
public class FindWord {
    public static void main(String[] args) {
        String str = "Have you ever gone shopping";
        char result = getFirstThreeChar(str);
        System.out.println(result);
    }
    public static char getFirstThreeChar(String str) {
        // str -> char[]
        char[] data = str.toCharArray();
        // 散列思想
        int[] charNum = new int[255];
        for (int i = 0;i < data.length;i++) {
            char c = data[i];
            // Have you ever gone shopping
            if (c >= 'A' && c <= 'Z'|| c >= 'a' && c<= 'z') {
                // int <-> char
                charNum[c] ++;
                if (charNum[c] == 3) {
                    return c;
                }
            }
        }
        return ' ';
    }
}