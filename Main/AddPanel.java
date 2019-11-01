package Main;
/**
 * This JPanel class is for adding players.
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddPanel extends JPanel {
	private List<Player> players;

	public AddPanel(PokerGame game) {
		players = new ArrayList<Player>(game.getPlayers());
		addPlayer(game);
	}

	public void addPlayer(PokerGame game) {
		setLayout(new FlowLayout());
		if (game.getPlayers().size() < 5) {
			JLabel enter = new JLabel("Enter player name to add(no spaces)");
			JTextField name = new JTextField(20);
			JButton enterButton = new JButton("Add Player");
			add(enter);
			add(name);
			add(enterButton);
			enterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String playerName = name.getText();
					if (playerName.contains(" ")) {
						JLabel warning = new JLabel("<html>Please don't use spaces.<html/>");
						add(warning);
						name.setText("");
						revalidate();
						repaint();
					} else if (players.size() >= 6) {
						JLabel warn = new JLabel(
								"<html>The Maximum number of players have been added. <br/> Delete a Player to add another one<html/>");
						add(warn);
						revalidate();
						repaint();
					} else {
						players.add(new Player(playerName));
						JLabel added = new JLabel(playerName + " has been added");
						add(added);
						name.setText("");
						game.saveData(players);
						revalidate();
						repaint();
					}

				}
			});
		} else if (game.getPlayers().size() >= 5) {
			JLabel warn = new JLabel(
					"<html>The Maximum number of players have been added.<br/> Delete a Player to add another one<html/>");
			add(warn);
		}
		returnMain(game);
	}

	private void returnMain(PokerGame game) {
		JButton returnButton = new JButton("Main Menu");
		add(returnButton);
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				JPanel panel = (JPanel) button.getParent();
				JFrame frame = (JFrame) SwingUtilities.getRoot(panel);
				frame.remove(panel);
				frame.add(new MenuPane(game));
				frame.validate();
				frame.repaint();
			}
		});
	}
}
