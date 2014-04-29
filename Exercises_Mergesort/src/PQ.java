
public class PQ {
    public int[] array = {0,90, 83, 67, 61, 74, 31, 57, 12, 15, 14,0,0,0};
    public void insert (int index, int a) {
        array[index] = a;
    }
    public void swim(int k) {
//        int l = (int) Math.floor(k/2);
        while (k > 1 && (array[k/2] < array[k])) {
            int temp = array[k/2];
            array[k/2] = array[k];
            array[k] = temp;
            k = k/2;
        }
    }
    
    public static void main(String[] args) {
        PQ pq = new PQ();
        for (int b : pq.array) StdOut.print(b + " ");
        StdOut.println();
        
        pq.insert(11, 66);
        pq.swim(11);
        for (int b : pq.array) StdOut.print(b + " ");
        StdOut.println();
        
        pq.insert(12, 39);
        pq.swim(12);
        for (int b : pq.array) StdOut.print(b + " ");
        StdOut.println();
        
        pq.insert(13, 28);
        pq.swim(13);
        for (int b : pq.array) StdOut.print(b + " ");
        StdOut.println();
    }

}
