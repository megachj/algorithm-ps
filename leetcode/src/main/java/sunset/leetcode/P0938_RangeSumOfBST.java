package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0938_RangeSumOfBST {

    /**
     * 결과: 1ms / 53.84MB
     */
    class Solution {

        public int rangeSumBST(TreeNode root, int low, int high) {
             return dfsRangeSum(root, low, high);
        }

        private int dfsRangeSum(TreeNode node, int low, int high) {
            if (node == null) {
                return 0;
            }

            int leftSum = dfsRangeSum(node.left, low, high);
            int rightSum = dfsRangeSum(node.right, low, high);

            int result = leftSum + rightSum;
            if (node.val >= low && node.val <= high) {
                result += node.val;
            }

            return result;
        }
    }
}
