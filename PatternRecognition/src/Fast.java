import java.util.Arrays;
/**
 * Fast sorting algorithm algs4 Lab_3.
 * data 01.03.2014
 * execute Point
 * @author Andrei Murzin
 */
public class Fast {
    /**
     * Points array dimension.
     */
    private static int N;
    /**
     * Points array for sorting.
     */
    private static Point[] a;
    /**
     * Points array for pivots.
     */
    private static Point[] b;
    /**
     * Input data file.
     */
    private static In file;
    /**
     * @param args
     */
    private static void arrayShow(String message, Point[] a) {
        StdOut.print(message + ": ");
        for (int i = 0; i < N; i++) {
            StdOut.print(a[i].toString() + "-");
        }
        StdOut.println();
    }
    /**
     * .
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
        b = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = file.readInt();
            int y = file.readInt();
            Point p = new Point(x, y);
            a[i] = p;
            b[i] = p;
            p.draw();
            //StdOut.println(x + ":" + y);
        }

        int count_lo = 0;
        int count_hi = 0;
        Arrays.sort(b, 0, N);
        for (int j = 0; j < N; j++) {
            Arrays.sort(a, b[j].SLOPE_ORDER);
            for (int k = 1; k < N; k++) {
                if ((b[j].slopeTo(a[k]) == b[j].slopeTo(a[k - 1]))
                        && (k != (N - 1))) {
                    count_hi++;
                } else {
                    if ((k == (N - 1)) && (b[j].slopeTo(a[k])
                            == b[j].slopeTo(a[k - 1]))) {
                        count_hi++;
                    }
                    if (count_hi > 1) {
                        Arrays.sort(a, count_lo, count_lo + count_hi + 1);
                        if (b[j].compareTo(a[count_lo]) < 0) {
                            StdOut.print(b[j].toString() + " -> ");
                            for (int l = count_lo; l < (count_lo + count_hi);
                                    l++) {

                                StdOut.print(a[l].toString() + " -> ");
                            }
                            StdOut.println(a[count_lo + count_hi].toString());
                            b[j].drawTo(a[count_lo + count_hi]);
                        }


                    }
                    count_lo = k;
                    count_hi = 0;
                }
            }
        }

        StdDraw.show(0);
    }

}
