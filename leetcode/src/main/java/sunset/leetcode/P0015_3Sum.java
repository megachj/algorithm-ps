package sunset.leetcode;

import java.util.*;

public class P0015_3Sum {

    public static void main(String[] args) {
        Solution solution = new P0015_3Sum().new Solution();
        int[] input = new int[]{0, 0, 0};
        List<List<Integer>> output = solution.threeSum(input);
        System.out.println(output);
    }

    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            List<Integer> negatives = new ArrayList<>(nums.length);
            Set<Integer> negativeSet = new HashSet<>(nums.length);
            List<Integer> positives = new ArrayList<>(nums.length);
            Set<Integer> positiveSet = new HashSet<>(nums.length);
            int zeroCount = 0;

            for (int num : nums) {
                if (num < 0) {
                    negatives.add(num);
                    negativeSet.add(num);
                } else if (num > 0) {
                    positives.add(num);
                    positiveSet.add(num);
                } else {
                    zeroCount++;
                }
            }
            // 오름차순
            negatives.sort(Integer::compareTo);
            // 내림차순
            positives.sort((a, b) -> Integer.compare(b, a));

            Set<List<Integer>> resultSet = new HashSet<>();

            // 1) 0, 0, 0 인 경우 찾기
            if (zeroCount >= 3) {
                resultSet.add(new ArrayList<>(Arrays.asList(0, 0, 0)));
            }

            // 2) 0, neg, pos 인 경우 찾기
            if (zeroCount >= 1) {
                for (Integer negative : negatives) {
                    for (Integer positive : positives) {
                        int value = negative + positive;
                        if (value == 0) {
                            resultSet.add(new ArrayList<>(Arrays.asList(0, negative, positive)));
                        } else if (value < 0) {
                            // 양수는 내림차순이라 더 작아지므로 다음번도 음수일꺼라 종료한다.
                            break;
                        }
                    }
                }
            }

            // 3) neg, neg, pos 인 경우 찾기
            for (int i = 0; i < negatives.size() - 1; ++i) {
                for (int j = i+1; j < negatives.size(); ++j) {
                    int absValue = Math.abs(negatives.get(i) + negatives.get(j));
                    if (positiveSet.contains(absValue)) {
                        resultSet.add(new ArrayList<>(Arrays.asList(negatives.get(i), negatives.get(j), absValue)));
                    }
                }
            }

            // 4) pos, pos, neg 인 경우 찾기
            for (int i = 0; i < positives.size() - 1; ++i) {
                for (int j = i+1; j < positives.size(); ++j) {
                    int negativeValue = -1 * (positives.get(i) + positives.get(j));
                    if (negativeSet.contains(negativeValue)) {
                        resultSet.add(new ArrayList<>(Arrays.asList(positives.get(i), positives.get(j), negativeValue)));
                    }
                }
            }

            return new ArrayList<>(resultSet);
        }
    }
}
