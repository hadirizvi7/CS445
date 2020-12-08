import java.io.*;
import java.util.*;

public class CDLL_List<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	
	public CDLL_List()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public CDLL_List( String fileName, boolean orderedFlag )
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



	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################

	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(T data)
	{
		CDLL_Node<T> newNode = new CDLL_Node<T>( data,null,null);
		if (head==null)
		{
			newNode.next = newNode;
			newNode.prev = newNode;
			head = newNode;
			return;
		}
		newNode.next = head;
		newNode.prev = head.prev;

		head.prev.next = newNode;
		head.prev = newNode;

		head = newNode;
	}
	public void insertAtTail(T data)
	{
		insertAtFront(data);
		head = head.next;

	}
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{	
		if (head == null) {
			return false;
		}

		if (head.next.equals(head)) {
			head = null;
			return true;
		}

		CDLL_Node<T> newTail = head.prev.prev;
		newTail.next = head;
		head.prev = newTail;
		return true;
	}

	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{	
		
		if (head == null) {
			return false;
		}

		if (head.next.equals(head)) {
			head = null;
			return true;
		}

		CDLL_Node<T> newHead = head.next;
		CDLL_Node<T> newTail = head.prev;

		newTail.next = newHead;
		newHead.prev = newTail;
		head = newHead;
		return true;
		
	}

	public String toString()
	{
		if (head == null) {
			return "";
		}
		String returnStr = ""+head.data+" ";
		CDLL_Node<T> curr = head.next;

		while (curr != head) {
			if (curr.next.equals(head)) {
				returnStr += curr.data;
				break;
			} 
			returnStr += curr.data+" ";
			curr = curr.next;
		}

		return returnStr;
	}

	public int size() // OF COURSE MORE EFFICIENT to KEEP COUNTER BUT YOU  MUST WRITE LOOP
	{
		CDLL_Node<T> curr = head;
		int counter = 0;

		if (head == null) {
			return counter;
		}

		curr = curr.next;
		counter += 1;

		while (curr != head) {
			curr = curr.next;
			counter += 1;
		}

		return counter;		
	}

	public boolean empty()
	{
		return size() == 0;
	}

	public boolean contains( T key )
	{
		return search(key) != null;
	}

	public CDLL_Node<T> search( T key )
	{
		if (head == null) {
			return null;
		}

		if (head.data == key) {
			return head;
		}
		CDLL_Node<T> curr = head.next;

		while (curr != head) {
			if (curr.data.equals(key)) {
				return curr;
			}
			curr = curr.next;
		}

		return null;
	}

	@SuppressWarnings("unchecked")  //CAST TO COMPARABLE THROWS WARNING
	public void insertInOrder(T data)
	{
		Comparable cdata = (Comparable)data;
		if (head == null || cdata.compareTo(head.data) < 0) {
			insertAtFront(data);
			return;
		}
		CDLL_Node<T> curr = head.next;
		CDLL_Node<T> prev = head;
		while (curr != head && cdata.compareTo(curr.data) > 0) {
			prev = curr;
			curr = curr.next;
		}

		CDLL_Node<T> newNode = new CDLL_Node<T>(data, prev, curr);
		curr.prev = newNode;
		prev.next = newNode;
	}

	public boolean remove(T key)
	{
		if (head == null) {
			return false;
		}

		if (head.data.equals(key)) {
			removeAtFront();
			return true;
		}
		
		CDLL_Node<T> previous = head;
		CDLL_Node<T> curr = head.next;

		while (curr != head) {
			if (curr.data.equals(key)) {
				previous.next = curr.next;
				curr.prev = previous.prev;
				return true;
			}

			previous = curr;
			curr = curr.next;
		}

		return false;

	}


	public CDLL_List<T> union( CDLL_List<T> other )
	{
		CDLL_List<T> union = new CDLL_List<T>();

		CDLL_Node<T> curr1 = head;
		union.insertAtTail((T)curr1.data);
		curr1 = curr1.next;

		while (curr1 != head) {
			union.insertAtTail((T)curr1.data);
			curr1 = curr1.next;
		}

		CDLL_Node<T> curr2 = other.head;
		if (contains((T)curr2.data) == false) {
			union.insertInOrder(curr2.data);
		}
		curr2 = curr2.next;

		while (curr2 != other.head) {
			if (contains((T)curr2.data) == false) {
				union.insertInOrder(curr2.data);
				curr2 = curr2.next;
			}
			else {
				curr2 = curr2.next;
			}
		}

		return union;
	}
	public CDLL_List<T> inter( CDLL_List<T> other )
	{
		CDLL_List<T> inter = new CDLL_List<T>();

		CDLL_Node<T> curr = head.next;

		while (curr != head) {
			if (other.contains((T)curr.data)) {
				inter.insertInOrder((T)curr.data);
			}
			curr = curr.next;
		}

		if (other.contains((T)head.data)) {
			inter.insertInOrder((T)head.data);
		}

		return inter;
	}
	public CDLL_List<T> diff( CDLL_List<T> other )
	{
		CDLL_List<T> diff = new CDLL_List<T>();

		CDLL_Node<T> curr = head.next;

		if (!other.contains((T)head.data)) {
			diff.insertInOrder((T)head.data);
		}

		while (curr != head) {
			if (!other.contains((T)curr.data)) {
				diff.insertInOrder((T)curr.data);
			}
			curr = curr.next;
		}

		return diff;
	}
	public CDLL_List<T> xor( CDLL_List<T> other )
	{
		return union(other).diff(inter(other)); 
	}

} //END LINKEDLIST CLASS

// A D D   C D L L  N O D E   C L A S S  D O W N   H E R E 
// R E M O V E  A L L  P U B L I C  &  P R I V A T E (except toString)
// R E M O V E  S E T T E R S  &  G E T T E R S 
// M A K E  T O  S T R I N G  P U B L I C
class CDLL_Node<T>
{
  T data;
  CDLL_Node<T> prev, next; // EACH CDLL_Node PTS TO ITS PREV  & NEXT

  CDLL_Node()
  {
    this( null, null, null );  // 3 FIELDS TO INIT
  }

  CDLL_Node(T data)
  {
    this( data, null, null);
  }

  CDLL_Node(T data, CDLL_Node<T> prev, CDLL_Node<T> next)
  {
    this.data = data;
    this.prev = prev;
    this.next = next;
  }

  T getData()
  {
    return data;
  }

  CDLL_Node<T> getPrev()
  {
    return prev;
  }
  CDLL_Node<T> getNext()
  {
    return next;
  }

  void setData(T data)
  {
     this.data = data;
  }

  void setNext(CDLL_Node<T> next)
  {
    this.next = next;
  }
  
  void setPrev(CDLL_Node<T> prev)
  {
    this.prev = prev;
  }
 
  public String toString()
  {
	  return ""+getData();
  } 
	 
} //EOF