/*
	Deck class (for TopCardPlacer class of project #1
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Deck
{
	private int[] deck;
	private final int MAX_DECK_SIZE = 30;
	public Deck( int numCards )
	{	if ( numCards%2 != 0 || numCards > MAX_DECK_SIZE ) 
		{
			System.out.format("\nINVALID DECK SIZE: (" + numCards + "). Must be an small even number <= %d\n", MAX_DECK_SIZE);
			System.exit(0);
		}
		deck = new int[numCards];
		for ( int i=0 ; i<numCards ; i++ ) deck[i] = i;
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
	
	public String toBitString( int n ) 
	{
		if (n == 0) {
			return "";
		}

		int length = (int)(Math.log(n)/Math.log(2)) + 1;
		int[] bitArray = new int[length];

		for (int i = 0; i < bitArray.length; i++) {
			bitArray[i] = 0;
		}

		while (n > 0) {
			for (int i = 0; i < bitArray.length; i++) {
				int power = (int)Math.pow(2,(bitArray.length-1) - i);
				if (power <= n) {
					n -= power;
					bitArray[i] = 1;
					break;
				}
			}
		}
		String bitString = "";
		for (int i = 0; i < bitArray.length; i++) {
			bitString += bitArray[i] + "";
		}
		return bitString;
	}
	
}	// END DECK CLASS