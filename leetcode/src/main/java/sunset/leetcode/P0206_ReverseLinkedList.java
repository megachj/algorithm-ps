package sunset.leetcode;

import sunset.leetcode.support.datastructure.ListNode;

import static sunset.leetcode.support.datastructure.ListNodeUtils.convertToListNode;

public class P0206_ReverseLinkedList {

    public static void main(String[] args) {
        int[] inputList1 = new int[] {1, 2, 3, 4, 5};
        ListNode list1 = convertToListNode(inputList1);

        ListNode result = new P0206_ReverseLinkedList().new Solution().reverseList(list1);
    }

    /**
     * 설명: 재귀를 이용해 리스트를 뒤집는다.
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 0ms / 42.90MB<br>
     */
    class Solution {
        public ListNode reverseList(ListNode head) {
            return reverse(null, head);
        }

        // 반대로 돌렸을 때 헤드
        private ListNode reverse(ListNode prev, ListNode cur) {
            if (cur == null) {
                return prev;
            }
            ListNode head = reverse(cur, cur.next);
            cur.next = prev;

            return head;
        }
    }
}
