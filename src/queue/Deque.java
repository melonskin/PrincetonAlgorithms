package queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	
	private int size;
	private Node<Item> first;
	private Node<Item> last;
	
	private class Node<Item>
	{
		Item item;
		Node<Item> next;
		Node<Item> previous;
	}
	public Deque()                           // construct an empty deque
	{
		size = 0;
		first = null;
		last = null;
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return size == 0;
	}
	public int size()                        // return the number of items on the deque
	{
		return size;
	}
	public void addFirst(Item item)          // add the item to the front
	{
		if (item == null) {throw new NullPointerException(); }
		Node<Item> newFirst = new Node<Item>();
		newFirst.item = item;
		newFirst.next = first;
		newFirst.previous = null;
		
		if ( isEmpty() ) {last = newFirst; }
		else { first.previous = newFirst;	}
		first = newFirst;
		size++;
	}
	
	public void addLast(Item item)           // add the item to the end
	{
		if (item == null) {throw new NullPointerException(); }
		Node<Item> newLast = new Node<Item>();
		newLast.item = item;
		newLast.previous = last;
		newLast.next = null;
		
		if( isEmpty() ) {first = newLast; }
		else {	last.next = newLast; }
		last = newLast;
		size++;
	}
	
	public Item removeFirst()                // remove and return the item from the front
	{
		if ( isEmpty() ) { throw new NoSuchElementException("empty deque."); }
		Item removedItem = first.item;
		first = first.next;
		size--;
		if (isEmpty() ) { last = null; }
		else { first.previous = null; }
		return removedItem;	
	}
	public Item removeLast()                 // remove and return the item from the end
	{
		if ( isEmpty() ) { throw new NoSuchElementException("empty deque."); }
		Item removedItem = last.item;
		last = last.previous;
		size--;
		if (isEmpty() ) { first = null; }
		else { last.next = null; }
		return removedItem;	
	}
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new DequeIterator<Item>(first);
	}
	
	private class DequeIterator<Item> implements Iterator<Item> 
	{
		Node<Item> current;
		DequeIterator(Node<Item> first) { current = first;	}
		public boolean hasNext()	{return current != null;	}
		public void remove()	{ throw new UnsupportedOperationException();  }
		
		public Item next()
		{
			if (!hasNext() ) {throw new NoSuchElementException(); }
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	public static void main(String[] args)   // unit testing
	{
		Deque<String> deque = new Deque<String>();
		deque.addFirst("3");
		deque.addFirst("2");
		deque.addFirst("1");
//		deque.addLast("4");
//		deque.addLast("2");
//		deque.addLast("4");
//		deque.addLast("3");
//		deque.removeFirst();
//		deque.removeLast();
//		deque.removeLast();
//		deque.removeLast();
		for (String s : deque)
			System.out.println(s);        
	}
}
