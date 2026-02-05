package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class P0543_DiameterOfBinaryTree {

    /**
     * - 시간복잡도: O(V)
     * - 공간복잡도: O(V)
     * - 결과: 4ms / 46.86MB
     */
    class Solution {
        private int maxDiameter;
        private Map<TreeNode, Integer> heightMap;

        public int diameterOfBinaryTree(TreeNode root) {
            maxDiameter = 0;
            heightMap = new HashMap<>();

            dfsDiameter(root);

            return maxDiameter;
        }

        private void dfsDiameter(TreeNode node) {
            if (node == null) {
                return;
            }

            maxDiameter = Math.max(maxDiameter, diameter(node));
            dfsDiameter(node.left);
            dfsDiameter(node.right);
        }

        private int diameter(TreeNode node) {
            return height(node.left) + height(node.right) + 2;
        }

        private int height(TreeNode node) {
            if (node == null) {
                return -1;
            }

            if (heightMap.containsKey(node)) {
                return heightMap.get(node);
            }

            int height = Math.max(height(node.left), height(node.right)) + 1;
            heightMap.put(node, height);
            return height;
        }
    }
}
