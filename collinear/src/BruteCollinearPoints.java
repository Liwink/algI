import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

/**
 * Created by Liwink on 12/2/16.
 */

public class BruteCollinearPoints {
    Point[] points;
    Queue<LineSegment> lineSegments = new Queue<LineSegment>();
    Queue<Point> storedPoints = new Queue<Point>();

    int COUNT = 2;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;

        int len = points.length;
        Queue<Double> slopes;

        double slope;
        for (int i = 0; i < (len - 3); i++) {
            slopes = new Queue<Double>();
            for (int j = i+1; j < (len - 2); j++) {
                slope = points[i].slopeTo(points[j]);
                for (int n = j+1; n < (len - 1); n++) {
                    if (points[i].slopeTo(points[n]) != slope) continue;
                    else {
                        for (int m = n+1; m < len; m++) {
                            if (points[i].slopeTo(points[m]) != slope) continue;
                            else {
                                boolean check = true;
                                for (Point l : storedPoints) {
//                                    System.out.println(l.slopeTo(points[i]) + " " + slope);
//                                    System.out.println(i  + " " +  j + " " +  n  + " " +  m);
                                    if (l.slopeTo(points[i]) == slope) {
                                        check = false;
                                        break;
                                    }
                                }
                                for (double s: slopes) {
                                    if (s == slope) {
                                        check = false;
                                        break;
                                    }
                                }
                                if (check) {
                                    lineSegments.enqueue(new LineSegment(points[i], points[j]));
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
//        Point[] ps = new Point[] {new Point(1,2), new Point(2,3), new Point(3,4), new Point(4,5), new Point(5,6)};
//        BruteCollinearPoints col = new BruteCollinearPoints(ps);
//        System.out.println(col.numberOfSegments());

        /*
        System.out.println(args[0]);
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Point[] ps = new Point[n];
        int m = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            ps[m] = new Point(i, j);
            m++;
        }

        BruteCollinearPoints col = new BruteCollinearPoints(ps);
        LineSegment[] segments = col.segments();
        for (LineSegment s: segments) s.draw();
        System.out.println("num: " + col.numberOfSegments());
        */
    }
}
