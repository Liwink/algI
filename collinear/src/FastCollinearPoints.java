import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by Liwink on 12/2/16.
 */

public class FastCollinearPoints {
    private Stack<LineSegment> lineSegments = new Stack<LineSegment>();
    private Point[] points;
    private Stack<PointPair> pointPairs = new Stack<PointPair>();

    private int COUNT = 2;

    private class PointPair {
        public Point p1;
        public Point p2;
        public final Comparator<PointPair> BY_SLOPE = new BySlope();

        public PointPair(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        private class BySlope implements Comparator<PointPair> {
            public int compare(PointPair pr1, PointPair pr2) {
                double s1 = pr1.p1.slopeTo(pr1.p2);
                double s2 = pr2.p1.slopeTo(pr2.p2);
                if (s1 > s2) return 1;
                else if (s1 < s2) return -1;
                else {
                    int j = pr1.p2.compareTo(pr2.p2);
                    if (j == 0) return pr1.p1.compareTo(pr2.p1);
                    return j;
                }
            }
        }

        // to judge whether the two pairs is on same line
        public boolean sameLine(PointPair pp) {
            if (this.p1.slopeTo(this.p2) == pp.p1.slopeTo(pp.p2)
                    && this.p2.compareTo(pp.p2) == 0) return true;
            else return false;
        }
    }

    public FastCollinearPoints(Point[] ps) {
        int len = ps.length;
        points = new Point[len];
        for (int i = 0; i < len; i++) points[i] = ps[i];

        Arrays.sort(points);
        for (int i = 1; i < len; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }

        Point[] resortPoints = new Point[len];

        for (int i = 0; i < (len - 3); i++) {
            if (points[i] == null) throw new NullPointerException();
            double current = Double.NEGATIVE_INFINITY;
            int currentCount = COUNT;

            for (int n = i; n < len; n++) {
                resortPoints[n] = points[n];
            }
            Arrays.sort(resortPoints, i, len, resortPoints[i].slopeOrder());
            for (int j = i + 1; j < len; j++) {
                double slope = resortPoints[i].slopeTo(resortPoints[j]);

                if (current != slope) {
                    current = slope;
                    currentCount = COUNT;
                } else {
                    currentCount--;
                    if (currentCount <= 0) {
                        if (currentCount < 0) pointPairs.pop();
                        pointPairs.push(new PointPair(resortPoints[i], resortPoints[j]));
                    }
                }
            }
        }

        // to remove the duplicated case for more than 4 points
        PointPair[] pairList = new PointPair[pointPairs.size()];
        int i = 0;
        for (PointPair p :
                pointPairs) {
            pairList[i] = p;
            i++;
        }
        Arrays.sort(pairList, new PointPair(new Point(1, 2), new Point(2, 3)).BY_SLOPE);
        for (int j = 0; j < pointPairs.size(); j++) {
            if (j > 0 && pairList[j].sameLine(pairList[j - 1])) continue;
            lineSegments.push(new LineSegment(pairList[j].p1, pairList[j].p2));
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[lineSegments.size()];
        int i = 0;
        for (LineSegment s :
                lineSegments) {
            segments[i] = s;
            i++;
        }
        return segments;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
