

//import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;
import java.util.LinkedList;
import java.util.List;

public class PointSET {
    private SET<Point2D> p_set;
    
    public PointSET() {
        // construct an empty set of points 
        p_set = new SET<Point2D>();
    }
    public boolean isEmpty() {
        // is the set empty? 
        return p_set.isEmpty();
    }
    public int size() {
        // number of points in the set 
        return p_set.size();
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        p_set.add(p);
    }
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        return p_set.contains(p);
    }
    public void draw() {
        // draw all points to standard draw 
        for(Point2D p: p_set) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle 
        List<Point2D> point_list = new LinkedList<Point2D>();
        for (Point2D p:p_set) {
            if (rect.distanceTo(p)==0.0) {
                point_list.add(p);
            }
        }
        return point_list;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty 
        if (p_set == null) return null;
        Point2D pp = null;
        double dis = Double.MAX_VALUE;
        for (Point2D i : p_set) {
            if (i.distanceTo(p) < dis) {
                dis = i.distanceTo(p);
                pp = i;
            }
        }
        return pp;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional) 
    }
}