package sunset.i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/POLY
 *
 * 상태: 성공
 */
public class mid_POLY {

    public static int[][][] cache = new int[101][101][101];

    public static final int DIV = 10_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            int n = Integer.parseInt(br.readLine().trim());
            initCache(n);

            results.add(solve(n));
        }

        results.forEach(result -> {
            System.out.println(result);
        });
    }

    public static int solve(int n) {
        int result = 1; // poly(n, n, 0) 1가지
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j <= n - i; ++j) {
                result = (result + poly(n, i, j)) % DIV;
            }
        }

        return result;
    }

    /*
    NOTE: 점화식
     - poly(n, first, second) = (first + second - 1) * (poly(n-first, second, 1) + poly(n-first, second, 2) + ... + poly(n-first, second, n-first-second))
     */
    public static int poly(int n, int first, int second) {
        if (cache[n][first][second] >= 0)
            return cache[n][first][second];

        if (n == first + second) {
            cache[n][first][second] = n - 1;
            return n - 1;
        }

        int result = 0;
        for (int third = 1; third <= n-first-second; ++third) {
            result = (result + poly(n-first, second, third)) % DIV;
        }
        result = ((first + second - 1) * result) % DIV;

        cache[n][first][second] = result;
        return result;
    }

    public static void initCache(int n) {
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                for (int k = 0; k <= n; ++k) {
                    cache[i][j][k] = -1;
                }
            }
        }
    }
}
