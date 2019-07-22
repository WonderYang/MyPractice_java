package www.yy.day22;

import java.util.HashMap;
import java.util.List;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/ed85a09f0df047119e94fb3e5569855a
 * 来源：牛客网
 *
 * 有两个用链表表示的整数，每个结点包含一个数位。这些数位是反向存放的，也就是个位排在链表的首部。编写函数对
 * 这两个整数求和，并用链表形式返回结果。
 * 给定两个链表ListNode* A，ListNode* B，请返回A+B的结果(ListNode*)。
 *
 * 测试样例：
 * {1,2,3},{3,2,1}
 * 返回：{4,4,4}
 * @Time : Created in 16:22 2019/7/19
 */

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
public class LinkedAdd {
    static ListNode head = new ListNode(-1);
    public static ListNode plusAB(ListNode a, ListNode b) {
        ListNode temp = head;
        int flag = 0;
        while(true) {
            if(a!=null && b!=null) {
                int val = a.val+b.val + flag;
                if(val >= 10) {
                    val -= 10;
                    flag = 1;
                }else {
                    flag = 0;
                }
                temp = headInsert(temp,val);
                a = a.next;
                b = b.next;
            }else if(a==null && b==null) {
                if(flag == 1) {
                    temp = headInsert(temp,1);
                }
                return head.next;
            }else if(a!=null) {
                //此时a不为空，但b为空了
                int val = a.val + flag;
                if(val >= 10) {
                    val -= 10;
                    flag = 1;
                }else {
                    flag = 0;
                }
                temp = headInsert(temp,val);
                a = a.next;
            }else {
                //此时b不为空，但a为空了
                int val = b.val + flag;
                if(val >= 10) {
                    val -= 10;
                    flag = 1;
                }else {
                    flag = 0;
                }
                temp = headInsert(temp,val);
                b = b.next;
            }
        }
    }
    public static ListNode headInsert(ListNode temp, int val) {
        ListNode newNode = new ListNode(val);
        temp.next = newNode;
        temp = temp.next;
        return temp;
    }
}

/**
 * 下面这种写法思路跟我上免得一样，但简洁了许多
 */
//    public ListNode plusAB(ListNode a, ListNode b) {
//        // write code here
//        ListNode resultHead = new ListNode(-1);
//        ListNode resultCurrent = resultHead;
//        int addToNextDigit = 0;
//        while (a != null || b != null || addToNextDigit != 0) {
//            int aVal = a != null ? a.val : 0;
//            int bVal = b != null ? b.val : 0;
//
//            int sum = aVal + bVal + addToNextDigit;
//            int nodeDigit = sum % 10;
//            addToNextDigit = sum / 10;
//
//            resultCurrent.next = new ListNode(nodeDigit);
//            resultCurrent = resultCurrent.next;
//
//            a = a != null ? a.next : null;
//            b = b != null ? b.next : null;
//        }
//        return resultHead.next;
//    }
