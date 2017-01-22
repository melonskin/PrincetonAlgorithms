package queue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
// StdRandom.uniform(n)

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] a;
	private int N;
	public RandomizedQueue()                 // construct an empty randomized queue
	   {
		   a = (Item[]) new Object[2];
		   N = 0;
	   }
	   public boolean isEmpty()                 // is the queue empty?
	   {
		   return (N == 0) ;
	   }
	   public int size()                        // return the number of items on the queue
	   {
		   return N;
	   }
	   private void resize(int capacity)
	   {
		   Item[] aTemp = (Item[]) new Object[capacity];
		   for (int i = 0; i < N; i++)
		   {
			   aTemp[i] = a[i];
		   }
		   a = aTemp;
	   }
	   public void enqueue(Item item)           // add the item
	   {
		   if (item == null) {throw new NullPointerException(); }
		   if (N == a.length) resize(2*a.length);
		   a[N++] = item;
	   }
	   
	    public Item dequeue() {
	        if (isEmpty()) {
	            throw new NoSuchElementException();
	        }
	        int randomIndex = StdRandom.uniform(N);
	        Item item = a[randomIndex];
	        a[randomIndex] = a[N-1];
	        a[N-1] = null;
	        N--;
	        if ( N > 0 && N == a.length/4 ) {
	            resize(a.length/2);
	        }
	        return item;
	    }	   
//	   public Item dequeue()                    // remove and return a random item
//	   {
//		   if ( isEmpty() ) {throw new NoSuchElementException("empty queue"); }
//		   int randomIndex = StdRandom.uniform(N);
//		   Item[] aTemp = (Item[]) new Object[N-1];
//		   Item aRemoved = a[randomIndex];
//		   if (randomIndex == 0) {
//			   for (int i = 0; i < N - 1; i++)
//			   {
//				   aTemp[i] = a[i+1];
//			   }
//		   }
//			   else if (randomIndex == N)
//			   {
//				   for (int i = 0; i < N - 1; i++)
//				   {
//					   aTemp[i] = a[i];
//				   }
//			   }
//			   else {
//				   for (int i = 0; i < randomIndex; i++)
//				   {
//					   aTemp[i] = a[i];
//				   }
//				   for (int i = randomIndex; i < N - 1; i++)
//				   {
//					   aTemp[i] = a[i+1];
//				   }
//			   }
//		   a = aTemp;	
//		   N--;
//		   return aRemoved;
//	   }
	   
	   public Item sample()                     // return (but do not remove) a random item
	   {
		   if ( isEmpty() ) {throw new NoSuchElementException("empty queue"); }
		   int randomIndex = StdRandom.uniform(N);
		   return a[randomIndex];
	   }
	   
	   public Iterator<Item> iterator()         // return an independent iterator over items in random order
	   {
		   return new RandomQueueIterator();
	   }
	   private class RandomQueueIterator implements Iterator<Item>
	   {
		   private Item[] r;
		   private int iN;
		   public RandomQueueIterator()
		   {
			   r = (Item[]) new Object[N];
	           for (int i = 0; i < N; i++) {
	                r[i] = a[i];
	            }
	           StdRandom.shuffle(r);
	            iN = 0;
		   }
		   
		   public boolean hasNext() {
	            return iN < N;
	        }

	        public void remove() {
	            throw new UnsupportedOperationException();
	        }

	        public Item next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            return r[iN++];
	        }
	        
	   }
	   public static void main(String[] args)
	   {
		   // unit testing
		   RandomizedQueue<String> randq = new RandomizedQueue<String>();
		   randq.enqueue("1");
		   randq.enqueue("2");
		   randq.enqueue("3");
		   randq.enqueue("4");
		   randq.enqueue("5");
		   for (String s : randq)
				System.out.println(s); 
		   randq.dequeue();
		   for (String s : randq)
				System.out.println(s); 
	   }
	}