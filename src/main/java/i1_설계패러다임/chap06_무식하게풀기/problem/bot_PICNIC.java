package i1_설계패러다임.chap06_무식하게풀기.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/PICNIC
 *
 * 상태: 성공
 */
public class bot_PICNIC {

    public static boolean[][] adj;

    public static boolean[] matched;

    public static List<Integer> results;

    public static int n, m;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input;

        int C = Integer.parseInt(br.readLine().trim());
        results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            input = br.readLine().trim().split("\\s+");
            n = Integer.parseInt(input[0]);
            m = Integer.parseInt(input[1]);

            adj = new boolean[n][n];
            matched = new boolean[n];
            init();
            input = br.readLine().trim().split("\\s+");
            for (int i = 0; i < m * 2; i += 2) {
                int u, v;
                u = Integer.parseInt(input[i]);
                v = Integer.parseInt(input[i+1]);
                adj[u][v] = adj[v][u] = true;
            }

            results.add(solve(0));
        }

        for (Integer result : results) {
            System.out.println(result);
        }
    }

    public static int solve(int curIdx) {
        if (curIdx >= n)
            return 1;

        if (matched[curIdx])
            return solve(curIdx + 1);

        int result = 0;
        for (int next = curIdx+1; next < n; ++next) {
            if (adj[curIdx][next] && !matched[next]) {
                matched[curIdx] = matched[next] = true;
                result += solve(curIdx + 1);
                matched[curIdx] = matched[next] = false;
            }
        }

        return result;
    }

    public static void init() {
        for (int i = 0; i < n; ++i) {
            matched[i] = false;
            for (int j = 0; j < n; ++j) {
                adj[i][j] = false;
            }
        }
    }
}
