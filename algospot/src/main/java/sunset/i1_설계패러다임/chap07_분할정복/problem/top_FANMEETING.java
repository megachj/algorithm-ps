package sunset.i1_설계패러다임.chap07_분할정복.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * https://www.algospot.com/judge/problem/read/FANMEETING
 *
 * 상태: 포기
 */
public class top_FANMEETING {

    private static int C;
    private static String[] MEMBERS;
    private static String[] FANS;

    public static void main(String[] args) throws Exception {
        inputProblem();

        for (int i = 0; i < FANS.length; ++i) {
        }
    }

    private static void inputProblem() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        C = Integer.parseInt(br.readLine());
        MEMBERS = new String[C];
        FANS = new String[C];

        for(int i = 0; i < C; ++i) {
            MEMBERS[i] = br.readLine();
            FANS[i] = br.readLine();
        }
    }
}
