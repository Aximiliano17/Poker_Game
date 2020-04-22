package Main;

import java.util.List;


public class Dealer {
	private final int ANTE = 5;
	private final int SMALLBLIND = 10;
	private final int BIGBLIND = 20;
	private final int MIN = 200;

//Determines which players can play and stores them into allowedPlayers
	public CircularArrayList<Player> determinePlayers(List<Player> players) {
		CircularArrayList<Player> allowedPlayers = new CircularArrayList<>();

		for (Player p : players) {
			if (p.getMoney() >= 200) {
				allowedPlayers.add(p);
			}
		}
		return allowedPlayers;
	}
	public int addAnte(CircularArrayList<Player> players)
	{
		int total=0;
		for(Player p:players)
		{
			total+=p.substractMoney(ANTE);
		}
		return total;
	}
	public int getAnte()
	{
		return ANTE;
	}
}
