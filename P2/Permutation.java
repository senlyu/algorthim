import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class Permutation{
    
    private int n;
    
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int n = 0;
        In in = new In(args[2]);      // input file
        while (!in.isEmpty()) {
            String inputString = in.readString();
            n++;
            if (rq.size() < k){
                rq.enqueue(inputString);
            } else {
                int randomnum = StdRandom.uniform(1, n + 2);
                if (randomnum <= k){
                    rq.dequeue();
                    rq.enqueue(inputString);
                }
            }
        }
        for (String i: rq) {
            StdOut.println(i);
        }
    }
}