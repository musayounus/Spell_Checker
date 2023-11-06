package Src;

public class AVLTree {
    private Node root;

    private static class Node {
        String word;
        Node left;
        Node right;
        int height;

        public Node(String word) {
            this.word = word;
            this.height = 1;
        }
    }

    public void insert(String word) {
        root = insertNode(root, word);
    }

    private Node insertNode(Node node, String word) {
        if (node == null) {
            return new Node(word);
        }

        int cmp = word.compareTo(node.word);

        if (cmp < 0) {
            node.left = insertNode(node.left, word);
        } else if (cmp > 0) {
            node.right = insertNode(node.right, word);
        } else {
            return node;
        }

        updateHeight(node);

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && word.compareTo(node.left.word) < 0) {
            return rotateRight(node);
        }

        if (balanceFactor < -1 && word.compareTo(node.right.word) > 0) {
            return rotateLeft(node);
        }

        if (balanceFactor > 1 && word.compareTo(node.left.word) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balanceFactor < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    public void remove(String value) {
        root = removeNode(root, value);
    }

    private Node removeNode(Node node, String value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.word);
        if (cmp < 0) {
            node.left = removeNode(node.left, value);
        } else if (cmp > 0) {
            node.right = removeNode(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node temp = findMin(node.right);
                node.word = temp.word;
                node.right = removeNode(node.right, temp.word);
            }
        }
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public boolean contains(String word) {
        return containsWord(root, word);
    }

    private boolean containsWord(Node node, String word) {
        if (node == null) {
            return false;
        }

        int cmp = word.compareTo(node.word);

        if (cmp < 0) {
            return containsWord(node.left, word);
        } else if (cmp > 0) {
            return containsWord(node.right, word);
        } else {
            return true;
        }
    }
}


