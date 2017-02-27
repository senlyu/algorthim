import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
    
    private int size = 0;
    private int maxsize = 4;
    private Item[] a;
    private Item[] newa;
    public RandomizedQueue(){
        // construct an empty randomized queue
        a =(Item[]) new Object[maxsize];
    }
    private RandomizedQueue(int n){
        // construct an empty randomized queue
        maxsize = n;
        a =(Item[]) new Object[maxsize];
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        // return the number of items on the queue
        return size;
    }
    public void enqueue(Item item){
        // add the item
        if (maxsize <= 2 * size){
            newa = (Item[]) new Object[maxsize * 2];
            for (int i = 1; i <= size; i++) {
                newa[i] = a[i];
            }
            a = newa;
            maxsize = maxsize * 2;
        }
        size++;
        a[size] = item;
        
    }
    public Item dequeue(){
        // remove and return a random item
        if (maxsize >= 4 * size){
            //newa =(Item[]) new Object[maxsize / 2];
            for (int i = 1; i <= size; i++) {
                newa[i] = a[i];
            }
            a = newa;
            maxsize = maxsize / 2;
        }
        
        int randomnum = StdRandom.uniform(1, size + 1);
        Item item = a[randomnum];
        a[randomnum] = a[size];
        a[size] = null;
        size--;
        return item;
    }
    public Item sample(){
        // return (but do not remove) a random item
        int randomnum = StdRandom.uniform(1, size + 1);
        Item item = a[randomnum];
        return item;
    }
    public Iterator<Item> iterator(){
        // return an independent iterator over items in random order
        return new ItemIterator();
    }
    private class ItemIterator implements Iterator<Item>{
        private int countn = size;
        @Override
        public boolean hasNext(){
            return countn != 0;
        }
        @Override
        public Item next(){
            if (!hasNext()){
                throw new java.util.NoSuchElementException();
            }
            int randomnum = StdRandom.uniform(1, countn + 1);
            Item item = a[randomnum];
            a[randomnum] = a[countn];
            a[countn] = item;
            countn--;
            return item;
        }
    }
    public static void main(String[] args){
        // unit testing (optional)
        RandomizedQueue<String> rq = new RandomizedQueue<String>(3);
        rq.isEmpty();
        
        rq.enqueue("1");
        System.out.println(rq.sample());
        rq.enqueue("2");
        
        rq.enqueue("3");
        rq.enqueue("4");
        rq.enqueue("5");
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        
        System.out.println(rq.dequeue());
        
        for (String i: rq){
            System.out.println("total " + i);
        }
        
    }
}