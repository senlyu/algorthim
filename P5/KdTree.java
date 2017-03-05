import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.TrieSET;

public class KdTree {

    private Node root;
    private int n;
    
    private class Node implements Comparable<Node> {
        private Point2D p;
        private Node left, right;
        private RectHV rect;
        private boolean dir;
        public Node(Point2D p, boolean dir, RectHV rect) {
            this.p = p;
            this.dir = dir;
            this.rect = rect;
            this.left = null;
            this.right = null;
        }
        @Override
        public int compareTo(Node q) {
            if (dir) {
                if (this.p.x()>q.p.x()) return 1;
                else if (Math.abs(this.p.x()-q.p.x())<1e-10) return 0;
                else return -1;
            } else {
                if (this.p.y()>q.p.y()) return 1;
                else if (Math.abs(this.p.y()-q.p.y())<1e-10) return 0;
                else return -1;
            }
        }
    }
    
    private boolean insert1(Node now, Point2D p) {
        if (now.p.equals(p)) return false;
        Node newnode = new Node(p, !now.dir, null);
        if (now.compareTo(newnode)>0) {
            // left
            RectHV newrect = null;
            if (now.dir) {
                newrect = new RectHV(now.rect.xmin(),now.rect.ymin(),now.p.x(),now.rect.ymax());
            } else {
                newrect = new RectHV(now.rect.xmin(),now.rect.ymin(),now.rect.xmax(),now.p.y());
            }
            if (now.left != null) {
                return insert1(now.left, p);
            } else {
                now.left = new Node(p, !now.dir, newrect);
                return true;
            }
        } else {
            // right
            RectHV newrect = null;
            if (now.dir) {
                newrect = new RectHV(now.p.x(),now.rect.ymin(),now.rect.xmax(),now.rect.ymax());
            } else {
                newrect = new RectHV(now.rect.xmin(),now.p.y(),now.rect.xmax(),now.rect.ymax());
            }
            if (now.right != null) {
                return insert1(now.right, p);
            } else {
                now.right = new Node(p, !now.dir, newrect);
                return true;
            }
            
        }
        
    }
    
    private boolean search1(Node now, Point2D p) {
        if (now.p.equals(p)) return true;
        if (now.p.compareTo(p)>0) {
            // left
            if (now.left != null) {
                return search1(now.left, p);
            } else {
                return false;
            }
        } else {
            // right
            if (now.right != null) {
                return search1(now.right, p);
            } else {
                return false;
            }
        }
    }
    
    private void rangesearch(Node now, RectHV rect, SET<Point2D> set) {
        if (now == null) return;
        if (now.rect.intersects(rect)) {
            if (rect.contains(now.p)) {
                set.add(now.p);
            }
            rangesearch(now.left, rect, set);
            rangesearch(now.right, rect, set);
        }
    }
    
    private void draw1(Node now, double minX, double maxX, double minY, double maxY) {
        if (now == null) return;

        StdDraw.setPenRadius(.001);
        if (now.dir) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(now.p.x(), minY, now.p.x(), maxY);
            draw1(now.left, minX, now.p.x(), minY, maxY);
            draw1(now.right, now.p.x(), maxX, minY, maxY);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(minX, now.p.y(), maxX, now.p.y());
            draw1(now.left, minX, maxX, minY, now.p.y());
            draw1(now.right, minX, maxX, now.p.y(), maxY);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        now.p.draw();
    }
    
    private Point2D nearestHelper(Node node, RectHV rect, double x, double y, Point2D nearestPointCandidate) {
        Point2D nearestPoint = nearestPointCandidate;

        if (node != null) {
            Point2D queryPoint = new Point2D(x, y);

            if (nearestPoint == null || queryPoint.distanceSquaredTo(nearestPoint) > rect.distanceSquaredTo(queryPoint)) {

                Point2D nodePoint = new Point2D(node.p.x(), node.p.y());

                if (nearestPoint == null) {
                    nearestPoint = nodePoint;
                } else {
                    if (queryPoint.distanceSquaredTo(nearestPoint) > queryPoint.distanceSquaredTo(nodePoint)) {
                        nearestPoint = nodePoint;
                    }
                }

                if (node.dir) {
                    if (x <= node.p.x()) {
                        if(node.left!=null) nearestPoint = nearestHelper(node.left, node.left.rect, x, y, nearestPoint);
                        if(node.right!=null) nearestPoint = nearestHelper(node.right, node.right.rect, x, y, nearestPoint);
                    } else {
                        if(node.right!=null) nearestPoint = nearestHelper(node.right, node.right.rect, x, y, nearestPoint);
                        if(node.left!=null) nearestPoint = nearestHelper(node.left, node.left.rect, x, y, nearestPoint);
                    }
                } else {
                    if (y <= node.p.y()) {
                        if(node.left!=null) nearestPoint = nearestHelper(node.left, node.left.rect, x, y, nearestPoint);
                        if(node.right!=null) nearestPoint = nearestHelper(node.right, node.right.rect, x, y, nearestPoint);
                    } else {
                        if(node.right!=null) nearestPoint = nearestHelper(node.right, node.right.rect, x, y, nearestPoint);
                        if(node.left!=null) nearestPoint = nearestHelper(node.left, node.left.rect, x, y, nearestPoint);
                    }
                }
            }
        }
        return nearestPoint;
    }
    
    public KdTree() {
        // construct an empty set of points 
        this.root = null;
        this.n = 0;
    }
    public boolean isEmpty() {
        // is the set empty? 
        return n==0;
    }
    public int size() {
        // number of points in the set 
        return n;
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (this.n ==0) {
            this.root = new Node(p, true, new RectHV(0,0,1,1));
            this.n++;
        } else {
            if (insert1(root, p)) this.n++;
        }
    }
    
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        if (isEmpty()) return false;
        return search1(root, p);
    }
    public void draw() {
        // draw all points to standard draw 
        if (isEmpty()) return;
        draw1(root, 0, 1, 0, 1);
    }
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle 
        SET<Point2D> point_list = new SET<Point2D>();
        rangesearch(root, rect, point_list);
        return point_list;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty 
        return nearestHelper(root, new RectHV(0,0,1,1), p.x(), p.y(), null);
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional) 
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        Point2D p = null;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.print(kdtree.contains(p));
        
        
    }
}