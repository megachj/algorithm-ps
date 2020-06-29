package i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/SNAIL
 *
 * 상태: 성공
 */
public class bot_SNAIL {

    public static double[][] cache = new double[1001][1001];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<Double> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            int n, m;
            String[] input = br.readLine().trim().split("\\s+");
            n = Integer.parseInt(input[0]);
            m = Integer.parseInt(input[1]);

            initCache(n, m);
            results.add(snail(n, m));
        }

        results.forEach(result -> {
            System.out.println(result);
        });
    }

    // n 미터를 m 일 동안 오를 확률
    public static double snail(int n, int m) {
        if (n <= 0) {
            return 1; // n 미터를 다 올랐으니 확률 1
        } else if (m == 0) {
            return 0; // n 미터를 다 오르지 못했는데, 일자를 다 써버렸다면 확률 0
        }

        if (cache[n][m] >= 0) {
            return cache[n][m];
        }

        double result = 0.75 * snail(n-2, m-1) + 0.25 * snail(n-1, m-1);
        cache[n][m] = result;

        return result;
    }

    public static void initCache(int n, int m) {
        for (int i = 0; i <= n; ++i)
            for (int j = 0; j <= m; ++j)
                cache[i][j] = -1;
    }
}
