package sunset.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class P0739_DailyTemperatures {

    public static void main(String[] args) {
        int[] input = new int[]{30,30, 40};
        int[] output = new P0739_DailyTemperatures().new Solution().dailyTemperatures(input);
        System.out.println(Arrays.stream(output).boxed().collect(Collectors.toList()));
    }

    /**
     * 설명: 스택을 이용해서 해결한다.
     * - 시간복잡도: O(N)<br>
     * - 공간복잡도: O(N)<br>
     * - 결과: 27ms / 56.66MB<br>
     */
    class Solution {
        public int[] dailyTemperatures(int[] temperatures) {
            int[] results = new int[temperatures.length];

            Deque<Tuple> stack = new ArrayDeque<>();
            for (int i = 0; i < temperatures.length; ++i) {
                if (stack.isEmpty()) {
                    stack.push(new Tuple(temperatures[i], i));
                    continue;
                }

                while (!stack.isEmpty()) {
                    Tuple head = stack.pop();
                    if (head.value >= temperatures[i]) {
                        stack.push(head);
                        stack.push(new Tuple(temperatures[i], i));
                        break;
                    }

                    results[head.index] = i - head.index;
                }

                if (stack.isEmpty()) {
                    stack.push(new Tuple(temperatures[i], i));
                }
            }

            return results;
        }
    }

    public static class Tuple {
        private int value;
        private int index;

        public Tuple(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
}
