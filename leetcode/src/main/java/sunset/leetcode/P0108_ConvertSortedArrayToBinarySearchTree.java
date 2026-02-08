package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0108_ConvertSortedArrayToBinarySearchTree {

    /**
     * 결과: 0ms / 44.98MB
     */
    class Solution {
        public TreeNode sortedArrayToBST(int[] nums) {
            return dfsCreateBST(nums, 0, nums.length);
        }

        // nums[a:b) 를 BST 로 생성한다.
        private TreeNode dfsCreateBST(int[] nums, int aIndex, int bIndex) {
            int diff = bIndex - aIndex;
            if (diff <= 0) {
                return null;
            } else if (diff == 1) {
                return new TreeNode(nums[aIndex]);
            }
            int middleIndex = (aIndex + bIndex) / 2;
            TreeNode left = dfsCreateBST(nums, aIndex, middleIndex);
            TreeNode right = dfsCreateBST(nums, middleIndex + 1, bIndex);

            return new TreeNode(nums[middleIndex], left, right);
        }
    }
}
