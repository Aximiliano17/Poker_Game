/**
 * The purpose of this class is to create a circular array,because when someone raises, i basically need to change the end
 * pointer to current-1;
 * Ex:if my players 1->2->3->4, end was at 4, but if player 3 raises, my end needs to change to player 2. Meaning if everyone
 * after player 3 turn calls him, then player 2 will be the last to call before my loop ends.  
 */
package Main;

import java.util.ArrayList;
import java.util.List;

public class CircularArrayList<Player> extends ArrayList<Player>
{
private int end=super.size()-1;
private int start;
//The button determines which players to collect the blinds and where to start each round.
private int button=-1;
private boolean finished;//marks the end of players turn, when someone raises it becomes false

//Constructors
public CircularArrayList(int i) {
	super(i);
}
public CircularArrayList(List<Player> list)
{
	super(list);
}
public CircularArrayList()
{
	super();
}
//Method to get the next player.Uses a boolean to mark the end of player.
public Player getNext()
{
	if(finished)return null;//If it returns null, the program knows there isn't any more players.      	
	if(start==super.size()) start=0;
	if(start==end) {
		finished=true;//finished marks the end of our circular arraylist
	}
	
	Player player=super.get(start);
	start++;
//	System.out.println(start+" "+end); test
	return player;
}
//When someone raises, i need to change the end of the circular array to start-2;
public void setEnd()
{
	finished=false;
	end=start-2;
	if(end==-1)
		end=super.size()-1;
	if(end==-2)
	{
		end=super.size()-2;
	}
	
	//System.out.println(start+" "+end); test
	
}
//method to continue with circular array.
 public void reset()
{
	finished=false;
}
 //Since at the end of the getNext() and setNext() methods I increment start by one, the current player is at start-1
 public Player getCurrPlayer()
 {
	 int current=start-1;
	 if(current==-1)current=super.size()-1;
	 return super.get(current);
 }
 //Need to override my remove and add methods to take the pointer at the end of the array into consideration.
 @Override
 public boolean remove(Object p)
 {
	 boolean b=super.remove(p);
	 if(b) {
	 end--;}
	 return b;
 }
 @Override
 public boolean add(Player p)
 {
	 boolean b=super.add(p);
	 end=super.size()-1;
	 return b; 
 }
 public void setUpButton()//increase button by one and sets start to button
 {
	 button++;
	 if(button==super.size()) button=0;
	 start=button;
 }
 public int getButton()
 {
	 return this.button;
 }
 public Player getBlindPlayer(int n)
 {
	 int index=button+n;
	 if(index==super.size())index=0;
	 else if(index==super.size()+1)index=1;
	 Player p=super.get(index);
	 return p;
 }
 }
