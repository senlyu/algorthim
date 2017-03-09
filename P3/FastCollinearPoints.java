import java.util.ArrayList;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    
    private ArrayList<LineSegment> ls;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        
        //quick sort
        qsort(points);
        for (int i = 0; i < points.length-1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) throw new java.lang.IllegalArgumentException();
        }
        Point[] points2 = points.clone();
        //for n merge sort
        ls = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            points = points2.clone();
            Point pp = points[i];
            msort(points, pp);
            //find duplicates add first and the last
            for (int j = 0; (j < points.length); j++) {
                Point p = null;
                Point q = null;
                if (pp.compareTo(points[j])<=0) {
                    p = pp;
                    q = points[j];
                } else {
                    p = points[j];
                    q = pp;
                }
                
                int jj = j;
                while (((jj+1)<points.length) && ((pp.slopeTo(points[jj]) == pp.slopeTo(points[jj + 1]))))  {
                    jj++;
                    if (points[jj].compareTo(p) < 0) {
                        p = points[jj];
                    }
                    if (points[jj].compareTo(q) > 0) {
                        q = points[jj];
                    }
                    
                }
                if ((jj - j + 1) >= 3) {
                    boolean flag = false;
                    LineSegment lss = new LineSegment(q, p);
                    for (LineSegment l : ls) {
                        if ((!flag) && (l.toString().compareTo(lss.toString())==0)) {
                            flag = true;
                        }
                    }
                    if (!flag) ls.add(lss);
                }
                j = jj;
                
            }
            
        }
        
        
    }
    
    private void msort(Point[] points, Point p) {
        // TODO Auto-generated method stub
        Point[] pointss = new Point[points.length];
        msort(points, pointss, 0, points.length - 1, p);
    }

    private void msort(Point[] points, Point[] pointss, int lo, int hi, Point p) {
        // TODO Auto-generated method stub
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        msort(points, pointss, lo, mid, p);
        msort(points, pointss, mid+1, hi, p);
        merge(points, pointss, lo, mid, hi, p);
    }
    
    private void merge(Point[] points, Point[] pointss, int lo, int mid, int hi, Point p) {
        // TODO Auto-generated method stub
        for (int k = lo; k <= hi; k++) {
            pointss[k] = points[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i>mid) points[k] = pointss[j++];
            else if (j>hi) points[k] = pointss[i++];
            else if (p.slopeTo(pointss[j]) < p.slopeTo(pointss[i])) points[k] = pointss[j++];
            else points[k] = pointss[i++];
        }
    }


    private static int partition(Point[] p, int lo, int hi){
        int i = lo, j = hi + 1;
        while (true) {
            while (p[++i].compareTo(p[lo]) < 0) {
                if (i == hi) break;
            }
            while (p[lo].compareTo(p[--j]) < 0) {
                if (j == lo) break;
            }
            
            if (i >= j) break;
            Point swapp = p[i];
            p[i] = p[j];
            p[j] = swapp;
        }
        Point swapp = p[lo];
        p[lo] = p[j];
        p[j] = swapp;
        return j;
    }
    
    private void qsort(Point[] p) {
        // TODO Auto-generated method stub
        StdRandom.shuffle(p);
        qsort(p, 0, p.length -1);
    }
    
    private void qsort(Point[] p, int lo, int hi) {
        // TODO Auto-generated method stub
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        Point v = p[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = p[i].compareTo(v);
            if (cmp<0) {
                
                Point swapp = p[i];
                p[i] = p[lt];
                p[lt] = swapp;
                lt++;
                i++;
            } else if (cmp>0) {
                
                Point swapp = p[i];
                p[i] = p[gt];
                p[gt] = swapp;
                gt--;
            } else i++;
        }
        //int j = partition(p, lo, hi);
        qsort(p, lo, lt-1);
        qsort(p, gt+1, hi);
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
        if (args == null) {
            throw new java.lang.NullPointerException();
        }
        In in = new In(args[0]);
        int n = in.readInt();
        if (n==0) {
            throw new java.lang.NullPointerException();
        }
        Point[] points = new Point[n];
        try{
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
        }catch(Exception e){
            throw new java.lang.NullPointerException();
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