/**
 * Solver data structure for algs4 Lab_4.
 * data 10.03.2014
 * execute
 * @author Andrei Murzin
 */
public class Solver {
    private Stack<Board> stack1 = new Stack<Board>(); //for solution 1
    private int moves = 0; //moves number for solution
    private boolean solvable = false; //true if solution exists
    /**
     *
     * @author murzin
     *
     */
    private class KeyBoard implements Comparable<KeyBoard> {
        /**
         * Puzzle board.
         */
        private Board board;
        /**
         * Pointer to previous.
         */
        private KeyBoard previous;
        /**
         * Comparable key for hamming or manhattan (without moves).
         */
        private int key;
        /**
         * moves.
         */
        private int move;
        /**
         * Constructor.
         * @param board
         * @param key
         */
        public KeyBoard(Board board, int key, int move) {
            this.board = board;
            this.key = key;
            this.move = move;
        }
        /**
         * Compare method for Comparable implementation.
         * @return
         * @param
         */
        public int compareTo(KeyBoard that) {
            if (this.key + this.move > that.key + that.move) { return 1; }
            if (this.key + this.move < that.key + that.move) { return -1; }
            return 0;
        }

        /**
         * return Board.
         * @return
         */
        public Board getBoard() {
            return board;
        }

        /**
         * Get move for board.
         * @return
         */
        public int getMove() {
            return move;
        }
    }

    /**
     * Find a solution to the initial board (using the A* algorithm).
     * @param initial
     */
    public Solver(Board initial) {
        MinPQ<KeyBoard> minPQ1 = new MinPQ<KeyBoard>();
        MinPQ<KeyBoard> minPQ2 = new MinPQ<KeyBoard>();
        KeyBoard tempBoard, tempBoard1, tempBoard2;
        //KeyBoard keyBoard = new KeyBoard(initial, 0);

        Board prevBoard1 = initial;
        Board prevBoard2 = initial.twin();
        tempBoard1 = new KeyBoard(initial, initial.manhattan(), 0);
        tempBoard1.previous = null;
        minPQ1.insert(tempBoard1);
        tempBoard2 = new KeyBoard(initial.twin(), initial.manhattan(), 0);
        minPQ2.insert(tempBoard2);
        KeyBoard top = null; //top pointer

        while (!tempBoard1.getBoard().isGoal()
                && !tempBoard2.getBoard().isGoal()) {
            tempBoard1 = minPQ1.delMin();

            top = tempBoard1;

            if (tempBoard1.previous != null) {
                prevBoard1 = tempBoard1.previous.getBoard();
            } else {
                prevBoard1 = tempBoard1.getBoard();
            }

            tempBoard2 = minPQ2.delMin();
            if (tempBoard2.previous != null) {
                prevBoard2 = tempBoard2.previous.getBoard();
            } else {
                prevBoard2 = tempBoard2.getBoard();
            }

            for (Board b : tempBoard1.getBoard().neighbors()) {
                if (!b.equals(prevBoard1)) {
                    tempBoard = new KeyBoard(b, b.manhattan(),
                            tempBoard1.getMove() + 1);
                    tempBoard.previous = top;
                    minPQ1.insert(tempBoard);
                }
            }
            for (Board b : tempBoard2.getBoard().neighbors()) {
                if (!b.equals(prevBoard2)) {
                    minPQ2.insert(new KeyBoard(b, b.manhattan(),
                            tempBoard2.getMove() + 1));
                }
            }
        }
        if (tempBoard1.getBoard().isGoal()) {
            solvable = true;

            if (top == null) {
                stack1.push(tempBoard1.getBoard());
                moves = 0;
            } else {
                moves = top.getMove();
                while (top != null) {
                    stack1.push(top.getBoard());
                    top = top.previous;

                }
            }
        } else {
            solvable = false;
            moves = -1;
        }

    }
    /**
     *  Is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
       return solvable;
    }
    
    /**
     * Number moves for solution.
     * @return
     */
    public int moves() {
        return moves;
    }
    /**
     * Solution.
     * @return
     */
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return stack1;
        }
        return null;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}
