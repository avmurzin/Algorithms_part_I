import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Deque data type for algs4 Lab_2.
 * data 18.02.2014
 * execute Deque <N>
 * @author Andrei Murzin
 */
public class Deque<Item> implements Iterable<Item> {
    /**
     * Header and tail poiters.
     */
    private Node first, last;
    /**
     * Size of queue.
     */
    private int N = 0;

    /**
     * Internal class for data container.
     * @author Andrei Murzin
     *
     */
    private class Node {
        /**
         *
         */
        private Item item;
        /**
         *
         */
        private Node next;
        /**
         *
         */
        private Node previous;
    }

    /**
     * Construct an empty deque.
     */
    public Deque() {
        first = new Node();
        last = new Node();
        last.previous = first;
        first.next = last;
        N = 0;
    }

    public boolean isEmpty() {
        return first.next == last;
    }

    /**
     * Return the number of items on the deque.
     * @return size of nodes numbers
     */
    public int size() {
        return N;
    }
    /**
     * Insert the item at the front.
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Null item add attempted");
        }
        Node temp = new Node();
        temp.next = first.next;
        temp.next.previous = temp;
        first.next = temp;
        temp.previous = first;
        temp.item = item;
        temp = null;
        N++;
    }

    /**
     * Insert the item at the end.
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Null item add attempted");
        }
        Node temp = new Node();
        temp.previous = last.previous;
        temp.previous.next = temp;
        temp.next = last;
        last.previous = temp;
        temp.item = item;
        temp = null;
        N++;
    }

    /**
     * Delete and return the item at the front.
     * @return <Item> object
     */
    public Item removeFirst() {
        if (!isEmpty()) {
            Item item = first.next.item;
            Node temp = first;
            first = first.next;
            first.previous = null;
            first.item = null;
            temp.next = null;
            temp = null;
            N--;
            return item;
        } else {
            throw new NoSuchElementException("Deque is empty");
        }

    }

    /**
     * Delete and return the item at the end.
     * @return
     */
    public Item removeLast() {
        if (!isEmpty()) {
                Item item = last.previous.item;
                Node temp = last;
                last = temp.previous;
                last.next = null;
                last.item = null;
                temp.previous = null;
                temp = null;
                N--;
                return item;
        } else {
            throw new NoSuchElementException("Deque is empty");
        }


    }

    /**
     * Return an iterator over items in order from front to end.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Make iteration from <first.next> pointer to <last.previous>.
     * @author murzin
     *
     */
    private class DequeIterator implements Iterator<Item> {
        private Node cursor = first.next;
        /**
         * Override hasNext().
         * @return true if iterator has next
         */
        public boolean hasNext() {
            return !(cursor == last);
        }

        /**
         * 
         */
        public Item next() {
            if (cursor == last) {
                throw new NoSuchElementException("Deque is empty");
            }
            cursor = cursor.next;
            return cursor.previous.item;
        }

        /**
         * Not implemented, return exception.
         */
        public void remove() {
            throw new UnsupportedOperationException("Method not implemented");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Deque<Integer> queue = new Deque<Integer>();
        int n = 0;
        if (args[0] != null) {
            n = Integer.parseInt(args[0]);
        }
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = i;
        }
        if (queue.isEmpty()) {
            StdOut.println("Deque is empty");
        }
        StdOut.println("Deque contents is: ");
        for (Integer d : queue) {
            StdOut.print(d + " ");
        }
        StdOut.println();
        StdOut.println("Run addFirst from 0 to " + (n - 1));
        for (int i = 0; i < n; ++i) {
            queue.addFirst(i);
        }
        StdOut.println("Deque size is: " + queue.size());
        StdOut.println("Deque contents is: ");
        for (Integer d : queue) {
            StdOut.print(d + " ");
        }
        StdOut.println();
        StdOut.println("Run addLast from " + (n - 1) + " to 0");
        for (int i = n - 1; i >= 0; --i) {
            queue.addLast(i);
        }
        StdOut.println("Deque size is: " + queue.size());
        StdOut.println("Deque contents is: ");
        for (Integer d : queue) {
            StdOut.print(d + " ");
        }
        StdOut.println();
        StdOut.println("Deque removeFirst: " + queue.removeFirst());

        StdOut.println("Deque removeLast: " + queue.removeLast());

        StdOut.println("Deque size is: " + queue.size());
        StdOut.println("Deque contents is: ");
        for (Integer d : queue) {
            StdOut.print(d + " ");
        }
        StdOut.println();
        for (int i = queue.size(); i > 0; --i) {
            StdOut.println(i + ": " + queue.removeFirst());
        }
    }

}
