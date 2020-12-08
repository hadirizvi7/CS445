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

	// LOAD LINKED LIST FORM INCOMING FILE
	
	public CDLL_List( String fileName, String insertionMode ) throws Exception
	{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );	
			while ( infile.ready() )
			{	@SuppressWarnings("unchecked") 
				T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
				if ( insertionMode.equals("atFront") )
					insertAtFront( data ); 	
				else if ( insertionMode.equals( "atTail" ) )
					insertAtTail( data ); 
				else
					die( "FATAL ERROR: Unrecognized insertion mode <" + insertionMode + ">. Aborting program" );
			}
			infile.close();
	}	
	
	private void die( String errMsg )
	{
		System.out.println( errMsg );
		System.exit(0);
	}
		
	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################

	// OF COURSE MORE EFFICIENT TO KEEP INTERNAL COUNTER BUT YOU COMPUTE IT DYNAMICALLY WITH A TRAVERSAL LOOP
	@SuppressWarnings("unchecked")
	public int size()
	{	
		CDLL_Node<T> curr = head;
		int counter = 0;

		if (head == null) {
			return counter;
		}

		curr = curr.getNext();
		counter += 1;

		while (curr != head) {
			curr = curr.getNext();
			counter += 1;
		}

		return counter;

	}


	// TACK A NEW NODE ONTO THE FRONT OF THE LIST
	@SuppressWarnings("unchecked")
	public void insertAtFront(T data)
	{
		// BASE CASE WRITTEN FOR YOU
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);
		if (head==null)
		{
			newNode.setNext( newNode );
			newNode.setPrev( newNode );
			head = newNode;
			return;
		}
		// NOT EMPTY. INSERT NEW NODE  BETWEEN HEAD POINTER AND 1ST NODE
		// MAKE HEAD POINT TO NEW NODE

		CDLL_Node<T> tempHead = head;
		CDLL_Node<T> tempTail = head.getPrev();

		tempTail.setNext(newNode);
		newNode.setPrev(tempTail);
		newNode.setNext(tempHead);
		tempHead.setPrev(newNode);

		head = newNode;
	}
	
	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		// BASE CASE WRITTEN FOR YOU
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);
		if (head==null)
		{
			newNode.setNext( newNode );
			newNode.setPrev( newNode );
			head = newNode;
			return;
		}
		
		CDLL_Node<T> tempHead = head;
		CDLL_Node<T> tempTail = head.getPrev();

		tempTail.setNext(newNode);
		newNode.setPrev(tempTail);
		newNode.setNext(tempHead);
		tempHead.setPrev(newNode);
	}
	
	// RETURN TRUE/FALSE THIS LIST CONTAINS A NODE WITH DATA EQUALS KEY
	public boolean contains( T key )
	{
		if (head == null) {
			return false;
		}

		if (head.getData().equals(key)) {
			return true;
		}
		CDLL_Node<T> curr = head.getNext();

		while (curr != head) {
			if (curr.getData().equals(key)) {
				return true;
			}

			curr = curr.getNext();
		}

		return false;
	}

	// RETURN REF TO THE FIRST NODE (SEARCH CLOCKWISE FOLLOWING next) THAT CONTAINS THIS KEY. DO -NOT- RETURN REF TO KEY ISIDE NODE
	// RETURN NULL IF NOT FOUND
	public CDLL_Node<T> search( T key )
	{
		if (head == null) {
			return null;
		}

		if (head.getData() == key) {
			return head;
		}
		CDLL_Node<T> curr = head.getNext();

		while (curr != head) {
			if (curr.getData().equals(key)) {
				return curr;
			}
			curr = curr.getNext();
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
		String returnStr = ""+head.getData()+"<=>";
		CDLL_Node<T> curr = head.getNext();

		while (curr != head) {
			if (curr.getNext().equals(head)) {
				returnStr += curr.getData();
				break;
			} 
			returnStr += curr.getData()+"<=>";
			curr = curr.getNext();
		}

		return returnStr;
	}
	
} // END CDLL_LIST CLASS