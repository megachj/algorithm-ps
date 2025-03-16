package sunset.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class P0001_TwoSum {

    public static void main(String[] args) {
        Solution solution = new P0001_TwoSum().new Solution();
        int[] result = solution.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 설명: 브루트포스로 2중 for loop 을 통해서 답을 구한다.<br>
     * - 시간복잡도: O(n^2)<br>
     * - 공간복잡도: O(1)<br>
     * - 결과: 50ms / 44.6MB<br>
     */
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            int[] result = new int[2];
            for (int i = 0; i < nums.length - 1; ++i) {
                for (int j = i+1; j < nums.length; ++j) {
                    if (nums[i] + nums[j] == target) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
            return result;
        }
    }

    /**
     * 설명: Map 에 각 숫자를 넣어두고, Map 에서 조회해서 찾는다.<br>
     * - 시간복잡도: O(n)<br>
     * - 공간복잡도: O(n)<br>
     * - 결과: 4ms / 45.2MB<br>
     */
    class Solution1 {
        public int[] twoSum(int[] nums, int target) {
            int[] result = new int[2];
            Map<Integer, Integer> map = new HashMap<>(nums.length);
            for (int i = 0; i < nums.length; ++i) {
                map.put(nums[i], i);
            }

            for (int i = 0; i < nums.length; ++i) {
                Integer counterIndex = map.get(target - nums[i]);
                if (counterIndex != null && i != counterIndex) {
                    result[0] = i;
                    result[1] = counterIndex;
                    return result;
                }
            }
            // 항상 정답이 있다고 했으므로 불려질 일이 없다.
            throw new RuntimeException();
        }
    }
}
