
package Main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class PokerGame extends JFrame {
	private static List<Player> players = new ArrayList<Player>(6);

	public static void main(String[] args) {
		PokerGame game = new PokerGame();
		readData();
		game.add(new MenuPane());
		game.setSize(400, 400);
		game.setLocationRelativeTo(null);
		game.setTitle("Poker Game");
		game.setResizable(false);
		game.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public static void readData() {
		try
        {
            FileInputStream fis = new FileInputStream("data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            players = (ArrayList<Player>) ois.readObject();
            ois.close();
            fis.close();
         }catch(IOException ioe){
        	 System.out.println("There are no current Players.");
             return;
          }
		catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return;
          }

	}

	public static void saveData(List<Player> players) {
		setPlayers(players);
		try {
		   FileOutputStream fos= new FileOutputStream("data.txt");
	         ObjectOutputStream oos= new ObjectOutputStream(fos);
	         oos.writeObject(players);
	         oos.close();
	         fos.close();
	       }catch(IOException ioe){
	            ioe.printStackTrace();
	        }
	   }

	public static List<Player> getPlayers() {
		return players;
	}

	public static void setPlayers(List<Player> p) {
		players = p;
	}
}
