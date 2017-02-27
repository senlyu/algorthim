import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
   
   private class Node{
       Item item;
       Node next;
       Node ahead;
   }
   
   private Node front = new Node();
   private Node end = new Node();
   private int size = 0;
   
   public Deque(){
       // construct an empty deque
       
   }
   public boolean isEmpty(){
       return size == 0;
   }
   
   public int size(){
       // return the number of items on the deque
       return size;
   }
   public void addFirst(Item item){
       // add the item to the front
       Node newone = new Node();
       newone.item = item;
       if (!isEmpty()){
           newone.next = front;
           front.ahead = newone;
       } else {
           end = newone;
       }
       front = newone;
       size++;
   }
   public void addLast(Item item){
       // add the item to the end
       Node newone = new Node();
       newone.item = item;
       if (!isEmpty()){
           newone.ahead = end;
           end.next = newone;
       } else {
           front = newone;
       }
       end = newone;
       size++;

   }
   public Item removeFirst(){
       // remove and return the item from the front
       if (isEmpty())
           throw new NoSuchElementException("DeQueue underflow");
       Item item = front.item;
       front = front.next;
       front.ahead = null;
       return item;
   }
   public Item removeLast(){
       // remove and return the item from the end
       if (isEmpty())
           throw new NoSuchElementException("DeQueue underflow");
       Item item = end.item;
       end = end.ahead;
       end.next = null;
       return item;
       
   }
   public Iterator<Item> iterator(){
       // return an iterator over items in order from front to end
       return new ItemIterator();
   }
   private class ItemIterator implements Iterator<Item>{
       private Node current = front;
       @Override
       public boolean hasNext(){
           return current != null;
       }
       @Override
       public Item next(){
           if (!hasNext()){
               throw new java.util.NoSuchElementException();
           }
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
   
   public static void main(String[] args){
       // unit testing (optional)
       Deque<Integer> dq = new Deque<Integer>();
       for (int i=0;i<10;i++){
           dq.addLast(i);
       }
       System.out.println(dq.removeFirst());
       System.out.println(dq.removeLast());
       for (Integer i : dq){
           System.out.println(i);
       }
       
       
   }
}