import edu.princeton.cs.algs4.Point2D;

/**
 * Created by Liwink on 12/15/16.
 */


public class KdTree {

    private Node root;
    private int size;

    private class Node {
        public double x;
        public double y;
        public Node left;
        public Node right;
        public int div;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();

        Node child = new Node(p.x(), p.y());
        size++;

        if (root == null) {
            root = child;
            child.div = 0;
        }
        else add(root, child);
    }

    public boolean contains(Point2D p) {
        Node n = root;
        Node c = new Node(p.x(), p.y());
        while (n != null) {
            if (n.x == c.x && n.y == c.y) return true;
            else n = next(n, c);
        }
        return false;
    }

    private void add(Node p, Node c) {
        Node tmp = next(p, c);
        if (tmp == null) append(p, c);
        else add(tmp, c);
    }

    private Node next(Node p, Node c) {
        if ((p.div == 0 && p.x > c.x) ||
                (p.div == 1 && p.y > c.y)) return p.right;
        else return p.left;
    }

    private void append(Node p, Node c) {
        if ((p.div == 0 && p.x > c.x) ||
                (p.div == 1 && p.y > c.y)) p.right = c;
        else p.left = c;
        c.div = (p.div + 1) % 2;
    }

}
