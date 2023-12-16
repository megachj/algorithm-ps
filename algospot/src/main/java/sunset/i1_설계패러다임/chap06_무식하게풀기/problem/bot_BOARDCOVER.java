package sunset.i1_설계패러다임.chap06_무식하게풀기.problem;

import sunset.library.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/BOARDCOVER
 *
 * 상태: 성공
 */
public class bot_BOARDCOVER {

    private static final char BLACK = '#';

    private static final char WHITE = '.';

    private static int H, W;

    private static char[][] board;

    private static final List<List<Pair<Integer, Integer>>> shapeList = Arrays.asList(
            Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(1, 1)),
            Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(1, 0)),
            Arrays.asList(new Pair<>(0, 0), new Pair<>(1, 0), new Pair<>(1, 1)),
            Arrays.asList(new Pair<>(0, 0), new Pair<>(1, 0), new Pair<>(1, -1))
    );

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<Integer> results = new ArrayList<>(C);

        for (int c = 0; c < C; ++c) {
            String[] words = br.readLine().split("\\s+");
            H = Integer.parseInt(words[0]);
            W = Integer.parseInt(words[1]);

            board = new char[H][];
            for (int y = 0; y < H; ++y) {
                String line = br.readLine().trim();
                board[y] = line.toCharArray();
            }

            results.add(solve(0, 0));
        }

        for (Integer result : results) {
            System.out.println(result);
        }
    }

    private static int solve(int y, int x) {
        int result = 0;
        if (x == W)
            return solve(y+1, 0);
        if (y == H)
            return 1;
        if (board[y][x] == BLACK)
            return solve(y, x+1);

        for (int i = 0; i < shapeList.size(); ++i) {
            if (cover(y, x, shapeList.get(i))) {
                result += solve(y, x + 1);
                uncover(y, x, shapeList.get(i));
            }
        }

        return result;
    }

    private static boolean cover(int y, int x, List<Pair<Integer, Integer>> shape) {
        int dy, dx;
        for (Pair<Integer, Integer> dp: shape) {
            dy = dp.getKey();
            dx = dp.getValue();
            if (y+dy >= H || y+dy < 0 || x+dx >= W || x+dx < 0)
                return false;

            if (board[y+dy][x+dx] == BLACK)
                return false;
        }

        for (Pair<Integer, Integer> dp: shape) {
            dy = dp.getKey();
            dx = dp.getValue();
            board[y+dy][x+dx] = BLACK;
        }

        return true;
    }

    private static void uncover(int y, int x, List<Pair<Integer, Integer>> shape) {
        int dy, dx;
        for (Pair<Integer, Integer> dp: shape) {
            dy = dp.getKey();
            dx = dp.getValue();
            board[y+dy][x+dx] = WHITE;
        }
    }
}
