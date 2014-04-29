/**
 * Subset client for algs4 Lab_2.
 * data 18.02.2014
 * execute Deque <N>
 * @author Andrei Murzin
 * @param k
 */
public class Subset {
    /**
     * @param args
     */

    public static void main(String[] args) {
        RandomizedQueue<String> rQ = new RandomizedQueue<String>();
        int k = 0;
        if (args[0] != null) {
            k = Integer.parseInt(args[0]);
        }
       while (!StdIn.isEmpty()) {
           rQ.enqueue(StdIn.readString());
       }
       for (int i = 0; i < k; ++i) {
           StdOut.println(rQ.dequeue());
       }
    }

}
