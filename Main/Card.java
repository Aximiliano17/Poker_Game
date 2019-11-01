/**Each Card will be its own object. Each Card will contain 2 integers that will determine the Rank and Suit of the Card.
 */
package Main;
import java.lang.Comparable;
public class Card implements Comparable<Card> {
	private Suits suit;
	private Ranks rank;

	Card(Suits nsuit, Ranks nrank) {
		this.rank = nrank;
		this.suit = nsuit;
	}

	// This method overrides the object method toString() to display the rank and suit of the card.
	public String toString() {
		return rank.getRank() + suit.getSuit();
	}
	//method used to sort an array of Cards by rank
	public int compareTo(Card card) 
    { 
        return this.rank.ordinal() - card.rank.ordinal(); 
    }
    
	// Getters
	public int getRank() {
		return rank.ordinal();
	}

	public char getSuit() {
		return suit.getSuit();
	}
}
