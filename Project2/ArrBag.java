// this file formatted by JFormat - jlbalder@netscape.net
//  STARTER FILE FOR ARRBAG PROJECT #2

import java.io.*;
import java.util.*;

public class ArrBag<T>
{
    final int NOT_FOUND = -1;
    final int INITIAL_CAPACITY = 1;
    private int count; // LOGICAL SIZE
    
    // DEFINE & INITIALIZE REF TO OUR ARRAY OF T OBJECTS
    @SuppressWarnings("unchecked") // SUPRESSION APPLIES TO THE NEXT LINE OF CODE
    T[] theArray = (T[]) new Object[INITIAL_CAPACITY]; // CASTING TO T[] (creates warning we have to suppress)
    
    // DEFAULT C'TOR
    public ArrBag()
    {
        count = 0; // i.e. logical size, actual number of elems in the array
    }
    
    // SPECIFIC INITIAL LENGTH VERSION OF CONSTRUCTOR
    // only the union,intersect,diff call this version of constructor
    @SuppressWarnings("unchecked")
    public ArrBag( int optionalCapacity )
    {
        theArray = (T[]) new Object[optionalCapacity ];
        count = 0; // i.e. logical size, actual number of elems in the array
    }
    
    // C'TOR LOADS FROM FILE Of STRINGS
    @SuppressWarnings("unchecked")
    public ArrBag( String infileName ) throws Exception
    {
        count = 0; // i.e. logical size, actual number of elems in the array
        BufferedReader infile = new BufferedReader( new FileReader(  infileName ) );
        while ( infile.ready() )
			this.add( (T) infile.readLine() );
        infile.close();
    }
    
    // APPENDS NEW ELEM AT END OF LOGICAL ARRAY. UPSIZES FIRST IF NEEDED
    public boolean add( T element )
    {
        // THIS IS AN APPEND TO THE LOGICAL END OF THE ARRAY AT INDEX count
        if (size() == theArray.length) upSize(); // DOUBLES PHYSICAL CAPACITY
			theArray[ count++] = element; // ADD IS THE "setter"
        return true; // success. it was added
    }
    
    // INCR EVERY SUCESSFULL ADD, DECR EVERY SUCESSFUL REMOVE
    public int size()
    {
        return count;
    }
    
    // RETURNS TRUE IF THERE ARE NO ELEMENTS IN THE ARRAY, OTHERWISE FALSE
    public boolean isEmpty()
    {
        return count==0;
    }
    
    public T get( int index )
    {
        if ( index < 0 || index >=size() )
			die("FATAL ERROR IN .get() method. Index to uninitialized element or out of array bounds. Bogus index was: " + 
				 index + "\n" );
        return theArray[index];
    }
    
    // SEARCHES FOR THE KEY. TRUE IF FOUND, OTHERWISE FALSE
    public boolean contains( T key )
    {
        if (key == null) return false;
        for ( int i=0 ; i < size() ; ++i )
			if ( get(i).equals( key ) ) 
				return true;
			
        return false;
    }
    
    void die( String errMsg )
    {
        System.out.println( errMsg );
        System.exit(0);
    }
    
    // --------------------------------------------------------------------------------------------
    // # # # # # # # # # # #   Y O U   W R I T E   T H E S E   M E T H O D S  # # # # # # # # # # #
    // --------------------------------------------------------------------------------------------
    
    // PERFORMS LOGICAL (SHALLOW) REMOVE OF ALL THE ELEMENTS IN THE ARRAY (SIMPLE 1 LINER!)
    public void clear()
    {
        count=0;
    }
    
    // DOUBLE THE SIZE OF OUR ARRAY AND COPY ALL THE ELEMS INTO THE NEW ARRAY
    @SuppressWarnings("unchecked")
    private void upSize()
    {
       T[] tempArray = (T[]) new Object[size()*2];

       for (int i = 0; i < size(); i++) {
            tempArray[i] = theArray[i];
       }

       theArray = tempArray;
    }
    
    public String toString()
    {
        String toString = "";

        for ( int i=0; i < size(); i++)
        {   toString += theArray[i];
            if (i < size()-1) {
                toString += " ";
            }
        }
        return toString;

    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS FROM BOTH BAGS
    // DOES -NOT- MODIFY THIS OR OTHER BAG
    public ArrBag<T> union( ArrBag<T> other )
    {
        // you must declare/define an ArrBag right here just like you did with the linked List Union.
        // However when you define it for the ArrBag version of Union you must use use the alternate
        // constructor which I have added nesr the top of this file
        // You must send in the initial capacity  for this array. The value you send in must be the
        // maximum possible .length that the union of the two sets could possibly be.
        // Ill give you the answer for union so you understand how you would figure it out for the other two.
        // The union of two sets could at MOST have the combined number of elements of both sets.
        // This is the value you will put into the constuctor when you declare your local result set that
        // that you will eventually return.
        
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE UNION
        ArrBag<T> unionSet = new ArrBag<T>( this.size() + other.size() );
        
        for (int i = 0; i < size(); i++) {
            unionSet.add(theArray[i]);
        }

        int end = theArray.length;
        for (int i = 0; i < other.size(); i++) {
            if (this.contains(other.get(i)) == false) {
                unionSet.add(other.get(i));
                end += 1;
            }
        }
        return unionSet;
    } // END UNION
    
    // RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS IN COMMON
    // DOES -NOT- MODIFY THIS OR OTHER
    public ArrBag<T> intersection( ArrBag<T> other )
    {
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE INTERSECT
        ArrBag<T> interSet = new ArrBag<T>(Math.min(this.size(), other.size())); // change the 0 to the right value for intersect
        

        for (int i = 0; i < this.size(); i++) {
            if (other.contains(this.get(i)) == true) {
                interSet.add(theArray[i]);
            }
        }
        return interSet;
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS
    // REMAINING AFTER THIS BAG - OTHER BAG
    // DOES -NOT- MODIFY THIS OR OTHER
    public ArrBag<T> difference( ArrBag<T> other )
    {
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE DIFF
        ArrBag<T> diffSet = new ArrBag<T>(this.size()); // change the 0 to the right value for diff
        
        int count = 0;

        for (int i = 0; i < size(); i++) {
            if (other.contains(this.get(i)) == false) {
                diffSet.add(theArray[i]);
                count += 1;
            }
        }
		return diffSet;
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS
    // CONTAINED IN THE UNION OF THIS AND OTHER - INTERSECTION OF THIS AND OTHER
    // DOES -NOT- MODIFY THE THIS OR OTHER
    public ArrBag<T> xor( ArrBag<T> other )
    {
        return union(other).difference(intersection(other));
    }
}// END ARRBAG CLASS
