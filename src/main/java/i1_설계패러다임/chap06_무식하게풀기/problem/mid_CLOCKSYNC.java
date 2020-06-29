package i1_설계패러다임.chap06_무식하게풀기.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/CLOCKSYNC
 *
 * 상태: 성공
 */
public class mid_CLOCKSYNC {

    public static int[] hourArray = new int[16];

    public static final List<List<Integer>> SWITCH = new ArrayList<>(10);

    public static final int MAX_VALUE = 60000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] input;

        initSwitch();

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> resultList = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            input = br.readLine().trim().split("\\s+");
            for (int i = 0; i < 16; ++i) {
                hourArray[i] = Integer.parseInt(input[i]);
            }

            int result = findMin(0);
            if (result >= MAX_VALUE)
                result = -1;
            resultList.add(result);
        }

        for (int result: resultList) {
            System.out.println(result);
        }
    }

    public static int findMin(int k) {
        if (isAllRight())
            return 0;

        int min = MAX_VALUE;
        if (k >= 10)
            return min;

        int cnt = 0;
        for (int i = 0; i < 4; ++i) {
            if (i > 0) {
                pushSwitch(k);
                cnt++;
            }
            min = Math.min(min, cnt + findMin(k+1));
        }

        // 원상복구
        pushSwitch(k);

        return min;
    }

    public static boolean isAllRight() {
        for (int i = 0; i < 16; ++i) {
            if (hourArray[i] != 12)
                return false;
        }

        return true;
    }

    public static void pushSwitch(int k) {
        if (k < 0 || k >= 10)
            return;

        for (Integer a : SWITCH.get(k)) {
            hourArray[a] = hourArray[a] == 12 ? 3 : hourArray[a] + 3;
        }
    }

    public static void initSwitch() {
        SWITCH.add(Arrays.asList(0, 1, 2));
        SWITCH.add(Arrays.asList(3, 7, 9, 11));
        SWITCH.add(Arrays.asList(4, 10, 14, 15));
        SWITCH.add(Arrays.asList(0, 4, 5, 6, 7));
        SWITCH.add(Arrays.asList(6, 7, 8, 10, 12));
        SWITCH.add(Arrays.asList(0, 2, 14, 15));
        SWITCH.add(Arrays.asList(3, 14, 15));
        SWITCH.add(Arrays.asList(4, 5, 7, 14, 15));
        SWITCH.add(Arrays.asList(1, 2, 3, 4, 5));
        SWITCH.add(Arrays.asList(3, 4, 5, 9, 13));
    }
}
