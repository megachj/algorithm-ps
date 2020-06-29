package i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://www.algospot.com/judge/problem/read/LIS
 *
 * 상태: 성공
 */
public class bot_LIS {

    public static int N;

    public static int[] cache = new int[500];

    public static List<Integer> A;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            initCache();

            N = Integer.parseInt(br.readLine().trim());
            A = Arrays.stream(br.readLine().trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            int result = 1;
            for (int i = 0; i < N; ++i) {
                result = Math.max(result, lis(i));
            }

            results.add(result);
        }

        for (Integer result: results)
            System.out.println(result);
    }


    public static int lis(int i) {
        if (cache[i] >= 0)
            return cache[i];

        int max = 1;
        for (int nextI = i + 1; nextI < N; ++nextI) {
            if (A.get(i) < A.get(nextI)) {
                max = Math.max(max, 1 + lis(nextI));
            }
        }

        cache[i] = max;
        return max;
    }

    public static void initCache() {
        for (int i = 0; i < 500; ++i)
            cache[i] = -1;
    }
}
