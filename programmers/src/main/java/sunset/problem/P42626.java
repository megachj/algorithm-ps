package sunset.problem;

import java.util.PriorityQueue;

/**
 * 문제 유형: 힙<br>
 * 난이도: lv2<br>
 *
 * @see <a href = "https://school.programmers.co.kr/learn/courses/30/lessons/42626?language=java">링크</a>
 */
public class P42626 {

    public static void main(String[] args) {
        Solution solution = new P42626().new Solution();
        int[] scoville = {
                1, 2, 3, 9, 10, 12
        };
        int k = 7;

        int result = solution.solution(scoville, k);

        System.out.println(result);
    }

    class Solution {

        public int solution(int[] scoville, int K) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (int j : scoville) {
                priorityQueue.add(j);
            }

            int answer = 0;
            while (true) {
                int min = priorityQueue.poll();
                if (min >= K) {
                    return answer;
                }

                Integer next = priorityQueue.poll();
                if (next == null) {
                    return -1;
                }

                int newScoville = min + (next * 2);
                priorityQueue.add(newScoville);
                answer++;
            }
        }
    }
}
