package sunset.problem;

/**
 * 문제 유형: DP<br>
 * 난이도: lv2<br>
 *
 * @see <a href = "https://school.programmers.co.kr/learn/courses/30/lessons/389480">링크</a>
 */
public class P389480 {

    public static void main(String[] args) {
        P389480.Solution solution = new P389480().new Solution();
        int[][] info = {
                {1, 2},
                {2, 3},
                {2, 1}
        };
        int result = solution.solution(info, 4, 4);
        System.out.println(result);
    }

    class Solution {

        private static final int NOT_MEMORY = -10;

        private int[][] info;
        private int n;
        private int m;

        private int[][][] memory;

        /**
         *
         * @param info  [i][0] 은 훔칠때 A 가 남기는 흔적수, [i][1] 은 훔칠때 B 가 남기는 흔적수
         * @param n     A 도둑 잡히는 흔적 수
         * @param m     B 도둑 잡히는 흔적 수
         * @return
         */
        public int solution(int[][] info, int n, int m) {
            this.info = info;
            this.n = n;
            this.m = m;
            this.memory = new int[info.length][n][m + 1];

            // 초기화
            for (int i = 0; i < info.length; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k <= m; k++) {
                        memory[i][j][k] = NOT_MEMORY;
                    }
                }
            }

            int result = solveRecursive(0, 0, m);
            return result == Integer.MAX_VALUE ? -1 : result;
        }

        /**
         *
         * @param infoIdx   선택할 info 의 인덱스(0~39)
         * @param sumA      infoIdx 전까지 A 가 훔쳐서 누적된 흔적 수(초기값 0, 최대 n-1)
         * @param bLimit    infoIdx 전까지 B 가 훔쳐서 현재 가능한 B 의 흔적 한도(초기 값 m, 최소 1)
         * @return infoIdx 부터 끝까지 다 훔쳤을 때 A 의 최소 흔적 수
         */
        private int solveRecursive(int infoIdx, int sumA, int bLimit) {
            if (sumA >= n) {
                return Integer.MAX_VALUE;
            }
            if (bLimit <= 0) {
                return Integer.MAX_VALUE;
            }
            if (infoIdx == info.length) {
                return sumA;
            }

            // 메모리에 있는 값 읽기
            if (memory[infoIdx][sumA][bLimit] != NOT_MEMORY) {
                return memory[infoIdx][sumA][bLimit];
            }

            int caseA = solveRecursive(infoIdx + 1, info[infoIdx][0] + sumA, bLimit);
            int caseB = solveRecursive(infoIdx + 1, sumA, bLimit - info[infoIdx][1]);
            int result = Math.min(caseA, caseB);

            // 메모이제이션
            memory[infoIdx][sumA][bLimit] = result;

            return result;
        }
    }
}
