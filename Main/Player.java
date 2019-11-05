package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Player {
	private String name;
	private int money;
	private int currentBet;
	//Stores the type of hand they have such as flush or pair, and a tie breaker integer.
	private Map<HandRanking,Integer> handValue=new HashMap<>();
	private boolean turn = false;//This allows me to keep track if the player has already gone thrugh its turn, or if someone raised and needs a turn again.
	private ArrayList<Card> hand = new ArrayList<Card>(5);
	
	// Constructors. For simplicity purpose, every time a player is added, they have
	// a default amount of money.
	public Player(String initialName) {
		this(initialName, 10000);
	}

	public Player(String initialName, int initialMoney) {
		this.name = initialName;
		this.money = initialMoney;
	}
	//___________GETTERS_______________
	public String getName() {
		return this.name;
	}

	public int getMoney() {
		return this.money;
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}

	public Map<HandRanking,Integer> getHandValue() {
		return this.handValue;
	}
	public HandRanking getHandRank()
	{
		for(HandRanking r:this.handValue.keySet())
		{
			return r;
		}
		return null;
	}
//used to keep track how much a player has bet, in case someone raises
	public int getCurrentBet()
	{
		return this.currentBet;
	}

	public boolean getTurn() {
		return this.turn;
	}
	// ________________SETTERS________________________
	public void setCurrentBet(int currentBet)
	{
		this.currentBet=currentBet;
	}
	public void setHand(Card temp) {
		if (hand.size() < 5) {
			hand.add(temp); // adds Cards to the arraylist
		}
		sortHand(getHand());
		
	}

	public void setHandValue(HandRanking k, int v) // after the hand is ranked, each player gets a handValue.
	{
		this.handValue.put(k,v);
	}

	public void setTurn(boolean newTurn) {
		this.turn = newTurn;
	}
	//___________________OTHER METHODS_____________
	public int substractMoney(int money)
	{
		this.money=this.money-money;
		return money;
	}
	public void addMoney(int money)
	{
		this.money=this.money+money;
	}
	public ArrayList<Card> sortHand(ArrayList<Card> hand)
	{
		Collections.sort(hand);
		this.hand=hand;
		return this.hand;
	}
	public void removeCard(Card card)
	{
		this.hand.remove(card);
	}
	public void clearHand()
	{
		hand.clear();
		handValue.clear();
	}
	public void clearTurn()
	{
		this.turn=false;
	}

}
