import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import static java.lang.Math.abs;

/**
 * Created by Liwink on 12/15/16.
 */


public class KdTree {

    private Node root;
    private int size = 0;

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

        public boolean isRight(Point2D p) {
            if ((div == 0 && x < p.x()) ||
                    (div == 1 && y < p.y())) return true;
            else return false;
        }

        public boolean isRight(Node n) {
            if ((div == 0 && x < n.x) ||
                    (div == 1 && y < n.y)) return true;
            else return false;
        }

        public double distDiv(Point2D p) {
            if (div == 0) return abs(x - p.x());
            else return abs(y - p.y());
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

        if (root == null) {
            root = child;
            child.div = 0;
            size++;
        }
        else {
            if (add(root, child)) size++;
        }
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

        for (Node n :
                queue) {
            n.point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();

        enRange(queue, root, rect);
        return queue;
    }

    public Point2D nearest(Point2D p) {
        Node near = getNearest(root, p);
        if (near == null) return null;
        else return near.point;
    }

    // private method

    private Node getNearest(Node node, Point2D p) {
        if (node == null) return null;
        Node first;
        Node second;
        Node near;

        if (node.isRight(p)) first = getNearest(node.right, p);
        else first = getNearest(node.left, p);

        if (first == null ||
                node.point.distanceTo(p) < first.point.distanceTo(p)) {
            near = node;
        } else near = first;

        if (near.point.distanceTo(p) > node.distDiv(p)) {
            if (node.isRight(p)) second = getNearest(node.left, p);
            else second = getNearest(node.right, p);
            if (second != null &&
                    (second.point.distanceTo(p) < near.point.distanceTo(p))) {
                near = second;
            }
        }
        return near;
    }

    private void enRange(Queue<Point2D> queue, Node node, RectHV rect) {
        if (node == null) return;
        if (rect.contains(node.point)) {
            queue.enqueue(node.point);
            enRange(queue, node.left, rect);
            enRange(queue, node.right, rect);
        } else {
            if ((node.div == 0 && node.x < rect.xmin()) ||
                    (node.div == 1 && node.y < rect.ymin())) {
                enRange(queue, node.right, rect);
            } else if ((node.div == 0 && node.x >= rect.xmax()) ||
                    (node.div == 1 && node.y >= rect.ymax())) {
                enRange(queue, node.left, rect);
            } else {
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

    private boolean add(Node p, Node c) {
        if (p.point.equals(c.point)) return false;

        Node tmp = next(p, c);
        if (tmp == null) {
            append(p, c);
            return true;
        }
        else return add(tmp, c);
    }

    private Node next(Node p, Node c) {
        if (p.isRight(c)) return p.right;
        else return p.left;
    }

    private void append(Node p, Node c) {
        if (p.isRight(c)) p.right = c;
        else p.left = c;
        c.div = (p.div + 1) % 2;
    }

}
