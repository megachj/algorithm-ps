package sunset.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class P0056_MergeIntervals {

    public static void main(String[] args) {
        // {1, 3}, {2, 6}, {8, 10}, {15, 18} -> [[1, 6], [8, 10], [15, 18]]
        // {1, 4}, {4, 5} -> [[1, 5]]
        // {4, 7}, {1, 4} -> [[1, 7]]
        int[][] inputs = new int[][]{
                {4, 7}, {1, 4}
        };
        Solution solution = new P0056_MergeIntervals().new Solution();
        int[][] outputs = solution.merge(inputs);

        System.out.print("[");
        for (int i = 0; i < outputs.length; ++i) {
            System.out.printf("[%d, %d], ", outputs[i][0], outputs[i][1]);
        }
        System.out.println("]");
    }

    class Solution {
        public int[][] merge(int[][] intervals) {
            List<int[]> sortedIntervals = convertToSortedList(intervals);

            List<int[]> mergedIntervals = new ArrayList<>();
            int prevStart = sortedIntervals.get(0)[0];
            int prevEnd = sortedIntervals.get(0)[1];
            for (int i = 1; i < sortedIntervals.size(); ++i) {
                int curStart = sortedIntervals.get(i)[0];
                int curEnd = sortedIntervals.get(i)[1];
                if (curStart <= prevEnd) {
                    prevEnd = Math.max(prevEnd, curEnd);
                } else {
                    mergedIntervals.add(new int[]{prevStart, prevEnd});

                    prevStart = curStart;
                    prevEnd = curEnd;
                }
            }
            mergedIntervals.add(new int[]{prevStart, prevEnd});

            int[][] results = new int[mergedIntervals.size()][2];
            for (int i = 0; i < mergedIntervals.size(); ++i) {
                results[i][0] = mergedIntervals.get(i)[0];
                results[i][1] = mergedIntervals.get(i)[1];
            }

            return results;
        }

        private List<int[]> convertToSortedList(int[][] intervals) {
            // TODO: 제대로 리스트로 변환되는지 확인
            return Arrays.stream(intervals)
                    .sorted(Comparator.comparingInt(a -> a[0]))
                    .collect(Collectors.toList());
        }
    }
}
