package topic3_설계패러다임.chap05_무식하게풀기.problem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * https://www.algospot.com/judge/problem/read/PICNIC
 *
 * 난이도: 하
 * 상태:
 */
public class PICNIC {
    private static ArrayList<Integer> mList;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int C = scanner.nextInt(); scanner.nextLine();
        for (int c = 0; c < C; ++c) {
            int n = scanner.nextInt();
            int m = scanner.nextInt(); scanner.nextLine();

            mList = new ArrayList<>(m);
            for (int i = 0; i < m; ++i) {
                int input = scanner.nextInt();
                mList.add(input);
            }


        }
    }
}
