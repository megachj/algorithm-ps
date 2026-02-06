package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

import java.util.*;

public class P0617_MergeTwoBinaryTrees {

    /**
     * 오답! index 가 최대 2^2000 까지 가능하게 돼서 IndexOutbound 예외가 발생한다.
     */
    class Solution {
        private static final int INDEX_LIMIT = 2000;
        private Integer[] mergedTree = new Integer[INDEX_LIMIT];

        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            Arrays.fill(mergedTree, null);

            dfsMergeTree(0, root1);
            dfsMergeTree(0, root2);

            return dfsMakeTree(0);
        }

        private void dfsMergeTree(int index, TreeNode node) {
            if (node == null) {
                return;
            }
            if (mergedTree[index] == null) {
                mergedTree[index] = node.val;
            } else {
                mergedTree[index] += node.val;
            }

            int rightChildIndex = (index + 1) * 2;
            int leftChildIndex = rightChildIndex - 1;

            dfsMergeTree(leftChildIndex, node.left);
            dfsMergeTree(rightChildIndex, node.right);
        }

        private TreeNode dfsMakeTree(int index) {
            if (index >= INDEX_LIMIT) {
                return null;
            }
            if (mergedTree[index] == null) {
                return null;
            }

            int rightChildIndex = (index + 1) * 2;
            int leftChildIndex = rightChildIndex - 1;

            TreeNode leftChildTree = dfsMakeTree(leftChildIndex);
            TreeNode rightChildTree = dfsMakeTree(rightChildIndex);

            return new TreeNode(mergedTree[index], leftChildTree, rightChildTree);
        }
    }

    /**
     * 오답! 왼쪽, 오른쪽 횟수로만 판단했는데 실제로 트리에서 노드의 위치는 순서까지 고려해야 한다.
     * 예) 루트 -> 왼쪽 -> 오른쪽 으로 이동하면 4번 인덱스가 되고, 루트 -> 오른쪽 -> 왼쪽 으로 이동하면 5번 인덱스가 된다.
     */
    class Solution1 {
        private Map<Integer, Map<Integer, Integer>> mergedTree;

        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            mergedTree = new HashMap<>(2000);

            dfsMergeTree(0, 0, root1);
            dfsMergeTree(0, 0, root2);

            return dfsMakeTree(0, 0);
        }

        private void dfsMergeTree(int left, int right, TreeNode node) {
            if (node == null) {
                return;
            }

            mergedTree.putIfAbsent(left, new HashMap<>());
            mergedTree.get(left).putIfAbsent(right, 0);
            int val = mergedTree.get(left).get(right) + node.val;
            mergedTree.get(left).put(right, val);

            dfsMergeTree(left + 1, right, node.left);
            dfsMergeTree(left, right + 1, node.right);
        }

        private TreeNode dfsMakeTree(int left, int right) {
            if (mergedTree.get(left) == null || mergedTree.get(left).get(right) == null) {
                return null;
            }

            TreeNode leftChildTree = dfsMakeTree(left + 1, right);
            TreeNode rightChildTree = dfsMakeTree(left, right + 1);

            return new TreeNode(mergedTree.get(left).get(right), leftChildTree, rightChildTree);
        }
    }

    /**
     * - 시간복잡도: O(V)
     * - 공간복잡도: O(V)
     * - 결과: 32ms / 52.70MB
     */
    class Solution2 {

        // 키: 2001 * 1000 byte = 2000 kb
        // 값: 4 * 2000 byte = 8kb
        // 빈 리스트는 루트
        // 0: left, 1: right
        private Map<List<Byte>, Integer> tree;

        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            tree = new HashMap<>();

            dfsMergeTree(Collections.emptyList(), root1);
            dfsMergeTree(Collections.emptyList(), root2);

            return dfsMakeTree(Collections.emptyList());
        }

        private void dfsMergeTree(List<Byte> positions, TreeNode node) {
            if (node == null) {
                return;
            }

            // 값 머지
            tree.putIfAbsent(positions, 0);
            tree.put(positions, tree.get(positions) + node.val);

            // 자식 노드 조회
            var leftChild = new ArrayList<>(positions);
            leftChild.add((byte) 0);

            var rightChild = new ArrayList<>(positions);
            rightChild.add((byte) 1);

            dfsMergeTree(leftChild, node.left);
            dfsMergeTree(rightChild, node.right);
        }

        private TreeNode dfsMakeTree(List<Byte> positions) {
            if (!tree.containsKey(positions)) {
                return null;
            }

            var leftChild = new ArrayList<>(positions);
            leftChild.add((byte) 0);

            var rightChild = new ArrayList<>(positions);
            rightChild.add((byte) 1);

            TreeNode left = dfsMakeTree(leftChild);
            TreeNode right = dfsMakeTree(rightChild);

            return new TreeNode(tree.get(positions), left, right);
        }
    }
}
