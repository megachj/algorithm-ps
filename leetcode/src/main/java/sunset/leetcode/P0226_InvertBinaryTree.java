package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0226_InvertBinaryTree {

    /**
     * - 시간복잡도: O(V)
     * - 공간복잡도: O(1)
     * - 결과: 0ms / 43.06MB
     */
    class Solution {
        public TreeNode invertTree(TreeNode root) {
            dfsInvert(root);
            return root;
        }

        private void dfsInvert(TreeNode node) {
            if (node == null) {
                return;
            }

            dfsInvert(node.left);
            dfsInvert(node.right);

            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
    }
}
