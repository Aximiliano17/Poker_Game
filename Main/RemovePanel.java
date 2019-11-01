package Main;

import java.awt.Component;
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

public class RemovePanel extends JPanel implements ActionListener {
	private List<Player> players;

	public RemovePanel(PokerGame game) {
		this.players = new ArrayList<Player>(game.getPlayers());
		removePlayer(game);

	}
	public void removePlayer(PokerGame game) {
		if (players.size() > 0) {
			JLabel sign = new JLabel("Click Remove to remove player.");
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(sign, gbc);
			// Loop iterates through list of players and creates a JPanel for each one of them.
			for (Player player: players) {
				add(playerPanel(player), gbc);
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
				game.saveData(players);
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
		JPanel a = new JPanel();
		JLabel b = new JLabel(player.getName());
		JButton c = new JButton("Remove");
		c.addActionListener(this);
		a.add(b);
		a.add(c);
		return a;

	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		JPanel panel = (JPanel) button.getParent();
		for (Component x : panel.getComponents()) {
			if (x instanceof JLabel) {
				JLabel text = (JLabel) x;
				String name = text.getText();
				for (int y = 0; y < players.size(); y++) {
					if (name == players.get(y).getName()) {
						players.remove(y);
					}
				}
			}
		}
		remove(panel);
		validate();
		repaint();
	}
}