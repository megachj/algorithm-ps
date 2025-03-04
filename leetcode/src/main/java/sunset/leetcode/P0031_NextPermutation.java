package sunset.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @see <a href="https://leetcode.com/problems/next-permutation/description/">링크</a>
 */
public class P0031_NextPermutation {

    public static void main(String[] args) {
        Solution solution = new P0031_NextPermutation().new Solution();
        int[] nums = new int[]{1, 2, 3};
        solution.nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }

    class Solution {

        public void nextPermutation(int[] nums) {
            int size = nums.length;
            int[] nextPermutation = new int[size];

            boolean existNext = false;
            for (int i = size - 2; i >= 0; --i) {
                int num = nums[i]; // 기준

                // 기준보다 큰 것중 가장 작은 것 찾기
                int nextNum = Integer.MAX_VALUE;
                int nextIdx = -1;
                for (int j = i + 1; j < size; ++j) {
                    if (nums[j] > num && nextNum > nums[j]) {
                        nextNum = nums[j];
                        nextIdx = j;
                    }
                }

                // 다음것이 없다면 좀 더 큰 자릿수에서 찾아야한다.
                if (nextNum == Integer.MAX_VALUE) {
                    continue;
                }

                // 다음것이 있다면 영향받지 않는 부분은 그대로
                for (int k = 0; k < i; ++k) {
                    nextPermutation[k] = nums[k];
                }
                nextPermutation[i] = nextNum;

                // 남은 것들 모아서 정렬하기
                List<Integer> remains = new ArrayList<>(size - (i + 1));
                for (int l = i; l < size; ++l) {
                    if (l != nextIdx) {
                        remains.add(nums[l]);
                    }
                }
                remains.sort(Integer::compareTo);

                // nextPermutation 에 남은것들 넣어주기
                for (int k = i+1; k < size; ++k) {
                    nextPermutation[k] = remains.get(k - (i + 1));
                }

                applyNext(nums, nextPermutation);
                existNext = true;
                break;
            }

            if (!existNext) {
                // 맨 마지막 것은 맨 처음껄로
                List<Integer> first = new ArrayList<>(size);
                for (int i = 0; i < size; ++i) {
                    first.add(nums[i]);
                }
                first.sort(Integer::compareTo);
                applyNext(nums, first);
            }
        }

        private void applyNext(int[] nums, int[] nextPermutation) {
            for (int i = 0; i < nums.length; ++i) {
                nums[i] = nextPermutation[i];
            }
        }

        private void applyNext(int[] nums, List<Integer> nextPermutation) {
            for (int i = 0; i < nums.length; ++i) {
                nums[i] = nextPermutation.get(i);
            }
        }
    }
}
