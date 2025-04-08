package sunset.leetcode;

public class P0024_SwapNodesInPairs {

    public static void main(String[] args) {
        int[] inputList1 = new int[] {1, 2, 3, 4, 5, 6};
        ListNode list1 = makeInput(inputList1);

        ListNode result = new P0024_SwapNodesInPairs().new Solution().swapPairs(list1);
        printListNode(result);
    }

    /**
     * 설명:
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 0ms / 41.64MB<br>
     */
    class Solution {
        public ListNode swapPairs(ListNode head) {
            if (head == null) {
                return null;
            }
            if (head.next == null) {
                return head;
            }

            ListNode p1 = null;
            ListNode p2 = head;
            ListNode p3 = p2.next;
            ListNode newHead = p3;
            while (p2 != null && p3 != null) {
                // 스왑
                if (p1 != null) {
                    p1.next = p3;
                }
                p2.next = p3.next;
                p3.next = p2;

                // p1, p2, p3 순서대로 재조정
                p1 = p2;
                p2 = p1.next;
                if (p2 != null) {
                    p3 = p2.next;
                }
            }
            return newHead;
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
