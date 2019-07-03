package www.yy.exer.StackApplication;

import java.util.Stack;

/**
 * @Author : YangY
 * @Description : 看符号是否成对
 * @Time : Created in 22:45 2019/5/22
 */
public class Marry {
    public static boolean isValid(String s) {
        char[] arr = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<arr.length; i++) {
            if(stack.isEmpty()) {
                stack.push(arr[i]);
            }else {
                //如果下一个符号和前一个符号匹配，就让前一个符号出栈；
                if(isSame(arr[i],stack.peek())) {
                    stack.pop();
                }else {
                    stack.push(arr[i]);
                }
            }
        }
        if(stack.isEmpty()) {
            return true;
        }
        return false;
    }
    public static boolean isSame(char a,char b){
        if (a =='{'&& b=='}' || a=='}'&&b=='{') {
            return true;
        }else if(a =='['&& b==']' || a==']'&&b=='[') {
            return true;
        }else if(a =='('&& b==')' || a==')'&&b=='(') {
            return true;
        }else {
            return false;
        }

    }

    public static void main(String[] args) {
        String s = "{{()[{}]}}}";
        System.out.println(isValid(s));
    }
}
