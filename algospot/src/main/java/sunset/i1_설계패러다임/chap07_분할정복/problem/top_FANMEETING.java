package sunset.i1_설계패러다임.chap07_분할정복.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * https://www.algospot.com/judge/problem/read/FANMEETING
 *
 * 상태: 오답
 */
public class top_FANMEETING {

    private static int C;
    private static String[] MEMBERS_STR;
    private static String[] FANS_STR;

    // F 여자는 false, M 남자는 true
    private static boolean[] members;
    private static boolean[] fans;

    public static void main(String[] args) throws Exception {
        inputProblem();

        for (int i = 0; i < FANS_STR.length; ++i) {
            solveUnitProblem(i);
        }
    }

    private static void inputProblem() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        C = Integer.parseInt(br.readLine());
        MEMBERS_STR = new String[C];
        FANS_STR = new String[C];

        for(int i = 0; i < C; ++i) {
            MEMBERS_STR[i] = br.readLine();
            FANS_STR[i] = br.readLine();
        }
    }

    private static void solveUnitProblem(int c) throws Exception {
        int membersSize = MEMBERS_STR[c].length();
        int fansSize = FANS_STR[c].length();

        members = new boolean[membersSize];
        fans = new boolean[fansSize];

        for (int i = 0; i < membersSize; ++i) {
            members[i] = MEMBERS_STR[c].charAt(i) != 'F';
        }
        for (int i = 0; i < fansSize; ++i) {
            fans[i] = FANS_STR[c].charAt(i) != 'F';
        }

        int result = 0;
        for (int i = 0; i < fans.length; ++i) {
            int fansEnd = i + members.length - 1;
            if (fansEnd >= fans.length) {
                break;
            }
            boolean isAllNotHugs = getResult(0, members.length-1, i, fansEnd);
            if (!isAllNotHugs) {
                result++;
            }
        }
        System.out.printf("%d\n", result);
    }

    /**
     * 해당 멤버가 모두 포옹을 했는지를 나타낸다.
     * false: 모두 포옹함.
     * true: 악수 한 멤버가 존재.
     */
    private static boolean getResult(
            int membersStart,
            int membersEnd,
            int fansStart,
            int fansEnd
    ) {
        if (membersEnd - membersStart < 10) {
            boolean result = false;
            for (int i = 0; i <= membersEnd-membersStart; ++i) {
                result = result || (members[membersStart + i] && fans[fansStart + i]);
                if (result) {
                    break;
                }
            }
            return result;
        }

        int membersMiddle = (membersStart + membersEnd) / 2;
        int fansMiddle = (fansStart + fansEnd) / 2;

        return getResult(membersStart, membersMiddle, fansStart, fansMiddle) ||
                getResult(membersMiddle+1, membersEnd, fansMiddle+1, fansEnd);
    }
}
