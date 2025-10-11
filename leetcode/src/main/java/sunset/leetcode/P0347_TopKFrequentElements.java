package sunset.leetcode;

import java.util.*;

/**
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 */
public class P0347_TopKFrequentElements {

    public static void main(String[] args) {
        int[] nums = new int[] {
                1,2,1,2,1,2,3,1,3,2
        };
        int k = 2;

        int[] result = new P0347_TopKFrequentElements().new Solution().topKFrequent(nums, k);

        for (int num: result) {
            System.out.printf("%d, ", num);
        }
    }

    /**
     * - 시간 복잡도: O(N logN)
     * - 결과: 15ms / 48.7MB
     */
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            HashMap<Integer, Integer> countMap = new HashMap<>();
            for (int num: nums) {
                if (countMap.containsKey(num)) {
                    countMap.put(num, countMap.get(num) + 1);
                } else {
                    countMap.put(num, 1);
                }
            }

            TreeMap<Integer, List<Integer>> frequencyMap = new TreeMap<>(
                    Comparator.reverseOrder()
            );
            for (Map.Entry<Integer, Integer> entry: countMap.entrySet()) {
                if (frequencyMap.containsKey(entry.getValue())) {
                    frequencyMap.get(entry.getValue()).add(entry.getKey());
                } else {
                    frequencyMap.put(entry.getValue(), new ArrayList<>());
                    frequencyMap.get(entry.getValue()).add(entry.getKey());
                }
            }

            int[] result = new int[k];
            for (int i = 0; i < k;) {
                List<Integer> elements = frequencyMap.pollFirstEntry().getValue();
                for (int elem: elements) {
                    if (i < k) {
                        result[i++] = elem;
                    }
                }
            }

            return result;
        }
    }

    /**
     * - 시간 복잡도: O(N logN)
     * - 결과: 16ms / 45.42MB
     */
    class Solution2 {
        public int[] topKFrequent(int[] nums, int k) {
            // key: 값, value: 빈도수
            HashMap<Integer, Integer> countMap = new HashMap<>();
            for (int num: nums) {
                if (countMap.containsKey(num)) {
                    countMap.put(num, countMap.get(num) + 1);
                } else {
                    countMap.put(num, 1);
                }
            }

            // [0]: 값, [1]: 빈도수
            List<int[]> list = new ArrayList<>(countMap.size());
            for (Map.Entry<Integer, Integer> entry: countMap.entrySet()) {
                list.add(new int[]{entry.getKey(), entry.getValue()});
            }
            list.sort((o1, o2) -> Integer.compare(o2[1], o1[1]));

            int[] result = new int[k];
            for (int i = 0; i < k; ++i) {
                result[i] = list.get(i)[0];
            }
            return result;
        }
    }
}
