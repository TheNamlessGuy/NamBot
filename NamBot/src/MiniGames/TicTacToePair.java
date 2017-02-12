package MiniGames;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class TicTacToePair extends MiniGamePair {
	public static final boolean P1 = false;
	public static final boolean P2 = true;
	
	public static final short NONE = 0;
	public static final short X = 1;
	public static final short O = 2;
	
	private boolean turn;
	
	private short[][] board;
	/*
	 * board[0][0-2] -> [ ] | [ ] | [ ]
	 * 					---------------
	 * board[1][0-2] -> [ ] | [ ] | [ ]
	 * 					---------------
	 * board[2][0-2] -> [ ] | [ ] | [ ]
	 */
	
	public TicTacToePair(User first, User second, MessageChannel originalChannel) {
		super(first, second, originalChannel);
		turn = P1;
		board = new short[3][3];
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = NONE;
			}
		}
	}
	
	public boolean isUsersTurn(User u) {
		if (turn == P1 && u.getId().equals(first.getId())) {
			return true;
		} else if (turn == P2 && u.getId().equals(second.getId())) {
			return true;
		}
		return false;
	}
	
	public boolean canPlace(int row, int col) {
		if (row > 3 || row < 0 || col > 3 || col < 0) { return false; }
		return (board[row][col] == NONE);
	}
	
	public boolean takeTurn(int row, int col) {
		if (turn == P1) {
			board[row][col] = X;
		} else {
			board[row][col] = O;
		}
		
		if (checkWin()) {
			win(false);
			return true;
		} else if (checkTie()) {
			tie();
			return true;
		} else {
			turn = !turn;
			sendBoard();
			return false;
		}
	}
	
	public void forfeit() {
		turn = !turn;
		win(true);
	}
	
	public boolean checkWin() {
		if ((board[0][0] != NONE && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
			(board[0][2] != NONE && board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
			// diagonals match
			return true;
		}		
		for (int i = 0; i < 3; i++) {
			if (board[i][0] != NONE && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				// row i matches
				return true;
			}
			if (board[0][i] != NONE && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				// col i matches
				return true;
			}
		}		
		return false;
	}
	
	public boolean checkTie() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row][col] == NONE) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void tie() {
		first.openPrivateChannel().queue((ch) -> {
			ch.sendMessage("Tie, no winner!").queue();
		});
		second.openPrivateChannel().queue((ch) -> {
			ch.sendMessage("Tie, no winner!").queue();
		});
		channel.sendMessage("The tic tac toe match between " + first.getAsMention() + " and " + second.getAsMention() + " ended in a tie!").queue();
	}
	
	public void win(boolean forfeit) {
		if (turn == P1) { // P1 won
			win(first, second, forfeit, " in their game of tic tac toe");
		} else { // P2 won
			win(second, first, forfeit, " in their game of tic tac toe");
		}
	}
	
	public void sendBoard() {
		String msg = "Current board:\n" + getBoard();
		first.openPrivateChannel().queue((ch) -> {
			if (turn == P1) {
				ch.sendMessage(msg + "\nYou are OO\nIt's your turn").queue();
			} else {
				ch.sendMessage(msg + "\nYou are OO\nIt's your opponents turn").queue();
			}
		});
		second.openPrivateChannel().queue((ch) -> {
			if (turn == P2) {
				ch.sendMessage(msg + "\nYou are XX\nIt's your turn").queue();
			} else {
				ch.sendMessage(msg + "\nYou are XX\nIt's your opponents turn").queue();
			}
		});
	}
	
	public String getBoard() {
		/* EXAMPLE BOARD:
		 * 
		 * XX | XX | 02
		 * ------------
		 * 10 | OO | 12
		 * ------------
		 * 20 | 21 | 22
		 */
		String s = "```";
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (col != 0) { s += " "; }
				
				if (board[row][col] == NONE) {
					s += Integer.toString(row) + Integer.toString(col);
				} else if (board[row][col] == X) {
					s += "OO";
				} else if (board[row][col] == O) {
					s += "XX";
				}

				if (col != 2) { s += " |"; }
			}
			s += "\n";
			if (row != 2) {
				s += "------------\n";
			}
		}
		
		s += "```";
		return s;
	}
	
	public static String startingBoard() {
		return "```00 | 01 | 02\n------------\n10 | 11 | 12\n------------\n20 | 21 | 22```";
	}
}
