package sunset.leetcode;

public class P0092_ReverseLinkedList2 {

    public static void main(String[] args) {
        int[] inputList = new int[]{1, 2, 3, 4, 5};
        int left = 1;
        int right = 5;

        ListNode result = new P0092_ReverseLinkedList2().new Solution().reverseBetween(makeInput(inputList), left, right);
        printListNode(result);
    }

    /**
     * 설명<br>
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 0ms / 41.51MB <br>
     */
    class Solution {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            if (left == right) {
                return head;
            }

            ListNode preLeftPointer; // null 가능
            ListNode leftPointer;
            ListNode rightPointer;
            ListNode postRightPointer; // null 가능

            ListNode pre, cur, post;
            // head(1) 부터 시작
            pre = null;
            cur = head;
            post = cur.next;

            // left 까지 이동
            for (int i = 1; i < left; ++i) {
                pre = cur;
                cur = post;
                post = post.next;
            }
            preLeftPointer = pre;
            leftPointer = cur;

            // right 까지 이동
            for (int i = left; i < right; ++i) {
                pre = cur;
                cur = post;
                post = post.next;

                cur.next = pre;
            }
            rightPointer = cur;
            postRightPointer = post;

            if (preLeftPointer != null) {
                preLeftPointer.next = rightPointer;
            }
            leftPointer.next = postRightPointer;

            if (preLeftPointer != null) {
                return head;
            } else {
                return rightPointer;
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
