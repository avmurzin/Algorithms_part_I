import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * Randomize queue data type for algs4 Lab_2.
 * data 18.02.2014
 * execute Deque <N>
 * @author Andrei Murzin
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /**
     * The main data array.
     */
    private Item[] itemArray;
    /**
     * Size of main data array.
     */
    private int N;

    /**
     * Construct an empty randomized queue.
     */
    public RandomizedQueue() {
        itemArray = (Item[]) new Object[1];
        N = 0;
    }

    /**
     * Resize main array if places missing.
     * @param max new array size
     */
    private void resize(final int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = itemArray[i];
        }
        itemArray = temp;
        temp = null;
    }

    /**
     * Is the queue empty?
     * @return true if queue is empty.
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Return the number of items on the queue.
     * @return integer
     */
    public int size() {
        return N;
    }

    /**
     * Add the item.
     * @param item (universal data type)
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Null item add attempted");
        }
        if (N == itemArray.length) {
            resize(2 * itemArray.length);
        }
        itemArray[N] = item;
        ++N;
    }

    /**
     * Delete and return a random item.
     * @return object of Item type
     */
    public Item dequeue() {
        Item temp;
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        int i = StdRandom.uniform(0, N);
        temp = itemArray[i];
        itemArray[i] = itemArray[N - 1];
        itemArray[N - 1] = null;
        --N;
        if (N > 0 && N == itemArray.length/4) {
            resize(itemArray.length / 2);
        }
          return temp;
    }

    /**
     * Return (but do not delete) a random item.
     * @return object of Item type.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return itemArray[StdRandom.uniform(0, N)];
    }

    /**
     * Return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Subclass for iteration support.
     * @author murzin
     *
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] iteratorArrayItems = (Item[]) new Object[N];
        private int i = N;
        public RandomizedQueueIterator() {
            for (int j = 0; j < N; ++j) {
                iteratorArrayItems[j] = itemArray[j];
            }
            StdRandom.shuffle(iteratorArrayItems);
        }
        /**
         * Override hasNext().
         * @return true if iterator has next
         */
        public boolean hasNext() {
            return i > 0;
        }

        /**
         * Next iteration.
         */
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Deque is empty");
            }
            return iteratorArrayItems[--i];
        }

        /**
         * Not implemented, return exception.
         */
        public void remove() {
            throw new UnsupportedOperationException("Method not implemented");
        }
    }
    /**
     * Unit testing.
     * @param args (command line string[] arguments)
     */
    public static void main(String[] args) {
        int i;
        i = Integer.parseInt(args[0]);
        RandomizedQueue<Integer> rQ = new RandomizedQueue<Integer>();
        if (rQ.isEmpty()) {
            StdOut.println("Queue is empty");
            StdOut.println("Queue size is:" + rQ.size());
        }
        for (int j = 0; j < i; ++j) {
            rQ.enqueue(j);
        }
        if (!rQ.isEmpty()) {
            StdOut.println("Queue is not empty");
            StdOut.println("Queue size is:" + rQ.size());
        }
        StdOut.println("Randomly deleted element is:" + rQ.dequeue());
        StdOut.println("Queue size is:" + rQ.size());
        StdOut.println("Randomly sampled element is:" + rQ.sample());
        StdOut.println("Queue size is:" + rQ.size());
        for (Integer d : rQ) {
            StdOut.print(d + " ");
        }
        StdOut.println();
        for (Integer d : rQ) {
            StdOut.print(d + " ");
        }
        StdOut.println();
        
        while (true) {
            StdOut.println("Randomly deleted element is:" + rQ.dequeue());
        }

    }


}
