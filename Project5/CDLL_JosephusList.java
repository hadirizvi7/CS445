import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method 
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	
	public CDLL_JosephusList( String infileName ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );	
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked") 
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data ); 
		}
		infile.close();
	}
	

	
	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################
	
	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		CDLL_Node<T> newNode = new CDLL_Node<T>(data, null, null);
		if (head == null) {
			newNode.next = newNode;
			newNode.prev = newNode;
			head = newNode;
			return;
		}

		CDLL_Node<T> tempHead = head;
		CDLL_Node<T> tempTail = head.prev;

		tempTail.next = newNode;
		newNode.prev = tempTail;
		newNode.next = tempHead;
		tempHead.prev = newNode;

		head = newNode.next;

	}

	
	public int size()
	{	
		if (head == null) {
			return 0;
		}
		if (head.next == head) {
			return 1;
		}
		int counter = 1;
		CDLL_Node<T> curr = head.next;

		while (curr != head) {
			curr = curr.next;
			counter += 1;
		}

		return counter;
	}
	
	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
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
	
	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		if (head == null) {
			return "";
		}
		String returnStr = ""+head.data;
		CDLL_Node<T> curr = head.next;

		while (curr != head) {
			returnStr += "<=>"+curr.data;
			curr = curr.next;
		}

		return returnStr;
		
	}
	
	void removeNode( CDLL_Node<T> deadNode )
	{
		if (head == null) {
			return;
		}

		if (head.data.equals(deadNode)) {
			head = null;
			return;
		}

		CDLL_Node<T> curr = head.next;
		CDLL_Node<T> previous = head;

		while (!curr.equals(deadNode)) {
			previous = curr;
			curr = curr.next;
		}

		CDLL_Node<T> temp = curr.next;
		previous.next = temp;
		temp.prev = previous;

	}
	
	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		if (size() <= 1 ) return;
		CDLL_Node<T> curr = search( first2Bdeleted );
		if ( curr==null ) return;
		
		// OK THERE ARE AT LEAST 2 NODES AND CURR IS SITING ON first2Bdeleted
		do
		{
			CDLL_Node<T> deadNode = curr;
			T deadName = deadNode.data;
			
			System.out.println( "stopping on " + curr.data + "to delete "+curr.data);
			
			// BEFORE DOING ACTUAL DELETE DO THESE TWO THINGS 

			// 1: you gotta move that curr off of the deadNode. 
			//    if skipCount poitive do curr=curr.next  esle do  curr=curr.prev
			// 2: check to see if HEAD is pointing to the deadnode. 
			//    If so make head=curr

			if (skipCount > 0) {
				curr = curr.next;
			}
			else {
				curr = curr.prev;
			}

			if (head == deadNode) {
				head = curr;
			} 
			
			
			// NOW DELETE THE DEADNODE
			removeNode(deadNode);

			System.out.println("deleted. list now: "+ toString() );
			
			// if the list size has reached 1 return YOU ARE DONE.  RETURN RIGHT HERE
			if (size() == 1) {
				return;
			}
	
			System.out.println("resuming at " + curr.data+", skipping "+"curr.data + "+(skipCount-1)+" nodes CLOCKWISE/COUNTERWISE after");
			
			// write loop that advances curr pointer skipCount-1 times (be sure of CLOCKWISE or COUNTER)
			for (int i = 0; i < Math.abs(skipCount); i++) {
				if (skipCount > 0) {
					curr = curr.next;
				}
				else {
					curr = curr.prev;
				}
			}

			// OPTIONAL HERE FOR DEBUGGING TO MAKE IT STOP AT BOTTOM OF LOOP
			// Scanner kbd = new Scanner( Systen.in ); String junk = kbd.nextLine();   
			
		}
		while (size() > 1 );  // ACTUALLY COULD BE WHILE (TRUE) SINCE WE RETURN AS SOON AS SIZE READES 1

	}
	
} // END CDLL_LIST CLASS
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
    this.next = next;
    this.prev = prev;
  }

 
  public String toString()
  {
	  return ""+this.data;
  } 
	 
} //EOF