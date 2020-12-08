import java.io.*;
import java.util.*;

public class MyHashSet implements HS_Interface
{	private int numBuckets;
	private Node[] bucketArray; 
	private int size; // total # keys stored in set right now
	
	// THIS IS A TYPICAL AVERAGE BUCKET SIZE. IF YOU GET A LOT BIGGER THEN YOU ARE MOVING AWAY FROM (1) 
	private final int MAX_ACCEPTABLE_AVE_BUCKET_SIZE = 10;  
	
	public MyHashSet( int numBuckets )
	{	size=0;
		this.numBuckets = numBuckets;
		bucketArray = new Node[numBuckets]; // array of linked lists
		System.out.format("IN CONSTRUCTOR: INITIAL TABLE LENGTH=%d RESIZE WILL OCCUR EVERY TIME AVE BUCKET LENGTH EXCEEDS %d\n", numBuckets, MAX_ACCEPTABLE_AVE_BUCKET_SIZE );
	}

	public boolean isEmpty()
	{
		return (size == 0);
	}

	public int size()
	{
		return size;
	}

	public void clear()
	{
		this.size = 0;
		this.bucketArray = new Node[this.numBuckets];
	}

	public int hashOf(String key)
	{
		int hash = 4111;
		for (int i = 0; i < key.length(); i++)
		{
			hash += (hash << 3) + (int)(key.charAt(i));
		}
		return Math.abs(hash%numBuckets);
	}

	public boolean contains(String key)
	{
		int bucketIndex = hashOf(key);
		Node curr = bucketArray[bucketIndex];

		while (curr != null)
		{
			if (curr.data.equals(key))
			{
				return true;
			}
			curr = curr.next;
		}
		return false;
	}
 

	public boolean add( String key )
	{	//ADD THE KEY TO THE TABLE
		if (contains(key))
		{
			return false;
		}

		int bucketIndex = hashOf(key);
		Node currHead = bucketArray[bucketIndex];
		Node newHead = new Node(key, currHead);
		bucketArray[bucketIndex] = newHead;
		size += 1;
		if ( size > MAX_ACCEPTABLE_AVE_BUCKET_SIZE * this.numBuckets)
			upSize_ReHash_AllKeys(); // DOUBLE THE ARRAY .length THEN REHASH ALL KEYS
		return true;
	}

	public boolean remove(String key)
	{
		if (isEmpty()) return false;
		int bucketIndex = hashOf(key);
		Node curr = bucketArray[bucketIndex];
		Node prev = null;
		while (curr != null)
		{
			if (curr.data.equals(key))
			{
				if (prev == null)
				{
					bucketArray[bucketIndex] = curr.next;
				}
				else
				{
					prev.next = curr.next;
				}

				size -= 1;
				return true;
			}
			prev = curr;
			curr = curr.next;
		}
		return false;
	}
	
	private void upSize_ReHash_AllKeys()
	{	System.out.format("KEYS HASHED=%10d UPSIZING TABLE FROM %8d to %8d REHASHING ALL KEYS\n",
						   size, bucketArray.length, bucketArray.length*2  );
		Node[] biggerArray = new Node[ bucketArray.length * 2 ];
		this.numBuckets = biggerArray.length; //<== DONT FORGET TO DO THIS AS SOON AS YOU UPSIZE
		/*
		FOR EACH BUCKET 
			FOR EACH NODE IN THE LIST 
				HASH THAT KEY BACK INTO THE BIGGER TABLE
				BE SURE YOU ARE USING THE BIGGER .LENGTH AS MODULUS !!
		*/

		for (int i = 0; i < bucketArray.length; i++)
		{
			Node curr = bucketArray[i];

			while (curr != null)
			{
				int bucketIndex = hashOf(curr.data);
				Node temp = new Node(curr.data, biggerArray[bucketIndex]);
				biggerArray[bucketIndex] = temp;
				curr = curr.next;
			}
		}

		this.bucketArray = biggerArray;
	} // END UPSIZE & REHASH
			

} //END MyHashSet CLASS

class Node
{	String data;
	Node next;  
	public Node ( String data, Node next )
	{ 	this.data = data;
		this.next = next;
	}
} 


