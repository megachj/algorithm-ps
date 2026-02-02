package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0104_MaximumDepthOfBinaryTree {

    /**
     * - 시간복잡도: O(V)
     * - 공간복잡도: O(1)
     * - 결과: 0ms / 44.85MB
     */
    class Solution {
        public int maxDepth(TreeNode root) {
            return depth(root, 1);
        }

        private int depth(TreeNode node, int depth) {
            if (node == null) {
                return depth-1;
            }
            int left = depth(node.left, depth+1);
            int right = depth(node.right, depth+1);
            return Math.max(left, right);
        }
    }
}
