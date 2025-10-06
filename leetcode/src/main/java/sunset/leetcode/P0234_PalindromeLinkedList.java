package sunset.leetcode;

import sunset.leetcode.listnode.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class P0234_PalindromeLinkedList {

    public static void main(String[] args) {
        int[] input = new int[]{1, 2, 2, 2};
        ListNode head = makeInput(input);

        boolean result = new P0234_PalindromeLinkedList().new Solution3().isPalindrome(head);
        System.out.println(result);
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

    /**
     * 설명: 스택에 넣어 처리
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 11ms / 65.66MB<br>
     */
    class Solution1 {
        public boolean isPalindrome(ListNode head) {
            Deque<Integer> stack = new ArrayDeque<>();

            ListNode pointer = head;
            while (pointer != null) {
                stack.push(pointer.val);
                pointer = pointer.next;
            }

            pointer = head;
            while (pointer != null) {
                if (pointer.val != stack.pop()) {
                    return false;
                }
                pointer = pointer.next;
            }
            return true;
        }
    }

    /**
     * 설명: 데크에 넣어 처리
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 11ms / 66.06MB<br>
     */
    class Solution2 {
        public boolean isPalindrome(ListNode head) {
            Deque<Integer> deque = new ArrayDeque<>();

            ListNode pointer = head;
            while (pointer != null) {
                deque.addLast(pointer.val);
                pointer = pointer.next;
            }

            while (deque.size() > 1) {
                if (!deque.pollFirst().equals(deque.pollLast())) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 설명: 러너를 이용한 풀이
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 6ms / 69.02MB<br>
     */
    class Solution3 {
        public boolean isPalindrome(ListNode head) {
            // 원소가 1개인 경우
            if (head.next == null) {
                return true;
            }
            // 원소가 2개인 경우
            if (head.next.next == null) {
                return head.val == head.next.val;
            }

            ListNode runner = head;
            ListNode runnerX2 = head.next;

            while (runnerX2 != null && runnerX2.next != null) {
                runner = runner.next;
                runnerX2 = runnerX2.next.next;
            }

            ListNode reverseNodeStart;
            if (runnerX2 != null) {
                // 이 경우는 짝수일 때
                reverseNodeStart = runner.next;
                runner.next = null;
            } else {
                // 이 경우는 홀수일 때
                reverseNodeStart = runner;
            }

            ListNode tail = reverse(reverseNodeStart);

            while(head != null && tail != null) {
                if (head.val != tail.val) {
                    return false;
                }
                head = head.next;
                tail = tail.next;
            }
            return true;
        }

        private ListNode reverse(ListNode head) {
            // 0 개일 때
            if (head == null) {
                return null;
            }

            // 1 개일 때
            if (head.next == null) {
                return head;
            }

            // 2 개 이상일 때
            ListNode first = head;
            ListNode second = first.next;
            ListNode third = second.next;

            first.next = null;
            while (second != null) {
                second.next = first;
                first = second;
                second = third;
                if (third != null) {
                    third = third.next;
                }
            }

            return first;
        }
    }
}
