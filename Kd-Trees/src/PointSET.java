/**
 * PointSET data structure for algs4 Lab_5.
 * data 15.03.2014
 * execute
 * @author Andrei Murzin
 */
public class PointSET {
    /**
     * 
     */
//    private static final double MAX_R = 10.0;
    /**
     * BST for points.
     */
    private SET<Point2D> set;
    /**
     * construct an empty set of points.
     */
    public PointSET() {
        set = new SET<Point2D>();
    }

    /**
     * Is the set empty?
     * @return boolean
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Number of points in the set.
     * @return number of points
     */
    public int size() {
        return set.size();
    }

    /**
     * Add the point p to the set (if it is not already in the set).
     * @param p is a Point2D object
     */
    public void insert(Point2D p) {
        set.add(p);
    }

    /**
     * Does the set contain the point p?
     * @param p is a Point2D object
     * @return boolean
     */
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    /**
     * Draw all of the points to unit square.
     */
    public void draw() {
        // draw all of the points to standard draw
        StdDraw.show(0);
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
        StdDraw.show(0);
    }

    /**
     * All points in the set that are inside the rectangle.
     * @param rect - an object of RectHV class
     * @return Stack of points.
     */
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }
        return stack;
    }

    /**
     * A nearest neighbor in the set to p; null if set is empty.
     * @param p is Point2D object
     * @return Point2D object
     */
    public Point2D nearest(Point2D p) {
        Point2D retP = null;
        for (Point2D p1 : set) {
            if (retP == null) {
                retP = p1;
            } else {
//                if (!p1.equals(p)
//                   && (p.distanceSquaredTo(p1) < p.distanceSquaredTo(retP))) {
                if (p.distanceSquaredTo(p1) < p.distanceSquaredTo(retP)) {
                    retP = p1;
                }
            }
        }
        return retP;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        String name = args[0];
        In file = new In(name);
        while (!file.isEmpty()) {
            double x = file.readDouble();
            double y = file.readDouble();
            Point2D p = new Point2D(x, y);
            pointSET.insert(p);
        }
        pointSET.draw();
        Point2D p1 = new Point2D(.81, .10);
        StdOut.println("Nearest: " + pointSET.nearest(p1));
        for (Point2D p : pointSET.range((RectHV) new RectHV(0, 0, 1, 1))) {
            StdOut.println(p);
        }
    }

}
