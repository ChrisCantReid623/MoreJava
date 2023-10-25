/**
 * Rick suggests, the IntermediateAI first check to stop a win of the opponent, 
 * then look for its own win. If neither is found, select any other open
 * spot randomly. You may use any other strategy as long as it beats RandomAI.
 * 
 * @authors Rick Mercer and Christopher Reid
 */
package model;

import java.util.Random;

public class IntermediateAI implements TicTacToeStrategy {

	/**
	 * Decision process for intermediate AI. First checks if the AI can win,
	 * then if it can prevent the user from winning. Last resort is to pick
	 * any random open space. 
	 */
	@Override
	public OurPoint desiredMove(TicTacToeGame theGame) {
		boolean set = false;
		while (!set) {
			if (theGame.maxMovesRemaining() == 0)
				throw new IGotNowhereToGoException(" -- Hey there programmer, the board is filled");

			int[] moveHere = null;

			moveHere = checkCPUWinningMove(theGame);
			if (moveHere != null) {
				set = true;
				return new OurPoint(moveHere[0], moveHere[1]);
			} else {
				moveHere = checkHumanWinningMove(theGame);
				if (moveHere != null) {
					set = true;
					return new OurPoint(moveHere[0], moveHere[1]);
				}
			}
			Random generator = new Random();
			int row = generator.nextInt(3);
			int col = generator.nextInt(3);
			if (theGame.available(row, col)) {
				set = true;
				return new OurPoint(row, col);
			}
		}
		return null; // Avoid a compile-time error
	}

	/**
	 * Checks if any move would result in a win for the computer. 
	 * @param original
	 * @return
	 */
	private int[] checkCPUWinningMove(TicTacToeGame original) {
		char cpuChar = 'O';
		char[][] testBoard = copyBoard(original);

		boolean gameWon = false;
		boolean testVal = false;
		// Check for CPU winning move
		for (int testRow = 0; testRow < testBoard.length; testRow++) {
			for (int testCol = 0; testCol < testBoard[testRow].length; testCol++) {
				if (testBoard[testRow][testCol] == '_') {
					testVal = true;
					testBoard[testRow][testCol] = cpuChar;
					gameWon = wonByRow(testBoard, cpuChar) || wonByCol(testBoard, cpuChar)
							|| wonByDiagonal(testBoard, cpuChar);
				}
				if (gameWon) {
					int[] desiredMove = new int[2];
					desiredMove[0] = testRow;
					desiredMove[1] = testCol;
					return desiredMove;
				}
				if (testBoard[testRow][testCol] == cpuChar && testVal == true) {
					testBoard[testRow][testCol] = '_';
				}
				testVal = false;
			}
		}
		return null;
	}

	/**
	 * Checks if any move would result in a win for the user and choose that move.
	 * @param original
	 * @return
	 */
	private int[] checkHumanWinningMove(TicTacToeGame original) {
		char humanChar = 'X';
		char[][] testBoard = copyBoard(original);

		boolean gameWon = false;
		boolean testVal = false;
		// Check for human winning move
		for (int testRow = 0; testRow < testBoard.length; testRow++) {
			for (int testCol = 0; testCol < testBoard[testRow].length; testCol++) {
				if (testBoard[testRow][testCol] == '_') {
					testVal = true;
					testBoard[testRow][testCol] = humanChar;
					gameWon = wonByRow(testBoard, humanChar) || wonByCol(testBoard, humanChar)
							|| wonByDiagonal(testBoard, humanChar);
				}
				if (gameWon) {
					int[] desiredMove = new int[2];
					desiredMove[0] = testRow;
					desiredMove[1] = testCol;
					return desiredMove;
				}
				if (testBoard[testRow][testCol] == humanChar && testVal == true) {
					testBoard[testRow][testCol] = '_';
				}
				testVal = false;
			}
		}
		return null;
	}

	/**
	 * Duplicates the TicTacToe board object for strategy testing.
	 * @param original
	 * @return
	 */
	private char[][] copyBoard(TicTacToeGame original) {
		char[][] sourceBoard = original.getTicTacToeBoard();
		int numRows = sourceBoard.length;
		int numCols = sourceBoard[0].length;

		char[][] copyBoard = new char[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				copyBoard[i][j] = sourceBoard[i][j];
			}
		}
		return copyBoard;
	}

	/**
	 * Finds horizontal win combinations. 
	 * @param testBoard
	 * @param playerChar
	 * @return
	 */
	private boolean wonByRow(char[][] testBoard, char playerChar) {
		for (int r = 0; r < 3; r++) {
			int rowSum = 0;
			for (int c = 0; c < 3; c++)
				if (testBoard[r][c] == playerChar)
					rowSum++;
			if (rowSum == 3)
				return true;
		}
		return false;
	}

	/**
	 * Finds vertical win combinations. 
	 * @param testBoard
	 * @param playerChar
	 * @return
	 */
	private boolean wonByCol(char[][] testBoard, char playerChar) {
		for (int c = 0; c < 3; c++) {
			int colSum = 0;
			for (int r = 0; r < 3; r++)
				if (testBoard[r][c] == playerChar)
					colSum++;
			if (colSum == 3)
				return true;
		}
		return false;
	}

	/**
	 * Finds diagonal win combinations. 
	 * @param testBoard
	 * @param playerChar
	 * @return
	 */
	private boolean wonByDiagonal(char[][] testBoard, char playerChar) {
		// Check Diagonal from upper left to lower right
		int sum = 0;
		for (int r = 0; r < 3; r++)
			if (testBoard[r][r] == playerChar)
				sum++;
		if (sum == 3)
			return true;

		// Check Diagonal from upper right to lower left
		sum = 0;
		for (int r = 3 - 1; r >= 0; r--)
			if (testBoard[3 - r - 1][r] == playerChar)
				sum++;
		if (sum == 3)
			return true;

		// No win on either diagonal
		return false;
	}
}