package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class P0783_MinimumDistanceBetweenBSTNodes {

    /**
     * 결과: 0ms / 43.10MB
     */
    class Solution {
        public int minDiffInBST(TreeNode root) {
            var results = new ArrayList<Integer>();
            dfsCreateNodeList(results, root);

            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < results.size() - 1; ++i) {
                minDistance = Math.min(minDistance, results.get(i+1) - results.get(i));
            }
            return minDistance;
        }

        private void dfsCreateNodeList(List<Integer> results, TreeNode node) {
            if (node == null) {
                return;
            }

            dfsCreateNodeList(results, node.left);
            results.add(node.val);
            dfsCreateNodeList(results, node.right);
        }
    }
}
