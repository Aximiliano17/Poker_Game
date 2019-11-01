package Main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PlayerPanel extends JPanel {
	private List<Player> players;
	public PlayerPanel(PokerGame game) {
		this.players = new ArrayList<Player>(game.getPlayers());
		playerData(game);
	}

	public void playerData(PokerGame game) {
		if (players.size() > 0) {
			JLabel sign = new JLabel("List of current Players and their money");
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(sign, gbc);
			// Loop iterates through list of players and creates a JPanel for each one
			for (int x = 0; x < players.size(); x++) {
				add(playerPanel(players.get(x)), gbc);
			}
			returnMain(game);
		} else {
			JLabel sign = new JLabel("There are no Players, please add one.");
			add(sign);
			returnMain(game);

		}
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

	private JPanel playerPanel(Player player) {
		JPanel secondPanel = new JPanel();
		JLabel name = new JLabel(player.getName());
		JLabel money = new JLabel(Integer.toString(player.getMoney()));
		secondPanel.add(name);
		secondPanel.add(money);
		return secondPanel;
	}
}
