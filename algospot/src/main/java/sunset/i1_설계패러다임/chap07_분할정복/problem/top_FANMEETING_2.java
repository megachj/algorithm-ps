package sunset.i1_설계패러다임.chap07_분할정복.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * https://www.algospot.com/judge/problem/read/FANMEETING
 *
 * 상태:
 */
public class top_FANMEETING_2 {

    private static int C;
    private static String[] MEMBERS;
    private static String[] FANS;

    private static int CURRENT_TEST_NUMBER;

    public static void main(String[] args) throws Exception {
        inputParameter();

        int testCount = FANS.length;
        for (int i = 0; i < testCount; ++i) {
            solveTest(i);
        }
    }

    private static void inputParameter() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        C = Integer.parseInt(br.readLine());
        MEMBERS = new String[C];
        FANS = new String[C];

        for(int i = 0; i < C; ++i) {
            MEMBERS[i] = br.readLine();
            FANS[i] = br.readLine();
        }
    }

    private static void solveTest(int testNumber) throws Exception {
        int membersSize = MEMBERS[testNumber].length();
        int fansSize = FANS[testNumber].length();
        CURRENT_TEST_NUMBER = testNumber;

        int result = 0;
        for (int i = 0; i < fansSize; ++i) {
            int fansEnd = i + membersSize - 1;
            if (fansEnd >= fansSize) {
                break;
            }
            boolean isAllHugs = isAllHugs(0, membersSize - 1, i, fansEnd);
            if (isAllHugs) {
                result++;
            }
        }
        System.out.printf("%d\n", result);
    }

    /**
     * 해당 멤버가 모두 포옹을 했는지를 나타낸다.
     * true: 모두 포옹함.
     * false: 악수 한 멤버가 존재.
     */
    private static boolean isAllHugs(
            int membersStart,
            int membersEnd,
            int fansStart,
            int fansEnd
    ) {
        if (membersEnd - membersStart < 10) {
            for (int i = 0; i <= membersEnd-membersStart; ++i) {
                if (isNotHugs(MEMBERS[CURRENT_TEST_NUMBER].charAt(membersStart + i), FANS[CURRENT_TEST_NUMBER].charAt(fansStart + i))) {
                    return false;
                }
            }
            return true;
        }

        int membersMiddle = (membersStart + membersEnd) / 2;
        int fansMiddle = (fansStart + fansEnd) / 2;

        return isAllHugs(membersStart, membersMiddle, fansStart, fansMiddle) &&
                isAllHugs(membersMiddle+1, membersEnd, fansMiddle+1, fansEnd);
    }

    private static boolean isNotHugs(char member, char fan) {
        if (member == 'M' && fan == 'M') {
            return true;
        }
        return false;
    }
}

