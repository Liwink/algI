import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.text.Position;

/**
 * Created by Liwink on 12/2/16.
 */

public class FastCollinearPoints {
    private Stack<LineSegment> lineSegments = new Stack<LineSegment>();
    private Queue<Point> storedPoints = new Queue<Point>();
    private Point[] points;

    private int COUNT = 2;

    public FastCollinearPoints(Point[] ps) {
        int len = ps.length;
        points = new Point[len];
        for (int i = 0; i < len; i++) points[i] = ps[i];

        Arrays.sort(points);
        for (int i = 1; i < len; i++) {
            if (points[i].compareTo(points[i-1]) == 0) throw new IllegalArgumentException();
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
                    boolean check = true;
                    if (currentCount <= 0) {
                        for (Point l : storedPoints) {
                            if (l.slopeTo(resortPoints[i]) == slope) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
                            if (currentCount < 0) lineSegments.pop();
                            lineSegments.push(new LineSegment(resortPoints[i], resortPoints[j]));
                            storedPoints.enqueue(resortPoints[i]);
                        }
                    }
                }
            }
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
