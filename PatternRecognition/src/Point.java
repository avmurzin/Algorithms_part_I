
import java.util.Comparator;

/**
 * Point data type for algs4 Lab_3.
 * data 01.03.2014
 * execute Point
 * @author Andrei Murzin
 */
public class Point implements Comparable<Point> {
    /**
     * x coordinate
     */
    private final int x;
    /**
     * y coordinate
     */
    private final int y;

    // create the point (x, y)
    /**
     * .
     * @param x dekart coord
      * @param y dekart coord
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * compare points by slope.
     */
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    /**
     * Inner class for Comparator.
     * @author murzin
     *
     */
    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point arg0, Point arg1) {
            int x0 = Point.this.x;
            int y0 = Point.this.y;
            int x1 = arg0.x;
            int y1 = arg0.y;
            int x2 = arg1.x;
            int y2 = arg1.y;
            //if slopes is equal
//            if ((x1 == x2) && (y1 == y2)) {
            if (Point.this.slopeTo(arg0) == Point.this.slopeTo(arg1)) {
                return 0;
            }
            if (Point.this.slopeTo(arg0) < Point.this.slopeTo(arg1)) {
//            if ((y1 - y0) / (x1 - x0) < (y2 - y0) / (x2 - x0)) {
                return -1;
            } else {
                return 1;
            }


        }

    }



    /**
     *  plot this point to standard drawing.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     *  draw line between this point and that point to standard drawing.
     * @param that
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     *  slope between this point and that point.
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
        if ((that.x == this.x) && (that.y != this.y)) {
            return Double.POSITIVE_INFINITY;
        }
        if ((that.x == this.x) && (that.y == this.y)) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.y == this.y) {
            return (1.0 - 1.0)/1.0;
        }
        return (double) (that.y - this.y)/(that.x - this.x);
    }

    /**
     * is this point lexicographically smaller than that one.
     * comparing y-coordinates and breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int x0 = this.x;
        int y0 = this.y;
        int x1 = that.x;
        int y1 = that.y;
        if ((y0 == y1) && (x0 == x1)) {
            return 0;
        }
        if ((y0 < y1) || ((y0 == y1) && (x0 < x1))) {
            return -1;
        } else {
            return 1;
        }

    }

    /**
     * return string representation of this point.
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * unit test.
     * @param args
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
