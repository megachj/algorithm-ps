package sunset.leetcode;

import sunset.leetcode.listnode.ListNode;

import static sunset.leetcode.listnode.ListNodeUtils.convertToListNode;

public class P0021_MergeTwoSortedLists {

    public static void main(String[] args) {
        int[] inputList1 = new int[] {1, 2, 4};
        int[] inputList2 = new int[] {1, 3, 4};

        ListNode list1 = convertToListNode(inputList1);
        ListNode list2 = convertToListNode(inputList2);

        ListNode result = new P0021_MergeTwoSortedLists().new Solution().mergeTwoLists(list1, list2);
    }

    /**
     * 설명: 재귀 함수를 이용해 두 리스트를 병합한다.
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 0ms / 42.47MB<br>
     */
    class Solution {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            return merge(list1, list2);
        }

        private ListNode merge(ListNode list1, ListNode list2) {
            if (list1 == null) {
                return list2;
            }
            if (list2 == null) {
                return list1;
            }

            if (list1.val <= list2.val) {
                list1.next = merge(list1.next, list2);
                return list1;
            } else {
                list2.next = merge(list1, list2.next);
                return list2;
            }
        }
    }
}
