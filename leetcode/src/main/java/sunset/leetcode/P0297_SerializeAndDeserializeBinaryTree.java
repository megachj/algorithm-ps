package sunset.leetcode;

import sunset.leetcode.support.datastructure.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P0297_SerializeAndDeserializeBinaryTree {

    private static final String INPUT_1 = "[1,2,3,null,null,4,5]";
    private static final String INPUT_2 = "[]";

    public static void main(String[] args) {
        Codec codec = new P0297_SerializeAndDeserializeBinaryTree().new Codec();

        // test
        String input = INPUT_1;
        TreeNode node = codec.deserialize(input);
        String output = codec.serialize(node);
        System.out.println("Input: " + input);
        System.out.println("Output: " + output);
    }

    /**
     * 결과: 16ms / 48.56MB
     */
    public class Codec {

        private static final String NULL_STRING = "null";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            List<TreeNode> treeNodes = new ArrayList<>();

            treeNodes.add(root);
            int pointer = 0;

            while (pointer < treeNodes.size()) {
                var node = treeNodes.get(pointer);
                if (node == null) {
                    pointer++;
                    continue;
                }
                treeNodes.add(node.left);
                treeNodes.add(node.right);
                pointer++;
            }

            // 마지막 null 들은 모두 제거
            while (!treeNodes.isEmpty() && treeNodes.get(treeNodes.size() - 1) == null) {
                treeNodes.remove(treeNodes.size() - 1);
            }

            // String 만들기
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (TreeNode node: treeNodes) {
                if (node == null) {
                    sb.append(NULL_STRING);
                    sb.append(",");
                } else {
                    sb.append(node.val);
                    sb.append(",");
                }
            }
            if (!treeNodes.isEmpty()) {
                sb.delete(sb.length() - 1, sb.length()); // 끝에 , 지우기
            }
            sb.append("]");

            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // 정수 또는 null 문자
            String[] treeNodes = data.replace("[", "")
                    .replace("]", "")
                    .replace(" ", "")
                    .split(",");

            if (treeNodes.length == 1 && "".equals(treeNodes[0])) {
                return null;
            }

            int lastIndex = treeNodes.length - 1;

            Map<Integer, int[]> childrenMap = new HashMap<>();
            for (int i = 0, pointer = 0; i < treeNodes.length; ++i) {
                if (NULL_STRING.equals(treeNodes[i])) {
                    continue;
                }

                if (pointer + 2 <= lastIndex) {
                    childrenMap.put(i, new int[]{pointer + 1, pointer + 2});
                    pointer += 2;
                } else if (pointer +1 <= lastIndex) {
                    childrenMap.put(i, new int[]{pointer + 1});
                    pointer += 1;
                }
            }

            return dfsMakeTree(treeNodes, childrenMap, 0);
        }

        private TreeNode dfsMakeTree(
                String[] treeNodes,
                Map<Integer, int[]> childrenMap,
                int index)
        {
            int lastIndex = treeNodes.length - 1;
            if (index > lastIndex) {
                return null;
            }
            if (NULL_STRING.equals(treeNodes[index])) {
                return null;
            }

            TreeNode node = new TreeNode(Integer.parseInt(treeNodes[index]));

            int[] children = childrenMap.get(index);
            if (children != null && children.length >= 1) {
                node.left = dfsMakeTree(treeNodes, childrenMap, children[0]);
            }
            if (children != null && children.length == 2) {
                node.right = dfsMakeTree(treeNodes, childrenMap, children[1]);
            }

            return node;
        }
    }
}
