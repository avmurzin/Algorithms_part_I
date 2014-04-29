/**
 * PercolationStats data type for algs4 Lab_1.
 * Perform T independent computational experiments on an N-by-N grid
 * data 14.02.2014
 * execute PercolationStats <N> <T>
 * @author Andrei Murzin
 *
 */
public class PercolationStats {
    private static final double COEFF = 1.96;
    /**
     * T-dimension array for computation data store.
     */
    private double[] experimentArray;
    /**
     * Statistical values.
     */
    private double mMean, mStddev;
    /**
     * N & T.
     */
    private int mT;
    /**
     * Make T experiments by N-to-N grid.
     * @param N grid dimension
     * @param T independent computational experiments
     */
    public PercolationStats(int N, int T) {
        if ((N <= 0) || (T <= 0)) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        //mN = N;
        mT = T;
        int l, m;
        int count = 0;
        experimentArray = new double[T];

        for (int i = 1; i <= T; ++i) {
            Percolation experimentGrid = new Percolation(N);
            while (!experimentGrid.percolates()) {
                l = StdRandom.uniform(1, N + 1);
                m = StdRandom.uniform(1, N + 1);
                if (!experimentGrid.isOpen(l, m)) {
                    ++count;
                }
                experimentGrid.open(l, m);
             }
            experimentArray[i - 1] = (double) count / (N * N);

            count = 0;

        }
    }

    /**
     * Sample mean of percolation threshold.
     * @return mean of experimentArray
     */
    public double mean() {
        mMean = StdStats.mean(experimentArray);
        return mMean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     * @return standard deviation of experimentArray
     */
    public double stddev() {
        mStddev = StdStats.stddev(experimentArray);
        return mStddev;
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     * @return
     */
    public double confidenceLo() {
        return StdStats.mean(experimentArray) - COEFF
                * StdStats.stddev(experimentArray) / Math.sqrt(mT);
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     * @return
     */
    public double confidenceHi() {
        return StdStats.mean(experimentArray) + COEFF
                * StdStats.stddev(experimentArray) / Math.sqrt(mT);
    }
    /**
     * Test client.
     * @param args - N T (T independent computational experiments on an
     * N-by-N grid)
     */
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int t = Integer.parseInt(args[1]);
        PercolationStats experiment = new PercolationStats(n, t);
        StdOut.println("95% confidence inteval = " + experiment.confidenceLo()
                                            + ", " + experiment.confidenceHi());
        StdOut.println("mean = " + experiment.mean());
        StdOut.println("stddev = " + experiment.stddev());

    }

}
