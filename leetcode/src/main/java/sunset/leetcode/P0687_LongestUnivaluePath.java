package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0687_LongestUnivaluePath {

    /**
     * - 시간복잡도: O(V)
     * - 공간복잡도: O(1)
     * - 결과: 3ms / 49.17MB
     */
    class Solution {
        private int maxUnivaluePath;

        public int longestUnivaluePath(TreeNode root) {
            maxUnivaluePath = 0;

            dfsNodeUnivaluePath(root);

            return maxUnivaluePath;
        }

        private int[] dfsNodeUnivaluePath(TreeNode node) {
            if (node == null) {
                return new int[]{0, 0};
            }

            int[] leftChildUnivaluePath = dfsNodeUnivaluePath(node.left);
            int[] rightChildUnivaluePath = dfsNodeUnivaluePath(node.right);

            int[] nodeUnivaluePath = new int[]{0, 0};
            if (node.left != null && node.val == node.left.val) {
                nodeUnivaluePath[0] = Math.max(leftChildUnivaluePath[0], leftChildUnivaluePath[1]) + 1;
            }
            if (node.right != null && node.val == node.right.val) {
                nodeUnivaluePath[1] = Math.max(rightChildUnivaluePath[0], rightChildUnivaluePath[1]) + 1;
            }

            if (node.left != null && node.right != null && node.left.val == node.right.val) {
                maxUnivaluePath = Math.max(maxUnivaluePath, nodeUnivaluePath[0] + nodeUnivaluePath[1]);
            } else {
                maxUnivaluePath = Math.max(maxUnivaluePath, nodeUnivaluePath[0]);
                maxUnivaluePath = Math.max(maxUnivaluePath, nodeUnivaluePath[1]);
            }

            return nodeUnivaluePath;
        }
    }
}
