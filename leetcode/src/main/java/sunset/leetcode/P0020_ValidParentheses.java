package sunset.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class P0020_ValidParentheses {

    public static void main(String[] args) {
        String input = "([])";

        boolean result = new P0020_ValidParentheses().new Solution().isValid(input);
        System.out.println(result);
    }

    /**
     * 설명: (선셋풀이) 스택을 이용해 검증한다.<br>
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 3ms / 41.74MB<br>
     */
    class Solution {

        private final Map<Character, Character> closedToOpen;

        public Solution() {
            closedToOpen = new HashMap<>(3);
            closedToOpen.put(')', '(');
            closedToOpen.put('}', '{');
            closedToOpen.put(']', '[');
        }

        public boolean isValid(String s) {
            try {
                Deque<Character> stack = new ArrayDeque<>(s.length());
                for (int i = 0; i < s.length(); ++i) {
                    char c = s.charAt(i);
                    if (c == '(' || c == '{' || c == '[') {
                        stack.push(c);
                    } else if (c == ')' || c == '}' || c == ']') {
                        char expected = closedToOpen.get(c);
                        char actual = stack.pop();
                        if (expected != actual) {
                            return false;
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }

                return stack.isEmpty();
            } catch (Exception e) {
                return false;
            }
        }
    }
}
