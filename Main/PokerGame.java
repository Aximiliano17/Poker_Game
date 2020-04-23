
package Main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

	public static void readData() {
		String filename = ("data.txt");
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new File(filename));
		} catch (IOException e) {
			System.out.println("Error opening file");
		}
		while (inputStream.hasNextLine()) {
			String lineString = inputStream.nextLine();
			String[] line = lineString.split("\\s+");
			String first = line[0];
			int second = Integer.parseInt(line[1].trim());
			players.add(new Player(first, second));
		}
		System.out.println("Saved Data from file has been loaded");
	}

	public static void saveData(List<Player> players) {
		setPlayers(players);
		String filename = ("data.txt");
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(filename);
		} catch (IOException e) {
			System.out.println("Error opening the file.");
		}

		for (int i = 0; i < players.size(); i++) {

			outputStream.print(players.get(i).getName() + " ");
			outputStream.print(players.get(i).getMoney());
			outputStream.println();
		}
		outputStream.close();
	}

	public static List<Player> getPlayers() {
		return players;
	}

	public static void setPlayers(List<Player> p) {
		players = p;
	}
}
