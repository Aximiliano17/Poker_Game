package Main;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PlayPanel extends JPanel {
	private List<Player> players;
	private List<Player> allowedPlayers;
	// Since clicking a button inside of a loop, I loose control of that loop,I used
	// this variable to keep tracks of which player i'm currently accessing.
	public int player;
	PokerGame game;

	public PlayPanel(PokerGame game) {
		this.players = new ArrayList<Player>(game.getPlayers());
		this.allowedPlayers = new ArrayList<Player>(game.getAllowedPlayers());
		this.game = game;

		determinePlayers();
	}

	// Determines which players can play and stores them into allowedPlayers
	public void determinePlayers() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		if (players.size() > 1 & players.size() < 7) {
			for (Player p : players) {
				if (p.getMoney() > 10) {
					allowedPlayers.add(p);
				}
			}
			JLabel allowed = new JLabel("<html>Only players with more money than 10 money are allowed to play<br/>"
					+ "The following players are allowed to play:");
			allowed.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(allowed);

			for (Player p : allowedPlayers) {
				JLabel text = new JLabel(p.getName());
				text.setAlignmentX(Component.CENTER_ALIGNMENT);
				add(text);
			}

			JButton continueButton = new JButton("Start");
			continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			continueButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					removeAll();
					turn();
					validate();
					repaint();
				}
			});
			add(continueButton);
			returnMain();

		} else if (players.size() == 1) {
			JLabel warning = new JLabel("You need at least 2 players to play");
			add(warning);
			returnMain();

		} else {
			JLabel warning = new JLabel("Add Players first.");
			add(warning);
			returnMain();
		}
	}

	public void dealCards() {
		Deck deck = Deck.getDeck();
		// Assign each player cards from the deck
		for (Player p : allowedPlayers) {
			while (p.getHand().size() < 5) {
				p.setHand(deck.dealCard());
			}
		}
	}

	// Depending on turn, ante or switchcards will be called
	public void turn() {
		switch (game.getTurn()) {
		case 0:
			ante();
			break;
		case 1:
			dealCards();
			switchCards();
			break;
		case 2:
			playerTurn();
			break;
		case 3:
			assert false;
			break;
		}
	}

	public void ante() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		for (Player p : allowedPlayers) {
			p.substractMoney(10);
			game.setPot(game.getPot() + 10);
		}
		JLabel anteMess = new JLabel("An ante of 10 has been take from each player and added to the pot");
		add(anteMess, gbc);
		JButton continueButton = new JButton("Continue");
		add(continueButton, gbc);
		anteMess.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				game.increaseTurn();
				turn();
				validate();
				repaint();
			}
		});
	}

	public void playerTurn() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		if (player == allowedPlayers.size() && (allowedPlayers.get(0).getTurn() == false)) {
			player = 0;
		} else if (player == allowedPlayers.size() && (allowedPlayers.get(0).getTurn() == true)) {
			displayWinner();
			return;
		}
		if (allowedPlayers.get(player).getTurn() == false) {
			removeAll();
			Player currentPlayer = allowedPlayers.get(player);
			JLabel mess = new JLabel("New cards have been dealt!");
			add(mess, gbc);
			JLabel turn = new JLabel("It is " + currentPlayer.getName() + " turn");
			add(turn, gbc);
			JLabel pot = new JLabel("The current wager is of " + game.getWager() + " and the pot is " + game.getPot());
			JLabel cards = new JLabel("These are your cards:");
			JPanel hand = displayHand(currentPlayer);
			add(pot, gbc);
			add(cards, gbc);
			add(hand, gbc);
			JLabel message = new JLabel("Your current money is:" + currentPlayer.getMoney());
			JPanel buttons = new JPanel();

			add(message, gbc);
			if (game.getWager() > allowedPlayers.get(player).getCurrentBet()) {
				JLabel call2 = new JLabel(
						"Would you like to call: " + (game.getWager() - allowedPlayers.get(player).getCurrentBet()));
				JButton call = new JButton("Call");
				call.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						call();
					}
				});
				buttons.add(call);
				add(call2, gbc);
			} else {
				JButton check = new JButton("Check");
				check.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						allowedPlayers.get(player).setTurn(true);
						player++;
						playerTurn();
						validate();
						repaint();
					}
				});
				buttons.add(check);
			}
			JButton raise = new JButton("Raise");
			raise.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					raise();
				}
			});
			buttons.add(raise);
			JButton fold = new JButton("Fold");
			fold.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fold();
				}
			});
			buttons.add(fold);
			add(buttons, gbc);
		} else {
			player++;
			playerTurn();
		}
	}

	// Asks players one by one which cards they want to switch
	public void switchCards() {
		removeAll();
		if (player < allowedPlayers.size()) {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			JLabel message = new JLabel("It is " + allowedPlayers.get(player).getName() + "'s turn!");
			JLabel message2 = new JLabel("You have been dealt the following cards. Choose to switch or skip.");
			JButton next = new JButton("Next");
			add(message, gbc);
			add(message2, gbc);

			// Make a JPanel for each card.
			for (Card card : allowedPlayers.get(player).getHand()) {
				JPanel hand = cardPanel(card);
				add(hand, gbc);
			}
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					player++;
					switchCards();
					validate();
					repaint();
				}
			});
			add(next);

		} else if (player == allowedPlayers.size()) {
			player = 0;
			dealCards();
			game.increaseTurn();
			turn();
		}
	}

	public void displayWinner() {
		removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		Player pWinner = decideWinner();
		JLabel done = new JLabel("These are everyone's cards: ");
		add(done, gbc);
		for (int x = 0; x < allowedPlayers.size(); x++) {
			JPanel display = new JPanel();
			JLabel name = new JLabel(allowedPlayers.get(x).getName() + " ");
			JPanel hand = displayHand(allowedPlayers.get(x));
			display.add(name);
			display.add(hand);
			add(display, gbc);
		}
		JLabel winner = new JLabel(pWinner.getName() + " won with " + pWinner.getHandRank().getRanking());
		JLabel added = new JLabel(game.getPot() + " has been added to the winner");
		pWinner.addMoney(game.getPot());
		add(winner, gbc);
		add(added, gbc);
		resetGame();
		returnMain();

	}

	public JPanel displayHand(Player player) {
		JPanel hand = new JPanel();
		for (Card card : player.getHand()) {
			JLabel cards = new JLabel(card.toString());
			hand.add(cards);
		}
		return hand;
	}

	JPanel cardPanel(Card card) {
		JPanel cards = new JPanel();
		JLabel display = new JLabel(card.toString());
		JButton switchButton = new JButton("Switch");
		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				JPanel panel = (JPanel) button.getParent();
				allowedPlayers.get(player).removeCard(card);
				remove(panel);
				validate();
				repaint();
			}
		});
		cards.add(display);
		cards.add(switchButton);
		return cards;

	}

	public void defaultWin() {
		removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel winner = new JLabel("Since everyone else folded the winner is: " + allowedPlayers.get(0).getName());

		JLabel winner2 = new JLabel(allowedPlayers.get(0).getName() + " has won " + game.getPot() + " money");
		allowedPlayers.get(0).addMoney(game.getWager());

		winner.setAlignmentX(Component.CENTER_ALIGNMENT);
		winner2.setAlignmentX(Component.CENTER_ALIGNMENT);

		add(winner);
		add(winner2);
		returnMain();
		resetGame();
		validate();
		repaint();
		return;
	}

	public Player decideWinner() {
		for (Player player : allowedPlayers) {
			Ranking.evaluateHand(player);
		}
		Collections.sort(allowedPlayers, new Comparator<Player>() {
			public int compare(Player one, Player two) {
				for (HandRanking a : one.getHandValue().keySet()) {
					for (HandRanking b : two.getHandValue().keySet()) {
						int result = a.ordinal() - b.ordinal();
						if (result != 0)
							return result;
						return one.getHandValue().get(a) - two.getHandValue().get(b);
					}
				}
				return 0;

			}
		});
		return allowedPlayers.get(allowedPlayers.size() - 1);
	}

	private void returnMain() {
		JButton returnButton = new JButton("Main Menu");
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
		returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(returnButton);
	}

	public void resetGame() {
		player = 0;
		game.setPot(0);
		game.setWager(0);
		Deck.discardDeck();
		game.resetTracker();
		for (Player player : allowedPlayers) {
			player.clearHand();
			player.clearTurn();
		}
		allowedPlayers.clear();
	}

	public void raise() {
		removeAll();
		JLabel message = new JLabel("Enter amount to raise");
		JTextField amount = new JTextField(10);
		JButton enter = new JButton("Enter");
		add(message);
		add(amount);
		add(enter);
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeAll();
					int amount2 = Integer.parseInt(amount.getText());
					if (allowedPlayers.get(player).getMoney() > amount2) {
						allowedPlayers.get(player).substractMoney(amount2);
						game.setWager(game.getWager() + amount2);
						game.setPot(game.getPot() + amount2);
						JLabel mess = new JLabel("Amount betting has been raised to" + game.getWager());
						add(mess);
						for (Player player : players) {
							player.clearTurn();
						}
						allowedPlayers.get(player).setCurrentBet(game.getWager());
						allowedPlayers.get(player).setTurn(true);
						player++;
						turn();
						validate();
						repaint();

					} else {
						JLabel warning = new JLabel("Cant raise more than what you have. Make another choice");
						add(warning);
						turn();
						validate();
						repaint();
					}

				} catch (NumberFormatException ex) {
					JLabel warning = new JLabel("Please dont use words");
					add(warning);
					turn();
					validate();
					repaint();
				}

			}
		});
		validate();
		repaint();
	}

	public void fold() {
		removeAll();
		JLabel message = new JLabel(allowedPlayers.get(player).getName() + " has folded");
		add(message);

		if (allowedPlayers.size() > 1) {
			allowedPlayers.remove(player);
			if (player >= 1) {
				player--;
			}
			turn();
			validate();
			repaint();
		}
		if (allowedPlayers.size() == 1) {
			defaultWin();
			validate();
			repaint();
		}
	}

	public void call() {
		removeAll();
		int difference = game.getWager() - allowedPlayers.get(player).getCurrentBet();

		if (allowedPlayers.get(player).getMoney() >= difference) {
			allowedPlayers.get(player).substractMoney(difference);
			game.setPot(game.getPot() + difference);
			JLabel message = new JLabel(allowedPlayers.get(player).getName() + " has called");
			add(message);
			allowedPlayers.get(player).setCurrentBet(game.getWager());
			allowedPlayers.get(player).setTurn(true);
			player++;
			turn();
			validate();
			repaint();
		} else {
			JLabel message = new JLabel("You do not have enough money.Chosse another option");
			add(message);
			turn();
			validate();
			repaint();
		}
	}
}
