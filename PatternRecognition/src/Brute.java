import java.util.Arrays;
/**
 * Brute force algorithm algs4 Lab_3.
 * data 01.03.2014
 * execute Point
 * @author Andrei Murzin
 */
public class Brute {
    /**
     * array dimension.
     */
    private static int N;
    /**
     * array fo store Point.
     */
    private static Point[] a;
    /**
     * File for input data.
     */
    private static In file;
    /**
     * .
     * @param j
     * @param k
     * @param l
     * @param m
     * @return true or false for collinear checking.
     */
    private static boolean isCollinear(int j, int k, int l, int m) {
        if (a[j].slopeTo(a[k]) == a[j].slopeTo(a[l])) {
            if (a[j].slopeTo(a[k]) == a[j].slopeTo(a[m])) {
                return true;
            } else { return false; }
        } else { return false; }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        String name = args[0];
        file = new In(name);

        N = file.readInt();
        a = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = file.readInt();
            int y = file.readInt();
            Point p = new Point(x, y);
            a[i] = p;
            p.draw();
            //StdOut.println(x + ":" + y);
        }
        Arrays.sort(a, 0, N);

        for (int j = 0; j <= (N - 4); j++) {
            for (int k = j + 1; k <= (N - 3); k++) {
                for (int l = k + 1; l <= (N - 2); l++) {
                    for (int m = l + 1; m <= (N - 1); m++) {
                        if (isCollinear(j, k, l, m)) {
                            StdOut.println(a[j].toString() + " -> "
                                    + a[k].toString() + " -> "
                                    + a[l].toString() + " -> "
                                    + a[m].toString() + "");
                            a[j].drawTo(a[m]);
                        }
                    }
                }
            }
        }

        StdDraw.show(0);
    }

}
