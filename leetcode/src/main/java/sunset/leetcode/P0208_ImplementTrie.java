package sunset.leetcode;

import java.util.HashMap;
import java.util.Map;

public class P0208_ImplementTrie {}

class Trie {

    private final Node root;

    public Trie() {
        root = Node.rootNode();
    }

    public void insert(String word) {
        Node parent = root;
        for (int i = 0; i < word.length(); ++i) {
            char currentChar = word.charAt(i);
            parent.children.putIfAbsent(currentChar, Node.middleNode(currentChar));
            parent = parent.children.get(currentChar);
        }
        parent.children.putIfAbsent(null, Node.leafNode());
    }

    public boolean search(String word) {
        Node parent = root;
        for (int i = 0; i < word.length(); ++i) {
            char currentChar = word.charAt(i);
            if (!parent.children.containsKey(currentChar)) {
                return false;
            }
            parent = parent.children.get(currentChar);
        }

        return parent.children.containsKey(null) && parent.children.get(null).isLeaf();
    }

    public boolean startsWith(String prefix) {
        Node parent = root;
        for (int i = 0; i < prefix.length(); ++i) {
            char currentChar = prefix.charAt(i);
            if (!parent.children.containsKey(currentChar)) {
                return false;
            }
            parent = parent.children.get(currentChar);
        }

        return true;
    }

    private static class Node {
        private Character val;
        private Map<Character, Node> children;

        private Node() {

        }

        static Node rootNode() {
            Node rootNode = new Node();
            rootNode.children = new HashMap<>();
            return rootNode;
        }

        static Node middleNode(char val) {
            Node node = new Node();
            node.val = val;
            node.children = new HashMap<>();
            return node;
        }

        static Node leafNode() {
            return new Node();
        }

        boolean isRoot() {
            return val == null && children != null;
        }

        boolean isLeaf() {
            return val == null && children == null;
        }
    }
}
