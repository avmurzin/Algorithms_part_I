/**
 * KdTree data structure for algs4 Lab_5.
 * data 15.03.2014
 * execute
 * @author Andrei Murzin
 */
public class KdTree {

    private Node root; //pointer to root node
    private int N = 0; //numbers of set elements
    private static final boolean RED = true; //constant for node color
    private static final boolean BLUE = false; //constant for node color
    private static final boolean LEFT = true; //constant for node side
    private static final boolean RIGHT = false; //constant for node side
    private Stack<Node> stack = new Stack<Node>();
    private Stack<Point2D> stackRange;
    private Point2D nearestPoint = null;
    /**
     * Data structure for nodes.
     * @author murzin
     *
     */
    private class Node {
        private Point2D point; //point on unit square
        private boolean color; //isRed==true for vertical node
        private RectHV rect;   //rectangle associated to point
        private Node left = null, right = null; //pointers to subtrees
        /**
         * Constructor.
         * @param point - point at unit square
         * @param isRed - true if node is red
         * @param rect - associated rectangle
         */
        public Node (Point2D point, boolean color, RectHV rect) {
            this.point = point;
            this.color = color;
            this.rect = rect;
        }
    }

    /**
     * Construct an empty set of points.
     */
    public KdTree() {
    }

    /**
     * Is the set empty?
     * @return true if set is empty
     */
    public boolean isEmpty() {
        return root == null;
    }
    /**
     * Number of points in the set.
     * @return integer
     */
    public int size() {
        return N;
    }
    /**
     * Add the point p to the set (if it is not already in the set).
     * @param p is Point2D object
     */
    public void insert(Point2D p) {
//        N++;
        put(p);
    }

    /**
     * Does the set contain the point p?
     * @param p - Point2D object for search
     * @return true if exists
     */
    public boolean contains(Point2D p) {
        return get(p);
    }

