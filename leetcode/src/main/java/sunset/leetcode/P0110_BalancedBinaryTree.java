package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0110_BalancedBinaryTree {

    /**
     * 결과: 0ms / 45.78MB
     */
    class Solution {

        private boolean balanced;

        public boolean isBalanced(TreeNode root) {
            balanced = true;

            dfsHeight(root);

            return balanced;
        }

        private int dfsHeight(TreeNode node) {
            // fail fast
            if (!balanced) {
                return -1;
            }

            if (node == null) {
                return -1;
            }

            int leftChildHeight = dfsHeight(node.left);
            int rightChildHeight = dfsHeight(node.right);
            if (Math.abs(leftChildHeight - rightChildHeight) > 1) {
                balanced = false;
            }

            return Math.max(leftChildHeight, rightChildHeight) + 1;
        }
    }
}
