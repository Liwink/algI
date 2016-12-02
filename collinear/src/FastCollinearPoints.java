import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by Liwink on 12/2/16.
 */

public class FastCollinearPoints {
    Point[] points;
    Queue<LineSegment> lineSegments = new Queue<LineSegment>();
    Queue<Point> storedPoints = new Queue<Point>();

    int COUNT = 2;

    public FastCollinearPoints(Point[] points) {
        this.points = points;

        int len = points.length;

        for (int i = 0; i < (len - 3); i++) {
            if (points[i] == null) throw new NullPointerException();
            double current = Double.NEGATIVE_INFINITY;
            int currentCount = COUNT;

            Arrays.sort(points, i, len, points[i].slopeOrder());
            for (int j = i + 1; j < len; j++) {
                double slope = points[i].slopeTo(points[j]);
                if (slope == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();

                if (current != slope) {
                    current = slope;
                    currentCount = COUNT;
                } else {
                    currentCount--;
                    boolean check = true;
                    if (currentCount == 0) {
                        for (Point l : storedPoints) {
                            if (l.slopeTo(points[i]) == slope) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
                            lineSegments.enqueue(new LineSegment(points[i], points[j]));
                            storedPoints.enqueue(points[i]);
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
        Point[] ps = new Point[] {new Point(1,2), new Point(2,3), new Point(3,4), new Point(4,5), new Point(5,6)};
        FastCollinearPoints col = new FastCollinearPoints(ps);
        System.out.println(col.numberOfSegments());
    }
}
