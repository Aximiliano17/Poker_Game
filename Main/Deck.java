/**
 * Each Deck object will have 52 Card objects, and the Deck will be shuffled as soon as the Deck is constructed.
 */
package Main;

import java.util.ArrayList;

public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>(52);

	public Deck() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 13; y++) {
				cards.add(new Card(Suits.values()[x],Ranks.values()[y]));
			}
	}
		cards = shuffle(cards);
	}
	private ArrayList<Card> shuffle(ArrayList<Card> deck) {
		ArrayList<Card> shuffledDeck = new ArrayList<Card>(52);
		while (deck.size() > 0) {
			int r = (int) (Math.random() * (deck.size()));// Makes r random
			Card holder = deck.get(r);// gets a random card and assign it to a Card holder
			deck.remove(r);// removes random card from tempDeck
			shuffledDeck.add(holder);// adds card to a new array
		}
		return shuffledDeck;
	}
//deals a card at position 0 from deck, one at a time. Because deck is shuffled randomly, position 0 is also a random card.
	public Card dealCard() {
		return this.cards.remove(0);
	}
	
}
