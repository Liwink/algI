/**
 * Created by Liwink on 12/15/16.
 */

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;


public class PointSET {
    SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p:
             points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();

        for (Point2D p:
             points) {
            if (rect.contains(p)) queue.enqueue(p);
        }
        return queue;
    }

    public Point2D nearest(Point2D p) {
        double minDis = -1;
        Point2D np = null;

        for (Point2D tp:
             points) {
            double dis = p.distanceTo(tp);
            if (minDis == -1 || dis < minDis) {
                minDis = dis;
                np = tp;
            }
        }
        return np;
    }

}
