/**
 * Percolation data type for algs4 Lab_1.
 * data 14.02.2014
 * execute Percolation <N>
 * @author Andrei Murzin
 */
public class Percolation {
    /**
     * linear array for union manipulation.
     */
    private WeightedQuickUnionUF gridUF;
    /**
     * linear array for union manipulation.
     */
    private WeightedQuickUnionUF gridUF1;
    /**
     * Grid for percolation presentation. Be carefully then the public methods
     * mean 1 <= i,j <= N,  while grid index belongs [0;N-1]
     */
    private boolean[][] grid;
    /**
     * Last site in the bottom row (last site in the gridUF object).
     */
    private int lastBottomSite;
    /**
     * Last site in the grid.
     */
    private int lastSite;
    /**
     * Dimention of percolate grid.
     */
    private int gridDimention;

 /**
  * Generate linear array (N*N + 2*N) for represent N*N grid and top and bottom
  * rows for check full site state or percolation.
  * All sites at top row are connected, all sites at bottom row too.
  * @param N the grid dimension
  */
    public Percolation(int N) {
        int i, j;
        gridUF = new WeightedQuickUnionUF(N * N + 2 * N);
        gridUF1 = new WeightedQuickUnionUF(N * N + 2 * N);
        lastBottomSite = (N * N) + (2 * N) - 1;
        lastSite = N * N + N - 1;
        gridDimention = N;
        // make top and bottom unions
        for (j = 0; j < N; ++j) {
            //union all of top sites
            gridUF.union(j, 0);
            //union all of bottom sites
            gridUF.union(lastSite + 1 + j, lastBottomSite);
        }
        // make greed for visualisation and so on
        grid = new boolean[N][N];
        for (i = 0; i < N; ++i) {
            for (j = 0; j < N; ++j) {
                grid[i][j] = false;
            }
        }
    }
   /**
    * Open site (row i, column j) if it is not already
    * and connect it if opened site is near.
    * @param i - row
    * @param j - column
    */
    public void open(int i, int j) {
        if ((i > gridDimention) || (j > gridDimention)
                || (i <= 0) || (j <= 0)) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;
            connectToNeighbor(i, j);
        }
    }
    /**
     * Check is site (row i, column j) open?
     * @param i - row
     * @param j - column
     * @return - true if site is open
     */
    public boolean isOpen(int i, int j) {
        if ((i > gridDimention) || (j > gridDimention)
                || (i <= 0) || (j <= 0)) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        return grid[i - 1][j - 1];
    }
   /**
    * Check is site (row i, column j) full? (Mean connected to top row)
    * @param i - row
    * @param j - column
    * @return true if site is full
    */
    public boolean isFull(int i, int j) {
        if ((i > gridDimention) || (j > gridDimention)
                || (i <= 0) || (j <= 0)) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        return gridUF1.connected(0, i * gridDimention + j -1);
    }
    /**
     * Check does the system percolate? (Mean top row connected to bottom row)
     * @return true if percolates
     */
    public boolean percolates() {
        return gridUF.connected(0, lastBottomSite);
    }

    /**
     * Connect site to opened neighbors.
     * @param i row
     * @param j column
     */
    private void connectToNeighbor(int i, int j) {
        int k = i * gridDimention + j - 1;
        // if site at top row connect it to
        if (i == 1) {
            gridUF.union(k, 0);
            gridUF1.union(k, 0);
        }
        if (i == gridDimention) {
                gridUF.union(k, lastBottomSite);
        }
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                gridUF.union(k, k - 1);
                gridUF1.union(k, k - 1);
            }
        }
        if (j < gridDimention) {
            if (isOpen(i, j + 1)) {
                gridUF.union(k, k + 1);
                gridUF1.union(k, k + 1);
            }
        }
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                gridUF.union(k, k - gridDimention);
                gridUF1.union(k, k - gridDimention);
            }
        }
        if (i < gridDimention) {
            if (isOpen(i + 1, j)) {
                gridUF.union(k, k + gridDimention);
                gridUF1.union(k, k + gridDimention);
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage: Percolation <grid dimention>");
        } else {
            StdOut.println("Grid dimention: " + Integer.parseInt(args[0]));
            Percolation test = new Percolation(Integer.parseInt(args[0]));
            for (int i = 1; i <= Integer.parseInt(args[0]); ++i) {
                test.open(i, Integer.parseInt(args[0]));
            }
            if (test.isOpen(1, 1)) { StdOut.println("Site (1, 1) is open"); }
            if (test.isFull(1, 1)) { StdOut.println("Site (1, 1) is full"); }
            if (test.percolates()) { StdOut.println("Grid is percolates"); }
            for (int i = 1; i <= Integer.parseInt(args[0]); ++i) {
                for (int j = 1; j <= Integer.parseInt(args[0]); ++j) {
                    if (test.isOpen(i, j)) { StdOut.print("0");
                    } else { StdOut.print("1"); }
                }
                StdOut.println();
            }
        }
    }
}
