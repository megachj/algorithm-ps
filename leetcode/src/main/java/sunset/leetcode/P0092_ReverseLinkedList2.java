package sunset.leetcode;

public class P0092_ReverseLinkedList2 {

    public static void main(String[] args) {
        int[] inputList = new int[]{1, 2, 3, 4, 5};
        int left = 2;
        int right = 4;

        ListNode result = new P0092_ReverseLinkedList2().new Solution().reverseBetween(makeInput(inputList), left, right);
        printListNode(result);
    }

    /**
     * 설명<br>
     * - 시간복잡도: <br>
     * - 공간복잡도: <br>
     * - 결과: ms / MB <br>
     */
    class Solution {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            if (left == right) {
                return head;
            }

            ListNode leftBeforePointer; // null 가능
            ListNode leftPointer;
            ListNode rightPointer;
            ListNode rightAfterPointer; // null 가능

            // leftBefore 찾기
            ListNode cur = null;
            int position = 0;
            while (position < left - 1) {
                cur = cur == null ? head : cur.next;
                position++;
            }
            leftBeforePointer = cur;

            // left 찾기
            position++;
            leftPointer = leftBeforePointer == null ? head : leftBeforePointer.next;

            // left+1 부터 right 까지 순서 바꾸기
            ListNode prev = leftPointer;
            position++;
            cur = leftPointer.next;
            ListNode next = leftPointer.next;
            for (; position <= right; ++position) {
                cur.next = prev;
                prev = cur;
                cur = next;
                next = next != null ? next.next : null;
            }
            // cur 가 right
            rightPointer = cur;
            if (leftBeforePointer != null) {
                leftBeforePointer.next = rightPointer;
            }
            // next 가 rightAfter
            rightAfterPointer = next;
            leftPointer.next = rightAfterPointer;

            if (left == 1) {
                return rightPointer;
            } else {
                return head;
            }
        }
    }

    private static ListNode makeInput(int[] inputs) {
        ListNode head = new ListNode(inputs[0]);
        ListNode pointer = head;
        for (int i = 1; i < inputs.length; ++i) {
            pointer.next = new ListNode(inputs[i]);
            pointer = pointer.next;
        }
        return head;
    }

    private static void printListNode(ListNode listNode) {
        while (listNode != null) {
            System.out.printf("%d, ", listNode.val);
            listNode = listNode.next;
        }
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
