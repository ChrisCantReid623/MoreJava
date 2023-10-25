/**
 * Console view of Tic Tac Toe simulation versus AI opponent.
 * 
 * @authors Christopher Reid
 */

package views_controllers;

import java.util.Scanner;
import model.TicTacToeGame;

public class TTTConsole {

	static TicTacToeGame game = new TicTacToeGame();

	public static void main(String[] args) {
		game.startNewGame();
		ticTacToe();
	} 

	/**
	 * Game engine. Prompts user for input, does validity check for 
	 * open spaces.
	 */
	public static void ticTacToe() {
		// Print blank board
		System.out.println(game.toString());
		
		while (game.stillRunning()) {			
			boolean openSpace = true;
			while (openSpace) {
				
				// Get move from user
				Scanner scanner = new Scanner(System.in);
				System.out.println();
				System.out.print("Enter a row and column: ");
				String input = scanner.nextLine();

				// Store user move
				String[] userMove = input.split(" ");
				int row = Integer.parseInt(userMove[0]);
				int column = Integer.parseInt(userMove[1]);

				// Check if user Move is available
				openSpace = game.available(row, column);
				if (!openSpace) {
					System.out.println();
					System.out.println("Space taken try again.");
					continue;
				}
				game.humanMove(row, column, false);
				System.out.println();
				System.out.println(game.toString());
				System.out.println();
				if (checkForWin()) {
					break;
				}
				
			}
		}
		return;
	}

	/**
	 * After every successful move, checks if the game has been won or 
	 * results in a tie.
	 * @return
	 */
	private static boolean checkForWin() {
		boolean gameWon = false;
		for (int x = 0; x < game.getTicTacToeBoard().length; x++) {
			for (int y = 0; y < game.getTicTacToeBoard()[x].length; y++) {
				char playerChar = game.getTicTacToeBoard()[x][y];

				if (!gameWon) {
					
					// Winning Move
					if ((playerChar != '_') && (playerChar != ' ') && (game.didWin(playerChar))) {
						System.out.println(playerChar + " won!");
						return gameWon = true;
					}
				}

			}

		}
		// Tie Game
		if (game.tied()) {
			System.out.println("Tie Game");
			return true;
		}
		return gameWon;
	}
}