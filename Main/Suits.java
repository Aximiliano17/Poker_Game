package Main;

public enum Suits {
	CLUBS('\u2764'), SPADES('\u2663'), HEART('\u2666'), DIAMOND('\u2660');

	private char suit;

	Suits(char suit) {
		this.suit = suit;
	}

	public char getSuit()

	{
		return this.suit;
	}
}
