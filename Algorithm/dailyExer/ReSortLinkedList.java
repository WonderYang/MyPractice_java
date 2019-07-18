package www.yy.exer.day20;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/0e27e0b064de4eacac178676ef9c9d70
 * 来源：牛客网
 *
 * 编写代码，以给定值x为基准将链表分割成两部分，所有小于x的结点排在大于或等于x的结点之前
 * 给定一个链表的头指针 ListNode* pHead，请返回重新排列后的链表的头指针。注意：分割以后保持原来的数据顺序不变。
 * @Time : Created in 22:00 2019/7/17
 */


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}
public class ReSortLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        ListNode head1 = new ListNode(6);
        ListNode head2 = new ListNode(8);
        ListNode head3 = new ListNode(1);
        ListNode head4 = new ListNode(2);
        head.next = head1;
        head1.next = head2;
        head2.next = head3;
        head3.next = head4;
        ListNode listNode = partition(head,3);
        for(ListNode te=listNode;te!=null;te=te.next) {
            System.out.println(te.val);
        }

    }
    //核心算法
    public static ListNode partition(ListNode pHead, int x) {
        if(pHead == null) {
            return null;
        }
        ListNode listNodeA = null;
        ListNode tempA = listNodeA;
        ListNode listNodeB = null;
        ListNode tempB = listNodeB;
        for(ListNode temp=pHead; temp!=null; temp=temp.next) {
            if(temp.val < x) {
                if(listNodeA == null) {
                    listNodeA = new ListNode(temp.val);
                    tempA = listNodeA;
                }else {
                    ListNode listNode = new ListNode(temp.val);
                    tempA.next = listNode;
                    tempA = tempA.next;
                }
            }else {
                if(listNodeB == null) {
                    listNodeB = new ListNode(temp.val);
                    tempB = listNodeB;
                }else {
                    ListNode listNode = new ListNode(temp.val);
                    tempB.next = listNode;
                    tempB = tempB.next;
                }
            }
        }
        if(listNodeA != null) {
            if(listNodeB != null) {
                tempA.next = listNodeB;
            }
            return listNodeA;
        }
        return listNodeB;
    }

}
