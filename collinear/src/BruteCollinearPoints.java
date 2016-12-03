import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by Liwink on 12/2/16.
 */

public class BruteCollinearPoints {
    private Stack<LineSegment> lineSegments = new Stack<LineSegment>();
    private Queue<Point> storedPoints = new Queue<Point>();
    private Point[] points;


    public BruteCollinearPoints(Point[] ps) {
        int len = ps.length;
        points = new Point[len];
        for (int i = 0; i < len; i++) {
            points[i] = ps[i];
        }

        Arrays.sort(points);
        for (int i = 1; i < len; i++) {
            if (points[i].compareTo(points[i-1]) == 0) throw new IllegalArgumentException();
        }

        Queue<Double> slopes;

        double slope;
        for (int i = 0; i < (len - 3); i++) {
            slopes = new Queue<Double>();
            for (int j = i + 1; j < (len - 2); j++) {
                slope = points[i].slopeTo(points[j]);
                for (int n = j + 1; n < (len - 1); n++) {
                    if (points[i].slopeTo(points[n]) != slope) continue;
                    else {
                        for (int m = n + 1; m < len; m++) {
                            if (points[i].slopeTo(points[m]) != slope) continue;
                            else {
                                boolean check = true;
                                for (Point l : storedPoints) {
                                    if (l.slopeTo(points[i]) == slope) {
                                        check = false;
                                        break;
                                    }
                                }
                                for (double s : slopes) {
                                    if (s == slope) {
                                        lineSegments.pop();
                                        break;
                                    }
                                }
                                if (check) {
                                    lineSegments.push(new LineSegment(points[i], points[m]));
                                    storedPoints.enqueue(points[i]);
                                    slopes.enqueue(slope);
                                }
                            }
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
//        Point[] ps = new Point[] {new Point(1,1), new Point(1,2), new Point(1,1)};
//        BruteCollinearPoints col = new BruteCollinearPoints(ps);

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
