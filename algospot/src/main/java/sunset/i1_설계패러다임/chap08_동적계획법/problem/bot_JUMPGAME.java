package sunset.i1_설계패러다임.chap08_동적계획법.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/JUMPGAME
 *
 * 상태: 성공
 */
public class bot_JUMPGAME {

    public static int[][] cache = new int[100][100];

    public static int[][] board;

    public static int n;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<String> results = new ArrayList<>(C);

        for (int c = 0; c < C; ++c) {
            initCache();
            n = Integer.parseInt(br.readLine().trim());

            // board 생성
            board = new int[n][n];
            for (int i = 0; i < n; ++i) {
                String[] input = br.readLine().trim().split("\\s+");
                for (int j = 0; j < n; ++j) {
                    board[i][j] = Integer.parseInt(input[j]);
                }
            }

            int result = solve(0, 0);
            if (result == 1) {
                results.add("YES");
            } else {
                results.add("NO");
            }
        }

        for (String result: results)
            System.out.println(result);
    }

    // (i, j) 에서 (n-1, n-1) 에 도달할 수 있으면 리턴(1), 아니면 리턴(0)
    public static int solve(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            return 0;
        }

        if (cache[i][j] >= 0) {
            return cache[i][j];
        }

        if (i == n-1 && j == n-1) {
            cache[i][j] = 1;
            return 1;
        }

        int right = solve(i, j + board[i][j]);
        int bot = solve(i + board[i][j], j);

        int result = right + bot >= 1 ? 1 : 0;
        cache[i][j] = result;

        return result;
    }

    public static void initCache() {
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                cache[i][j] = -1;
            }
        }
    }
}
