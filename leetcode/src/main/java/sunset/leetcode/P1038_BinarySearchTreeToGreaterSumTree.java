package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P1038_BinarySearchTreeToGreaterSumTree {

    /**
     * 결과: 0ms / 43.19MB
     */
    class Solution {

        private int sum;

        public TreeNode bstToGst(TreeNode root) {
            sum = 0;
            dfsConvertGst(root);
            return root;
        }

        private void dfsConvertGst(TreeNode node) {
            if (node == null) {
                return;
            }

            dfsConvertGst(node.right);
            sum += node.val;
            node.val = sum;
            dfsConvertGst(node.left);
        }
    }
}
