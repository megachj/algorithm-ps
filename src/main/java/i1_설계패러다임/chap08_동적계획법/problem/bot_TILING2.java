package i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/TILING2
 *
 * 상태: 성공
 */
public class bot_TILING2 {

    public static final int DIV = 1_000_000_007;

    public static int[] cache = new int[101];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        initCache();

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            int n = Integer.parseInt(br.readLine().trim());
            results.add(count(n));
        }

        results.forEach(result -> {
            System.out.println(result);
        });
    }

    public static int count(int n) {
        if (cache[n] >= 0)
            return cache[n];

        int result = (count(n-1) + count(n-2)) % DIV;
        cache[n] = result;

        return result;
    }

    public static void initCache() {
        cache[0] = 0;
        cache[1] = 1;
        cache[2] = 2;
        for (int i = 3; i <= 100; ++i)
            cache[i] = -1;
    }
}
