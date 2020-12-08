import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public LinkedList( String fileName, boolean orderedFlag )
	{	head = null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print

	public String toString()
	{
		String toString = "";

		for (Node curr = head; curr != null; curr = curr.next)
		{
			toString += curr.data;		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.next != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################



	public int size() // OF COURSE MORE EFFICIENT to KEEP COUNTER BUT YOU  MUST WRITE LOOP
	{
		Node<T> curr = head;
		int count = 0;

		while (curr != null) {
			curr = curr.next;
			count += 1;
		} 

		return count;
	}

	public boolean empty()
	{
		return size() == 0;
	}

	public boolean contains( T key )
	{
		return search(key) != null;
	}

	public Node<T> search( T key )
	{
		Node<T> curr = head;

		while (curr != null) {
			if (curr.data.equals(key)) {
				return curr;
			}
			curr = curr.next;
		}

		return null;		
	}

	public void insertAtTail(T data)
	{
		if (head == null) {
			insertAtFront(data);
		}

		else {
			Node<T> curr = head;
			while (curr.next != null) {
				curr = curr.next;
			}

			curr.next = new Node<T>(data);
		}
	}

	@SuppressWarnings("unchecked")  //CAST TO COMPARABLE THROWS WARNING
	public void insertInOrder(T data)
	{
		Comparable cdata = (Comparable)data;
		Node<T> curr = head;

		if (head == null || cdata.compareTo(curr.data) <= 0) {
			insertAtFront(data);
			return;
		}
		Node<T> prev = null;

		while (curr != null && cdata.compareTo(curr.data) > 0) {
			prev = curr;
			curr = curr.next;
		}

		Node<T> newNode = new Node<T>(data, null);
		prev.next = newNode;
		newNode.next = curr;
	}

	public boolean remove(T key)
	{
		if (size() == 0 || head == null) {
			return false;
		}

		if (head.data.equals(key)) {
			head = head.next;
			return true;
		}

		Node<T> prev = null;
		Node<T> curr = head;

		while (curr != null && !(curr.data.equals(key))) {
			prev = curr;
			curr = curr.next;
		}
		if (curr == null) {
			return false;
		}
		prev.next = curr.next;
		return true;
	}

	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if (size() == 0 || head == null) {
			return false;
		}

		if (size() == 1) {
			head = null;
			return true;
		}

		Node<T> prev = null;
		Node<T> curr = head;

		while (curr.next != null) {
			prev = curr;
			curr = curr.next;
		}

		prev.next = curr.next;
		return true;
	}

	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if (head == null) {
			return false;
		}
		head = head.next;
		return true;
	}

	public LinkedList<T> union( LinkedList<T> other )
	{
		LinkedList<T> union = new LinkedList<T>();

		Node<T> curr1 = head;

		while (curr1 != null) {
			union.insertAtTail((T)curr1.data);
			curr1 = curr1.next;
		}

		Node<T> curr2 = other.head;

		while (curr2 != null) {
			if (contains((T)curr2.data) == false) {
				union.insertInOrder(curr2.data);
			}
			curr2 = curr2.next;
		}

		return union;
	}
	public LinkedList<T> inter( LinkedList<T> other )
	{
		LinkedList<T> inter = new LinkedList<T>();

		Node<T> curr = head;

		while (curr != null) {
			if (other.contains((T)curr.data)) {
				inter.insertInOrder((T)curr.data);
			}
			curr = curr.next;
		}
		return inter;
	}
	public LinkedList<T> diff( LinkedList<T> other )
	{
		LinkedList<T> diff = new LinkedList<T>();

		Node<T> curr = head;

		while (curr != null) {
			if (!other.contains((T)curr.data)) {
				diff.insertInOrder((T)curr.data);
			} 
			curr = curr.next;
		}

		return diff;
	}

	public LinkedList<T> xor( LinkedList<T> other )
	{
		return union(other).diff(inter(other));

	}

} //END LINKEDLIST CLASS

// A D D   N O D E   C L A S S  D O W N   H E R E 
// R E M O V E  A L L  P U B L I C  &  P R I V A T E (except toString)
// R E M O V E  S E T T E R S  &  G E T T E R S 
// M A K E  T O  S T R I N G  P U B L I C
class Node<T>
{
  T  data;
  Node<T> next;

  Node()
  {
    this( null, null );
  }

  Node(T data)
  {
    this( data, null );
  }

  Node(T data, Node<T> next)
  {
    this.data = data;
    this.next = next;
  }

  public String toString()
  {
	  return ""+data;
  } 
	 
} //EOF