package sunset.leetcode;

import java.util.*;

public class P0316_RemoveDuplicateLetters {

    public static void main(String[] args) {
        String input = "bbcaac";
        String output = new P0316_RemoveDuplicateLetters().new Solution1().removeDuplicateLetters(input);
        System.out.println(output);
    }

    /**
     * 설명: 스택을 이용한 풀이. 책보고 구현하였음.
     * - 시간복잡도: O(n^2)?<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 8ms / 43.25MB<br>
     */
    class Solution1 {

        private final Map<Character, Integer> counter = new HashMap<>(26);
        private final Set<Character> seen = new HashSet<>(26);
        private final Deque<Character> resultStack = new ArrayDeque<>();

        public String removeDuplicateLetters(String s) {
            initCounter(s);

            for (int i = 0; i < s.length(); ++i) {
                char current = s.charAt(i);
                // stack 에 들어가 있는 값이면 continue
                if (seen.contains(current)) {
                    counter.put(current, counter.get(current) - 1);
                    continue;
                }

                // stack 에 안들어갔으면 push
                // - stack 헤드보다 내가 작을때까지 pop
                // - pop 한게 아직 counter 에 남아있다면 pop 가능. 아니라면 다시 넣어줘야함.
                Deque<Character> deque = new ArrayDeque<>();
                while (!resultStack.isEmpty()) {
                    char head = resultStack.peekLast();
                    if (current < head && counter.get(head) > 0) {
                        deque.offerFirst(resultStack.pollLast());
                        seen.remove(head);
                    } else {
                        break;
                    }
                }
                pushCharacter(current);
            }

            return convertToString();
        }

        private void initCounter(String s) {
            for (char c: s.toCharArray()) {
                if (counter.containsKey(c)) {
                    counter.put(c, counter.get(c) + 1);
                } else {
                    counter.put(c, 1);
                }
            }
        }

        private void pushCharacter(char c) {
            resultStack.offerLast(c);
            counter.put(c, counter.get(c) - 1);
            seen.add(c);
        }

        private String convertToString() {
            StringBuilder sb = new StringBuilder();
            while (!resultStack.isEmpty()) {
                sb.append(resultStack.poll());
            }
            return sb.toString();
        }
    }

    /**
     * 설명: 재귀를 이용해서 풀이해보자. 직접 풀지는 못하고 책보고 풀었음.
     * - 시간복잡도: O(n^2)<br>
     * - 공간복잡도: O()<br>
     * - 결과: 61ms / 45.49MB<br>
     */
    class Solution2 {
        public String removeDuplicateLetters(String s) {
            for (char c : toSortedSet(s)) {
                String suffix = s.substring(s.indexOf(c));
                if (toSortedSet(s).equals(toSortedSet(suffix))) {
                    return c + removeDuplicateLetters(suffix.replace(String.valueOf(c), ""));
                }
            }
            return "";
        }

        private Set<Character> toSortedSet(String s) {
            Set<Character> set = new TreeSet<>(Character::compareTo);
            for (int i = 0; i < s.length(); ++i) {
                set.add(s.charAt(i));
            }
            return set;
        }
    }
}
