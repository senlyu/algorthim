import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
   
   private class Node<Item>{
       Item item;
       Node<Item> next;
       Node<Item> ahead;
   }
   
   private Node<Item> front = new Node<Item>();
   private Node<Item> end = new Node<Item>();
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
       if (item == null) {
           throw new java.lang.NullPointerException("You want to add empty element");
       }
       Node<Item> newone = new Node<Item>();
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
       if (item == null) {
           throw new java.lang.NullPointerException("You want to add empty element");
       }
       Node<Item> newone = new Node<Item>();
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
       if (isEmpty()) {
           throw new NoSuchElementException("DeQueue underflow");
       }
       Item item = front.item;
       front = front.next;
       if (front != null) front.ahead = null;
       else end = null;
       size--;
       return item;
   }
   public Item removeLast(){
       // remove and return the item from the end
       if (isEmpty())
           throw new NoSuchElementException("DeQueue underflow");
       Item item = end.item;
       end = end.ahead;
       if (end != null) end.next = null;
       else front = null;
       size--;
       return item;
       
   }
   public Iterator<Item> iterator(){
       // return an iterator over items in order from front to end
       return new ItemIterator<Item>(front);
   }
   private class ItemIterator<Item> implements Iterator<Item>{
       private Node<Item> current;
       
       public ItemIterator(Node<Item> front) {
           current = front;
       }
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
       /*
       System.out.println(dq.removeFirst());
       System.out.println(dq.removeLast());
       System.out.println("remove all");
       */
       for (int i=0;i<10;i++){
           System.out.println(dq.removeLast());
       }
       System.out.println("left");
       for (Integer i : dq){
           System.out.println(i);
       }
   }
}