/**
 * This class contains a Menu of other JPanels and calls those JPanels based on which button is clicked.
 */
package Main;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MenuPane extends JPanel {
	private String background = "/images/pokerImage.jpg";

	public MenuPane(PokerGame game) {
		displayMenu(game);
	}

	public void displayMenu(PokerGame game) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JButton playButton = new JButton("Play");
		add(playButton, gbc);
		JButton addButton = new JButton("Add Player");
		add(addButton, gbc);
		JButton removeButton = new JButton("Remove Player");
		add(removeButton, gbc);
		JButton viewButton = new JButton("View Players");
		add(viewButton, gbc);

		/*
		 * ActionListener calls the inner class and passes a class of type JPanel as a
		 * parameter, then the actionlistener inside the class calls the correct JPanel.
		 */
		playButton.addActionListener(new JPanelSelector(new PlayPanel(game)));
		addButton.addActionListener(new JPanelSelector(new AddPanel(game)));
		removeButton.addActionListener(new JPanelSelector(new RemovePanel(game)));
		viewButton.addActionListener(new JPanelSelector(new PlayerPanel(game)));
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(getBackgroundImage(), 0, 0, null);
	}

	private Image getBackgroundImage() {
		ImageIcon i = new ImageIcon(getClass().getResource(background));
		return i.getImage();
	}

	private class JPanelSelector implements ActionListener {
		JPanel panel2;

		JPanelSelector(JPanel panel) {
			this.panel2 = panel;
		}

		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			JPanel panel = (JPanel) button.getParent();
			JFrame frame = (JFrame) SwingUtilities.getRoot(panel);
			frame.remove(panel);
			frame.add(panel2);
			frame.validate();
			frame.repaint();
		}

	}
}
