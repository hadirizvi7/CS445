/*
	Deck class
*/

import java.util.*;
import java.io.*;

public class Deck
{
	private int[] deck;
	private final int MAX_DECK_SIZE = 20;
	public Deck( int numCards )
	{	if ( numCards%2 != 0 || numCards > MAX_DECK_SIZE ) 
		{
			System.out.format("\nINVALID DECK SIZE: (" + numCards + "). Must be an small even number <= %d\n", MAX_DECK_SIZE);
			System.exit(0);
		}
		// YOU DO THIS => init deck to be exactly numCards long
		// YOU DO THIS => fill deck with with 0 1 2 3 ... numCards-1 in order
		deck = new int[numCards];

		for (int i = 0; i < deck.length; i++) {
			deck[i] = i;
		}
	}
	
	public String toString()
	{
		String deckStr = "";
		for ( int i=0 ; i < deck.length ; ++i )
			deckStr += deck[i] + " ";
		return deckStr;
	}

	// ONLY WORKS ON DECK WITH EVEN NUMBER OF CARDS
	// MODIFIES THE MEMBER ARRAY DECK
	public void inShuffle()
	{
		int[] newDeck = new int[deck.length];
		int mid = deck.length/2;
		int top = 0;

		for (int i = 0; i < newDeck.length; i++) {
			if (i%2 == 0) {
				newDeck[i] = deck[mid];
				mid += 1;
			}
			else {
				newDeck[i] = deck[top];
				top += 1;
			}
		}

		deck = newDeck;
	}

	// ONLY WORKS ON DECK WITH EVEN NUMBER OF CARDS
	// MODIFIES THE MEMBER ARRAY DECK
	public void outShuffle()
	{
		int[] newDeck = new int[deck.length];
		int mid = deck.length/2;
		int top = 0;

		for (int i = 0; i < newDeck.length; i++) {
			if (i%2 == 0) {
				newDeck[i] = deck[top];
				top += 1;
			}
			else {
				newDeck[i] = deck[mid];
				mid += 1;
			}
		}
		deck = newDeck;
	}
	
	// RETURNS TRUE IF DECK IN ORIGINAL SORTED:  0 1 2 3 ...
	public boolean inSortedOrder()
	{
		if (deck.length == 0 || deck.length == 1) {
			return true;
		}

		for (int i = 1; i < deck.length; i++) {
			if (deck[i-1] > deck[i]) {
				return false;
			}
		}

		return true;
	}
}	// END DECK CLASS