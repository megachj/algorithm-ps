package sunset.leetcode;

import sunset.leetcode.support.datastructure.ListNode;
import sunset.leetcode.support.datastructure.ListNodeUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

import static sunset.leetcode.support.datastructure.ListNodeUtils.printListNode;

/**
 * N = lists.length <= 10^4
 * M = lists[i].length <= 500
 * N*M = 5*10^6 = 500만
 */
public class P0023_MergeKSortedLists {

    public static void main(String[] args) {
        int[][] input = new int[][]{
                {1, 4, 5},
                {1, 3, 4},
                {2, 6},
        };

        ListNode[] list = ListNodeUtils.convertToListNodeArray(input);
        ListNode result = new P0023_MergeKSortedLists().new SolutionBook().mergeKLists(list);
        printListNode(result);
    }

    /**
     * 직접 생각한 풀이법.
     * 우선순위 큐(이진 힙)을 만들어서 모든 노드의 값을 전부 넣고, 하나씩 빼면서 ListNode 를 연결한다.
     *
     * - 시간복잡도: O(NM log(NM)) + O(NM log(NM)) = O(NM log(NM))
     * - 공간복잡도: O(NM)
     * - 결과: 5ms / 44.47MB
     */
    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null) {
                return null;
            }

            // 이진 힙에 넣기
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (int i = 0; i < lists.length; ++i) {
                ListNode pointer = lists[i];
                while (pointer != null) {
                    priorityQueue.add(pointer.val);
                    pointer = pointer.next;
                }
            }

            // 이집 힙에서 삭제하면서 결과 채우기
            if (priorityQueue.isEmpty()) {
                return null;
            }
            ListNode head = new ListNode(priorityQueue.poll());
            ListNode pointer = head;
            while (!priorityQueue.isEmpty()) {
                pointer.next = new ListNode(priorityQueue.poll());
                pointer = pointer.next;
            }

            return head;
        }
    }

    /**
     * 책에 나온 풀이법.
     * 우선순위 큐를 이용해 노드를 잘 연결한다.
     *
     * - 시간복잡도: O(NM log(N))
     * - 공간복잡도: O(N)
     * - 결과: 6ms / 44.57MB
     */
    class SolutionBook {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null) {
                return null;
            }

            PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(
                    Comparator.comparingInt(o -> o.val)
            );

            ListNode root = new ListNode(0);
            ListNode tail = root;

            for (ListNode node : lists) {
                if (node != null) {
                    priorityQueue.add(node);
                }
            }

            while (!priorityQueue.isEmpty()) {
                tail.next = priorityQueue.poll();
                tail = tail.next;
                if (tail.next != null) {
                    priorityQueue.add(tail.next);
                }
            }

            return root.next;
        }
    }
}
