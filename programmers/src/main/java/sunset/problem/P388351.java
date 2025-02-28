package sunset.problem;

/**
 * 문제 유형: 기본 구현<br>
 * 난이도: lv1<br>
 *
 * @see <a href = "https://school.programmers.co.kr/learn/courses/30/lessons/388351">링크</a>
 */
public class P388351 {

    public static void main(String[] args) {
        Solution solution = new P388351().new Solution();
        int[] schedules = {
                700, 800, 1100
        };
        int[][] timelogs = {
                {710, 2359, 1050, 700, 650, 631, 659},
                {800, 801, 805, 800, 759, 810, 809},
                {1105, 1001, 1002, 600, 1059, 1001, 1100}
        };
        int startday = 5;

        int result = solution.solution(schedules, timelogs, startday);

        System.out.println(result);
    }

    class Solution {

        /**
         * 상품을 받을 직원 수
         *
         * @param schedules 출근 희망 시각. schedules[i] 는 i+1 번째 직원이 설정한 출근 희망 시각이다.
         * @param timelogs  실제 출근 시각. timelogs[i][j] 는 i+1 번째 직원이 [j+1] 일차에 출근한 시각이다.
         * @param startday  1 은 월요일, ..., 7은 일요일에 이벤트를 시작했음을 의미
         * @return
         */
        public int solution(int[] schedules, int[][] timelogs, int startday) {
            int answer = 0;
            for (int i = 0; i < schedules.length; ++i) {
                boolean iTh = true;
                for (int j = 0; j < timelogs[i].length; ++j) {
                    // 토요일(6), 일요일(7) 은 상관없음
                    int dayMod = (startday + j) % 7;
                    if (dayMod == 6 || dayMod == 0) {
                        continue;
                    }

                    // 가능한 시간
                    int scheduleLimit = schedules[i] + 10;
                    int hour = scheduleLimit / 100;
                    int minute = scheduleLimit % 100;
                    if (minute >= 60) {
                        hour++;
                        minute -= 60;
                    }
                    scheduleLimit = hour * 100 + minute;

                    if (scheduleLimit < timelogs[i][j]) {
                        iTh = false;
                        break;
                    }
                }

                if (iTh) {
                    answer++;
                }
            }

            return answer;
        }
    }
}
