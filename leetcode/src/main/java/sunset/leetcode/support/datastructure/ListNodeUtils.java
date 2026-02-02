package sunset.leetcode.support.datastructure;

public class ListNodeUtils {

    public static ListNode convertToListNode(int[] inputs) {
        if (inputs == null || inputs.length == 0) {
            return null;
        }

        ListNode head = new ListNode(inputs[0]);
        ListNode pointer = head;
        for (int i = 1; i < inputs.length; ++i) {
            pointer.next = new ListNode(inputs[i]);
            pointer = pointer.next;
        }
        return head;
    }

    public static ListNode[] convertToListNodeArray(int[][] inputs) {
        if (inputs == null || inputs.length == 0) {
            return null;
        }

        ListNode[] listNodeArray = new ListNode[inputs.length];
        for (int i = 0; i < inputs.length; ++i) {
            listNodeArray[i] = convertToListNode(inputs[i]);
        }
        return listNodeArray;
    }

    public static void printListNode(ListNode listNode) {
        while (listNode != null) {
            System.out.printf("%d, ", listNode.val);
            listNode = listNode.next;
        }
    }
}
