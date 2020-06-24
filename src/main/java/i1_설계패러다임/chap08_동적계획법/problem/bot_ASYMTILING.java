package i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/ASYMTILING
 *
 * 상태: 성공
 */
public class bot_ASYMTILING {

    public static final int DIVISOR = 1_000_000_007;

    public static int[] allCache = new int[101];

    public static int[] symCache = new int[101];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        initCache();

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            int n = Integer.parseInt(br.readLine().trim());

            int result = allCases(n) - symCases(n);
            if (result < 0)
                result += DIVISOR;
            results.add(result);
        }

        for (int result: results) {
            System.out.println(result);
        }
    }

    public static int allCases(int n) {
        if (allCache[n] >= 0)
            return allCache[n];

        if (n == 0 || n == 1) {
            allCache[n] = 1;
            return 1;
        }

        int result = (allCases(n-1) % DIVISOR + allCases(n-2) % DIVISOR) % DIVISOR;
        allCache[n] = result;

        return result;
    }

    public static int symCases(int n) {
        if (symCache[n] >= 0)
            return symCache[n];

        if (n == 0 || n == 1 || n == 3) {
            symCache[n] = 1;
            return 1;
        }
        if (n == 2) {
            symCache[n] = 2;
            return 2;
        }

        int result = (symCases(n-2) % DIVISOR + symCases(n-4) % DIVISOR) % DIVISOR;
        symCache[n] = result;

        return result;
    }

    public static void initCache() {
        for (int i = 0; i < 101; ++i)
            allCache[i] = symCache[i] = -1;
    }
}
