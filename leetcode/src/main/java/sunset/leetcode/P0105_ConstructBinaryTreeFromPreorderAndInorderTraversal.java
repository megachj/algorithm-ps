package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

public class P0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {

    /**
     * 결과: 3ms / 45.00MB
     */
    class Solution {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            return dfsTree(preorder, 0, preorder.length, inorder, 0, inorder.length);
        }

        /**
         *
         * @param preorder
         * @param pStart    inclusive
         * @param pEnd      exclusive
         * @param inorder
         * @param iStart    inclusive
         * @param iEnd      exclusive
         * @return
         */
        private TreeNode dfsTree(
                int[] preorder,
                int pStart,
                int pEnd,
                int[] inorder,
                int iStart,
                int iEnd
        ) {
            if (pStart - pEnd == 0) {
                return null;
            } else if (pEnd - pStart == 1) {
                return new TreeNode(preorder[pStart]);
            }

            int rootVal = preorder[pStart];
            int rootInorderIndex = iStart;
            for (int i = iStart; i < iEnd; ++i) {
                if (inorder[i] == rootVal) {
                    rootInorderIndex = i;
                    break;
                }
            }
            int leftChildCount = rootInorderIndex - iStart;

            TreeNode leftSubtree = dfsTree(preorder, pStart+1, pStart+1+leftChildCount, inorder, iStart, rootInorderIndex);
            TreeNode rightSubtree = dfsTree(preorder, pStart+1+leftChildCount, pEnd, inorder, rootInorderIndex+1, iEnd);

            return new TreeNode(rootVal, leftSubtree, rightSubtree);
        }
    }
}
