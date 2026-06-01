import java.util.*;

public class Main {

    static class Node {
        int key, height;
        Node left, right;

        Node(int key) {
            this.key = key;
            height = 1;
        }
    }

    static int rotationCount = 0;

    static int height(Node n) {
        return n == null ? 0 : n.height;
    }

    static int balance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    static Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    static Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    static Node insert(Node node, int key) {

        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int bf = balance(node);

        // RR
        if (bf < -1 && key > node.right.key) {
            System.out.println(++rotationCount + ") After inserting "
                    + key + " -> RR Rotation at pivot " + node.key);
            return leftRotate(node);
        }

        // LL
        if (bf > 1 && key < node.left.key) {
            System.out.println(++rotationCount + ") After inserting "
                    + key + " -> LL Rotation at pivot " + node.key);
            return rightRotate(node);
        }

        // LR
        if (bf > 1 && key > node.left.key) {
            System.out.println(++rotationCount + ") After inserting "
                    + key + " -> LR Rotation at pivot " + node.key);
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (bf < -1 && key < node.right.key) {
            System.out.println(++rotationCount + ") After inserting "
                    + key + " -> RL Rotation at pivot " + node.key);
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

  static void printTree(Node root, String prefix, boolean isLeft) {
        if (root == null)
            return;

        // 1. Visit the right child first
        printTree(root.right, prefix + (isLeft ? "|   " : "    "), false);

        // 2. Print the current node with standard ASCII branch lines
        System.out.print(prefix);
        System.out.print(isLeft ? "+-- " : "+-- ");
        System.out.println(root.key + "(bf=" + balance(root) + ")");

        // 3. Visit the left child
        printTree(root.left, prefix + (isLeft ? "    " : "|   "), true);
    }

    static void topK(Node root, int k, List<Integer> list) {

        if (root == null || list.size() == k)
            return;

        topK(root.right, k, list);

        if (list.size() < k)
            list.add(root.key);

        topK(root.left, k, list);
    }

    public static void main(String[] args) {

        int ids[] = {
                100,110,120,130,140,150,160,
                170,180,190,200,210,220
        };

        Node root = null;

        System.out.println("AVL INSERTION (Parcel Arrival Order)\n");

        System.out.print("Insertion order:\n");
        for (int x : ids)
            System.out.print(x + " ");
        System.out.println("\n");

        System.out.println("Rotations that occurred:\n");

        for (int x : ids)
            root = insert(root, x);

        System.out.println("\nFINAL AVL TREE\n");

        printTree(root, "",true);

        List<Integer> top = new ArrayList<>();

        topK(root, 5, top);

        System.out.println("\nTOP 5 DESCENDING");
        System.out.println(top);

        System.out.println("\nTime Complexity:");
        System.out.println("AVL Insert/Delete : O(log n)");
        System.out.println("Search            : O(log n)");
    }
}