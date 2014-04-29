/**
 * Board data structure for algs4 Lab_4.
 * data 09.03.2014
 * execute
 * @author Andrei Murzin
 */
public class Board {
    /**
     * Board dimension.
     */
    private int N;
    /**
     * Board array.
     */
    private int[][] board;
    /**
     * blank element coordinats.
     */
    private int blankI, blankJ;

    /**
     * construct a board from an N-by-N array of blocks.
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks
     */
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
//                if ((blocks[i][j] > 0) && (blocks[i][j] <= N)) {
                if (blocks[i][j] > 0) {
                    board[i][j] = blocks[i][j];
                } else {
                    board[i][j] = 0;
                    blankI = i;
                    blankJ = j;
                }

            }
        }
    }

    /**
     * board dimension N.
     * @return N
     */
    public int dimension() {
        return N;
    }

    /**
     * Hamming priority function mean number of blocks out of place.
     * @return
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((board[i][j] != (i * N + j + 1)) && (board[i][j] != 0)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculate sum of Manhattan distances between blocks and goal.
     * @return
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((board[i][j] != 0) && (board[i][j] != (i * N + j + 1))) {
                    int l = 0;
                    int m = 0;
                    if ((board[i][j] % N) != 0) {
                        l = board[i][j] / N; //"l" must be "i"
                        m = (board[i][j] % N) - 1; //"m" must be "j"
                    } else {
                        l = board[i][j] / N - 1;
                        m = N - 1;
                    }
                    count += Math.abs(i - l) + Math.abs(j - m);
//                    StdOut.println(board[i][j] + " : "
//                    + (Math.abs(i - l) + Math.abs(j - m)) + " : "
//                            + count + " : "
//                            + "i=" + i + " j=" + j + " l=" + l + " m=" + m);
                }
            }
        }
        return count;
    }

    /**
     * Is this board the goal board?
     * @return true if board completed
     */
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((board[i][j] != (i * N + j + 1)) && !((i == N - 1)
                        && (j == N - 1))) {

                        return false;
                }
            }
        }
        return true;
    }

    /**
     * A board obtained by exchanging two adjacent blocks in the same row.
     * @return twined Board
     */
    public Board twin() {
        int[][] tempBoard = new int[N][N];
        Board twinBoard = null;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        for (int i = 0; i < N; i++) {
            if ((tempBoard[i][0] != 0) && (tempBoard[i][1] != 0)) {
                int temp = tempBoard[i][0];
                tempBoard[i][0] = tempBoard[i][1];
                tempBoard[i][1] = temp;
                twinBoard = new Board(tempBoard);
                break;
            }
        }
        return twinBoard;
    }

    /**
     * Does this board equal y?
     * @return true if equal
     */
    public boolean equals(Object y) {
        if (y == this) { return true; }
        if (y == null) { return false; }
        if (y.getClass() != this.getClass()) { return false; }
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) { return false; }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * all neighboring boards.
     * @return Stack<Board> object with all neighbors
     */
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int[][] tempBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        if (blankI > 0) {
            tempBoard[blankI][blankJ] = tempBoard[blankI - 1][blankJ];
            tempBoard[blankI - 1][blankJ] = 0;
            stack.push(new Board(tempBoard));
            tempBoard[blankI - 1][blankJ] = tempBoard[blankI][blankJ];
            tempBoard[blankI][blankJ] = 0;
        }
        if (blankI < N - 1) {
            tempBoard[blankI][blankJ] = tempBoard[blankI + 1][blankJ];
            tempBoard[blankI + 1][blankJ] = 0;
            stack.push(new Board(tempBoard));
            tempBoard[blankI + 1][blankJ] = tempBoard[blankI][blankJ];
            tempBoard[blankI][blankJ] = 0;
        }
        if (blankJ > 0) {
            tempBoard[blankI][blankJ] = tempBoard[blankI][blankJ - 1];
            tempBoard[blankI][blankJ - 1] = 0;
            stack.push(new Board(tempBoard));
            tempBoard[blankI][blankJ - 1] = tempBoard[blankI][blankJ];
            tempBoard[blankI][blankJ] = 0;
        }
        if (blankJ < N - 1) {
            tempBoard[blankI][blankJ] = tempBoard[blankI][blankJ + 1];
            tempBoard[blankI][blankJ + 1] = 0;
            stack.push(new Board(tempBoard));
            tempBoard[blankI][blankJ + 1] = tempBoard[blankI][blankJ];
            tempBoard[blankI][blankJ] = 0;
        }
        return stack;
    }

    /**
     * string representation of the board(in the output format specified below).
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println("manhattan = " + initial.manhattan());
        StdOut.println("twinnabled = ");
        StdOut.println(initial.twin());
        StdOut.println("Neighbors are = ");
        for (Board b : initial.neighbors()) {
            StdOut.println(b);
            StdOut.println(b.manhattan());
        }
    }

}
