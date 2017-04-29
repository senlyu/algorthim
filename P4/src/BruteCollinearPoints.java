import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    
    private ArrayList<LineSegment> ls;
    
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new java.lang.NullPointerException();
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
        
        for (int i = 0; i < points.length - 1; i++) {
            int jmin = i;
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(points[jmin]) < 0) {
                    jmin = j;
                }
            }
            if (jmin != i) {
                Point swapp = new Point(0,0);
                swapp = points[jmin];
                points[jmin] = points[i];
                points[i] =swapp;
            }
        }

        ls = new ArrayList<LineSegment>();
        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        
                        if ((points[i1].slopeTo(points[i2]) == points[i1].slopeTo(points[i3])) &&
                                (points[i1].slopeTo(points[i3]) == points[i1].slopeTo(points[i4])) && 
                                (points[i1].slopeTo(points[i4]) == points[i1].slopeTo(points[i2]))) {
                            ls.add(new LineSegment(points[i1],points[i4]));
                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments() {
        // the number of line segments
        return ls.size();
    }
    public LineSegment[] segments() {
        // the line segments
        return ls.toArray(new LineSegment[ls.size()]);
        
    }
    public static void main(String[] args) {

        // read the n points from a file
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