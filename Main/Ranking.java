/**The class checks for the type of hand the player has and a value that serves as a tie breaker.
 *
 */
package Main;

import java.util.ArrayList;

public class Ranking {
	public static void evaluateHand(Player player) {

		if (flushCheck(player.getHand()) & straightCheck(player.getHand())) {
			player.setHandValue(HandRanking.ROYAL_FLUSH, valueStraightFlush(player.getHand()));
		} else if (fourPairsCheck(player.getHand())) {
			player.setHandValue(HandRanking.FOUR_KIND, valueFour(player.getHand()));
		} else if (fullHouseCheck(player.getHand())) {
			player.setHandValue(HandRanking.FULL_HOUSE, valueFullHouse(player.getHand()));
		} else if (flushCheck(player.getHand())) {
			player.setHandValue(HandRanking.FLUSH, valueFlush(player.getHand()));
		} else if (straightCheck(player.getHand())) {
			player.setHandValue(HandRanking.STRAIGTH, valueStraight(player.getHand()));
		} else if (threeOfAKindCheck(player.getHand())) {
			player.setHandValue(HandRanking.THREE_KIND, valueSet(player.getHand()));
		} else if (twoPairsCheck(player.getHand())) {
			player.setHandValue(HandRanking.TWO_PAIRS, valueTwoPairs(player.getHand()));
		} else if (onePairCheck(player.getHand())) {
			player.setHandValue(HandRanking.ONE_PAIR, valueOnePair(player.getHand()));
		} else {
			player.setHandValue(HandRanking.NO_PAIR, valueHighCard(player.getHand()));
		}
	}
	// Methods that check for hand type.
	public static boolean flushCheck(ArrayList<Card> hand) {
		boolean a;
		a = hand.get(0).getSuit() == hand.get(1).getSuit() && hand.get(1).getSuit() == hand.get(2).getSuit()
				&& hand.get(2).getSuit() == hand.get(3).getSuit() && hand.get(3).getSuit() == hand.get(4).getSuit()
				&& hand.get(0).getSuit() == hand.get(4).getSuit();
		return a;
	}

	public static boolean straightCheck(ArrayList<Card> hand) {
		if (hand.get(4).getRank() == 12) {
			boolean a = hand.get(0).getRank() == 2 && hand.get(1).getRank() == 3 && hand.get(2).getRank() == 3
					&& hand.get(3).getRank() == 4;
			boolean b = hand.get(0).getRank() == 8 && hand.get(1).getRank() == 9 && hand.get(2).getRank() == 10
					&& hand.get(3).getRank() == 11;

			return (a || b);
		} else {
			int counter = hand.get(0).getRank();

			for (int x = 0; x < 4; x++) {
				if (hand.get(x).getRank() != counter)
					return (false);

				counter++;
			}
			return (true);
		}
	}

	public static boolean fourPairsCheck(ArrayList<Card> hand) {
		boolean a, b;
		a = hand.get(0).getRank() == hand.get(1).getRank() && hand.get(1).getRank() == hand.get(2).getRank()
				&& hand.get(2).getRank() == hand.get(3).getRank();

		b = hand.get(1).getRank() == hand.get(2).getRank() && hand.get(2).getRank() == hand.get(3).getRank()
				&& hand.get(3).getRank() == hand.get(4).getRank();

		return (a || b);
	}

	public static boolean fullHouseCheck(ArrayList<Card> hand) {
		boolean a, b;

		a = hand.get(0).getRank() == hand.get(1).getRank() && hand.get(1).getRank() == hand.get(2).getRank()
				&& hand.get(3).getRank() == hand.get(4).getRank();

		b = hand.get(0).getRank() == hand.get(1).getRank() && hand.get(2).getRank() == hand.get(3).getRank()
				&& hand.get(3).getRank() == hand.get(4).getRank();

		return (a || b);
	}

	public static boolean threeOfAKindCheck(ArrayList<Card> hand) {
		boolean a, b, c;

		if (fourPairsCheck(hand) || fullHouseCheck(hand))
			return (false); // The hand is better than three of a kind

		a = hand.get(0).getRank() == hand.get(1).getRank() && hand.get(1).getRank() == hand.get(2).getRank();

		b = hand.get(1).getRank() == hand.get(2).getRank() && hand.get(2).getRank() == hand.get(3).getRank();

		c = hand.get(2).getRank() == hand.get(3).getRank() && hand.get(3).getRank() == hand.get(4).getRank();

		return (a || b || c);
	}

