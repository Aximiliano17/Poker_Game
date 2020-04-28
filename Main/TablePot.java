/**
 * This class keeps track of the mainPot, as well as each player sidePot. 
 */
package Main;

import java.util.HashMap;
import java.util.List;

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
public void distributeMainPot(List<Player> winners)//Method to give the whole amount to the winner/s.
{
 int count=winners.size();
 int even=mainPot/count;
 for(Player p:winners)
 {
	 p.addMoney(even);
 }
 mainPot=0;
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
