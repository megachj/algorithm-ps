package sunset.i1_설계패러다임.chap07_분할정복.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.algospot.com/judge/problem/read/QUADTREE
 *
 * 상태: 성공
 */
public class bot_QUADTREE {

    public static int idx = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int C = Integer.parseInt(br.readLine().trim());
        List<String> results = new ArrayList<>(C);
        for (int c = 0; c < C; ++c) {
            String tree = br.readLine().trim();
            idx = 0;

            results.add(compress(tree));
        }

        for (String result: results) {
            System.out.println(result);
        }
    }

    public static String compress(String tree) {
        if (tree.charAt(idx) == 'w' || tree.charAt(idx) == 'b')
            return tree.substring(idx, idx+1);

        ++idx; String a1 = compress(tree);

        ++idx; String a2 = compress(tree);

        ++idx; String a3 = compress(tree);

        ++idx; String a4 = compress(tree);

        return "x" + a3 + a4 + a1 + a2;
    }
}