	public static boolean twoPairsCheck(ArrayList<Card> hand) {
		boolean a, b, c;

		if (fourPairsCheck(hand) || fullHouseCheck(hand) || threeOfAKindCheck(hand))
			return (false);

		a = hand.get(0).getRank() == hand.get(1).getRank() && hand.get(2).getRank() == hand.get(3).getRank();

		b = hand.get(0).getRank() == hand.get(1).getRank() && hand.get(3).getRank() == hand.get(4).getRank();

		c = hand.get(1).getRank() == hand.get(2).getRank() && hand.get(3).getRank() == hand.get(4).getRank();
		return (a || b || c);
	}

	public static boolean onePairCheck(ArrayList<Card> hand) {
		boolean a, b, c, d;

		if (fourPairsCheck(hand) || fullHouseCheck(hand) || threeOfAKindCheck(hand) || twoPairsCheck(hand))
			return (false);

		a = hand.get(0).getRank() == hand.get(1).getRank();

		b = hand.get(1).getRank() == hand.get(2).getRank();

		c = hand.get(2).getRank() == hand.get(3).getRank();

		d = hand.get(3).getRank() == hand.get(4).getRank();

		return (a || b || c || d);
	}

	// Methods that check for hand value.
	public static int valueStraightFlush(ArrayList<Card> hand) {

		return valueHighCard(hand);
	}

	public static int valueFour(ArrayList<Card> hand) {

		return hand.get(2).getRank();
	}

	public static int valueFullHouse(ArrayList<Card> hand) {
		return hand.get(2).getRank();
	}

	public static int valueFlush(ArrayList<Card> hand) {
		return valueHighCard(hand);
	}

	public static int valueStraight(ArrayList<Card> hand) {
		return valueHighCard(hand);
	}

	public static int valueSet(ArrayList<Card> hand) {
		return hand.get(2).getRank();
	}
	public static int valueTwoPairs(ArrayList<Card> hand) {
		int val = 0;
		if (hand.get(0).getRank() == hand.get(1).getRank() && hand.get(2).getRank() == hand.get(3).getRank())
			val = (14 * 14 * hand.get(2).getRank()) + (14 * hand.get(0).getRank()) + hand.get(4).getRank();
		else if (hand.get(0).getRank() == hand.get(1).getRank() && hand.get(3).getRank() == hand.get(4).getRank())
			val = (14 * 14 * hand.get(3).getRank()) + (14 * hand.get(0).getRank()) + hand.get(2).getRank();
		else
			val = (14 * 14 * hand.get(3).getRank()) + (14 * hand.get(1).getRank()) + hand.get(0).getRank();
		return val;
	}

	public static int valueOnePair(ArrayList<Card> hand) {
		int val = 0;

		if (hand.get(0).getRank() == hand.get(1).getRank())
			val = (14 * 14 * 14 * hand.get(0).getRank()) + hand.get(2).getRank()
					+ (14 * hand.get(3).getRank() + 14 * 14 * hand.get(4).getRank());
		else if (hand.get(1).getRank() == hand.get(2).getRank())
			val = (14 * 14 * 14 * hand.get(1).getRank()) + hand.get(0).getRank()
					+ (14 * hand.get(3).getRank() + 14 * 14 * hand.get(4).getRank());
		else if (hand.get(2).getRank() == hand.get(3).getRank())
			val = (14 * 14 * 14 * hand.get(2).getRank()) + hand.get(0).getRank()
					+ (14 * hand.get(1).getRank() + 14 * 14 * hand.get(4).getRank());
		else if (hand.get(3).getRank() == hand.get(4).getRank())
			val = (14 * 14 * 14 * hand.get(3).getRank()) + hand.get(0).getRank()
					+ (14 * hand.get(1).getRank() + 14 * 14 * hand.get(2).getRank());

		return val;
	}

	public static int valueHighCard(ArrayList<Card> hand) {
		int val;
		val = hand.get(0).getRank() + 14 * hand.get(1).getRank() + 14 * 14 * hand.get(2).getRank()
				+ 14 * 14 * 14 * hand.get(3).getRank() + 14 * 14 * 14 * 14 * hand.get(4).getRank();

		return val;
	}

}