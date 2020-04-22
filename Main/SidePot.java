/**
 * The purpose of this class is to keep track of everyones sidePot, it will get more complicated as I keep adding features.
 * If player C goes all in with 200, and then player D raises by 500, player C can no longer bet and his turn is skipped, and
 * if he wins, he will only win 200 for each player that didn't fold. The plan for this class is to keep track of everyones
 * sidePot, so i know how to distribute the winning properly. Will update this feature in the future.
 */
package Main;

import java.util.HashMap;

public class SidePot {
private int maxBid;//the newest amount that a player has raised to.
private HashMap<Player,Integer> currentBets=new HashMap<>();//Keeps track of players with their currentBet

	public int getCurrentBet(Player player)
	{
		if(!currentBets.containsKey(player))
		{
			currentBets.put(player,0);
		}
		return currentBets.get(player);
	}
	public void setCurrentBet(Player player,int amount)
	{
		int total=currentBets.get(player);
		total+=amount;
		currentBets.put(player,total);
	}
	public int getMaxBid()
	{
		return maxBid;
	}
	public void increaseBid(int a)//When a player raises, increases the amount of maxBid
	{
		maxBid+=a;
	}
	//Calculates the total bet amount from all players and returns it.
	public int getTotal()
	{
		int total=0;
		for(Player p:currentBets.keySet())
		{
			total+=currentBets.get(p);
			currentBets.put(p,0);
		}
		return total;
	}
	
}
