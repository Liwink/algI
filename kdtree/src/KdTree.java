import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

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
        public Point2D point;

        public Node(Point2D p) {
            this.x = p.x();
            this.y = p.y();
            this.point = p;
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

        Node child = new Node(p);
        size++;

        if (root == null) {
            root = child;
            child.div = 0;
        }
        else add(root, child);
    }

    public boolean contains(Point2D p) {
        Node n = root;
        Node c = new Node(p);
        while (n != null) {
            if (n.point.equals(c.point)) return true;
            else n = next(n, c);
        }
        return false;
    }

    public void draw() {
        Queue<Node> queue = new Queue<Node>();
        enqueue(queue, root);

        for (Node n:
             queue) {
            n.point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();

        enRange(queue, root, rect);
        return queue;
    }

    // private method

    private void enRange(Queue<Point2D> queue, Node node, RectHV rect) {
        if (node == null) return;
        if (rect.contains(node.point)) {
            queue.enqueue(node.point);
            enRange(queue, node.left, rect);
            enRange(queue, node.right, rect);
        }
        else {
            if ((node.div == 0 && node.x < rect.xmin()) ||
                    (node.div == 1 && node.y < rect.ymin())) {
                enRange(queue, node.right, rect);
            }
            else if ((node.div == 0 && node.x >= rect.xmax()) ||
                    (node.div == 1 && node.y >= rect.ymax())) {
                enRange(queue, node.left, rect);
            }
            else {
                enRange(queue, node.right, rect);
                enRange(queue, node.left, rect);
            }
        }

    }

    private void enqueue(Queue<Node> q, Node node) {
        if (node == null) return;
        q.enqueue(node);
        enqueue(q, node.right);
        enqueue(q, node.left);
    }

    private void add(Node p, Node c) {
        Node tmp = next(p, c);
        if (tmp == null) append(p, c);
        else add(tmp, c);
    }

    private Node next(Node p, Node c) {
        if ((p.div == 0 && p.x < c.x) ||
                (p.div == 1 && p.y < c.y)) return p.right;
        else return p.left;
    }

    private void append(Node p, Node c) {
        if ((p.div == 0 && p.x < c.x) ||
                (p.div == 1 && p.y < c.y)) p.right = c;
        else p.left = c;
        c.div = (p.div + 1) % 2;
    }

}
