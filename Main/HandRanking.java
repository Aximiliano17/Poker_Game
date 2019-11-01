package Main;

public enum HandRanking {
	NO_PAIR("No Pair"), ONE_PAIR("One Pair"), TWO_PAIRS("Two Pairs"), THREE_KIND("Three of a Kind"), STRAIGTH(
			"Straigth"), FLUSH("FLush"), FULL_HOUSE("Full House"), FOUR_KIND(
					"Four of a Kind"), STRAIGTH_FLUSH("Straigth FLush"), ROYAL_FLUSH("Royal Flush)");
	private String ranking;
	HandRanking(String ranking)
	{
		this.ranking=ranking;
	}
	public String getRanking()
	{
		return this.ranking;
	}
}