    /**
     * Draw all of the points to standard draw.
     */
    public void draw() {
        for (Node node : getAllPoint()) {
            if (node.color == RED) {
                StdDraw.setPenColor();
                StdDraw.setPenRadius(.01);
                StdDraw.point(node.point.x(), node.point.y());
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(),
                        node.rect.ymax());
            }
            if (node.color == BLUE) {
                StdDraw.setPenColor();
                StdDraw.setPenRadius(.01);
                StdDraw.point(node.point.x(), node.point.y());
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(),node.point.y(), node.rect.xmax(), 
                        node.point.y());
            }
        }
    }
    /**
     * All points in the set that are inside the rectangle.
     * @param rect
     * @return stack
     */
    public Iterable<Point2D> range(RectHV rect) {
        stackRange = new Stack<Point2D>();
        preorderRec(root, rect);
        return stackRange;
    }
    /**
     * A nearest neighbor in the set to p; null if set is empty.
     * @param p
     * @return nearest point
     */
    public Point2D nearest(Point2D p) {
        nearestPoint = null;
        preorderNearest(root, p);
        return nearestPoint;
    }
    /**
     * Internal method.
     */
    private void put(Point2D point) {
        //поместить узел по указанному указателю, содержащий point
        // указанного цвета с указанным прямоугольником
//        root = put(root, point, RED, new RectHV(0, 0, 1, 1));
        root = put(root, point, RED, null, LEFT);
    }
    /**
     * Internal put method for iteration.
     * @param x - Node
     * @param point - inserting point
     * @param color - color of inserting node
     * @param rec - inserting rectangle
     * @return new node
     */
    private Node put(Node x, Point2D point, boolean color, Node previous,
            boolean side) {
        RectHV rec;
        if (x == null) {
            N++;
            if (previous == null) {
                rec = new RectHV(0, 0, 1, 1);
            } else {
                rec = getRect(previous, previous.color, side);
            }
            return new Node(point, color, rec);
        }
        if (!point.equals(x.point)) {
            if (x.color == RED) {
                int cmp = compareX(point, x.point);
                if (cmp < 0) {
                    x.left = put(x.left, point, BLUE, x, LEFT);
                } else {
                    x.right = put(x.right, point, BLUE, x, RIGHT);
                }
            }
            if (x.color == BLUE) {
                int cmp = compareY(point, x.point);
                if (cmp < 0) {
                    x.left = put(x.left, point, RED, x, LEFT);
                } else {
                    x.right = put(x.right, point, RED, x, RIGHT);
                }
            }
        }
        return x;
    }
    /**
     * Return all points.
     * @return stack with points
     */
    private Iterable<Node> getAllPoint() {
        preorder(root);
        return stack;
    }
    /**
     * Recursion for traverse tree to get all nodes.
     * @param node
     */
    private void preorder(Node node) {
        if (node == null) {
            return;
        }
        stack.push(node);
        preorder(node.left);
        preorder(node.right);
    }
    /**
     * Recursion for traverse tree to get only nodes that rectangle contains.
     * @param node
     * @param rect
     */
    private void preorderRec(Node node, RectHV rect) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.point)) {
            stackRange.push(node.point);
        }
        if (node.rect.intersects(rect)) {
            preorderRec(node.left, rect);
            preorderRec(node.right, rect);
        }
    }
    private void preorderNearest(Node node, Point2D point) {
        boolean bool = false;
        if (node == null) {
            return;
        }
        if (nearestPoint == null)  {
            nearestPoint = node.point;
        }
        if (point.distanceSquaredTo(nearestPoint)
                > node.rect.distanceSquaredTo(point)) {
            if (node.point.equals(point)) {
                nearestPoint = node.point;
                return;

            } else {
                if (point.distanceSquaredTo(node.point)
                        < point.distanceSquaredTo(nearestPoint)) {
                    nearestPoint = node.point;
                }
            }
            if (node.left != null) {
                if (node.left.rect.contains(point)) {
                    bool = true;
                }
            }

            if (bool) {
                preorderNearest(node.left, point);
                preorderNearest(node.right, point);
            } else {
                preorderNearest(node.right, point);
                preorderNearest(node.left, point);
            }
        }
    }
    /**
     * Get rectangle for next level node as appropriate part parent rectangle.
     * @param node - parent node
     * @param color - parent color node
     * @param side - side of child node
     * @return RectHV
     */
    private RectHV getRect(Node node, boolean color, boolean side) {
        if (color == RED) {
            if (side == LEFT) {
                double minX = node.rect.xmin();
                double minY = node.rect.ymin();
                double maxX = node.point.x();
                double maxY = node.rect.ymax();
                return new RectHV(minX, minY, maxX, maxY);
            } else { //side == RIGHT
                double minX = node.point.x();
                double minY = node.rect.ymin();
                double maxX = node.rect.xmax();
                double maxY = node.rect.ymax();
                return new RectHV(minX, minY, maxX, maxY);
            }
        } else { //color == BLUE
            if (side == LEFT) {
                double minX = node.rect.xmin();
                double minY = node.rect.ymin();
                double maxX = node.rect.xmax();
                double maxY = node.point.y();
                return new RectHV(minX, minY, maxX, maxY);
            } else { //side == RIGHT
                double minX = node.rect.xmin();
                double minY = node.point.y();
                double maxX = node.rect.xmax();
                double maxY = node.rect.ymax();
                return new RectHV(minX, minY, maxX, maxY);
            }
        }

    }
    /**
     * Compare X method.
     * @param p1
     * @param p2
     * @return comparator
     */
    private int compareX(Point2D p1, Point2D p2) {
        if (p1.x() < p2.x()) {
            return -1;
        } else return 1;
    }
    /**
     * Compare Y method.
     * @param p1
     * @param p2
     * @return
     */
    private int compareY(Point2D p1, Point2D p2) {
        if (p1.y() < p2.y()) {
            return -1;
        } else return 1;     
    }
    /**
     * Internal get.
     * @param point
     * @return
     */
    private boolean get(Point2D point) {
        return get(root, point);
    }
    /**
     * Internal get.
     * @param x
     * @param point
     * @return
     */
    private boolean get(Node x, Point2D point) {
        if (x == null) return false;
        if (x.point.equals(point)) {
            return true;
        }
        if (x.color == RED) {
            int cmp = compareX(point, x.point);
            if (cmp < 0) {
                return get(x.left, point);
            } else {
                return get(x.right, point);
            }
        } else { //x.color == BLUE
            int cmp = compareY(point, x.point);
            if (cmp < 0) {
                return get(x.left, point);
            } else {
                return get(x.right, point);
            }
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
////        KdTree tree = new KdTree();
////        tree.insert(new Point2D(0.485, 0.52));
//        Point2D p = new Point2D(0.485, 0.52);
//        RectHV rec = new RectHV(0.088, 0.766, 0.52, 0.543);
//        StdOut.println(rec.contains(p));

    }

}
