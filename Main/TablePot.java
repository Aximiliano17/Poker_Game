/**
 * This class keeps track of the mainPot, as well as each player sidePot. If placer C goes all in with 300, and player D
 * raises to 700, even if player C wins, he can't win more than 300 from each player.This feature is not yet implemented
 * but will be soon.
 */
package Main;

import java.util.HashMap;

public class TablePot {
private int mainPot;
private int maxBid;//the newest amount that a player has raised to.
private HashMap<Player,Integer> currentBets=new HashMap<>();//Keeps track of players with their currentBet

public void addToMainPot(int n)
{
	mainPot+=n;
}
public int getMainPot()
{
	return mainPot;
}
public int distributeMainPot()//Method to give the whole amount to the winner/s.
{
	int total=mainPot;
	mainPot=mainPot-mainPot;
	return total;
}
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
	amount+=getCurrentBet(player);
	currentBets.put(player,amount);
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
public int getBetsTotal()
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
