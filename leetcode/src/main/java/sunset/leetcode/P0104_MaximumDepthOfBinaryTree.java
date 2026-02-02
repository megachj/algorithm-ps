package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0104_MaximumDepthOfBinaryTree {

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
