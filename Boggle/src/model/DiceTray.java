/**
 * @author Christopher Reid
 * 
 *         Models the tray of dice in the game Boggle. A DiceTray can be
 *         constructed with 4x4 array of characters for testing.
 */

package model;
import java.util.*;

public class DiceTray {
	private char[][] diceTray;
	private ArrayList<List<Integer>> stored = new ArrayList<List<Integer>>();
	
	/**
	 * Constructs a DiceTray object using a hard_coded 2D array of characters.
	 */
	public DiceTray(char[][] newDiceBoard) {
		diceTray = newDiceBoard;
	}
	
	/**
	 * Constructs a DiceTray object by randomly selecting 16 six sided dice.
	 */
	public DiceTray() {
		char[][] newBoard = new char[4][4];
		char[][] diceOptions = {
				{'L', 'R', 'Y', 'T', 'T', 'E'},
				{'A', 'N', 'A', 'E', 'E', 'G'},
				{'A', 'F', 'P', 'K', 'F', 'S'},
				{'Y', 'L', 'D', 'E', 'V', 'R'},
				{'V', 'T', 'H', 'R', 'W', 'E'},
				{'I', 'D', 'S', 'Y', 'T', 'T'},
				{'X', 'L', 'D', 'E', 'R', 'I'},
				{'Z', 'N', 'R', 'N', 'H', 'L'},
				{'E', 'G', 'H', 'W', 'N', 'E'},
				{'O', 'A', 'T', 'T', 'O', 'W'},
				{'H', 'C', 'P', 'O', 'A', 'S'},
				{'N', 'M', 'I', 'H', 'U', 'Q'},
				{'S', 'E', 'O', 'T', 'I', 'S'},
				{'M', 'T', 'O', 'I', 'C', 'U'},
				{'E', 'N', 'S', 'I', 'E', 'U'},
				{'O', 'B', 'B', 'A', 'O', 'J'}};
		
		//Shuffles Dice Options
		List<Integer> diceIndex = new ArrayList<>();
        for (int i = 0; i < diceOptions.length; i++) {
        	diceIndex.add(i);
        }
        Collections.shuffle(diceIndex);
        
        int dieNum = 0;
		for (int row = 0; row < newBoard.length; row++) {
            for (int col = 0; col < newBoard[row].length; col++) {
            	Random dieSide = new Random();
                int dieDisplayed = dieSide.nextInt(6);
            	newBoard[row][col] = diceOptions[diceIndex.indexOf(dieNum)][dieDisplayed];
            	dieNum++;
            }
		}
		diceTray = newBoard;
	}
	
	/**
	 * Returns true if attempt can be found on the DiceTray following the rules of
	 * Boggle such as single die usage.
	 */
	public boolean found(String input) {
		stored.clear();
		boolean match = false;
		if (input.length() < 3) {
			return false;
		}

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (search(input, row, col, 0)) {
					match = true;
				}
			}
		}
		return match;
	}

	/** 
	 * Searches a dice tray cell for a character from the input string. Performs a 
	 * progressive surrounding check to advance the word search. 
	 */
	public boolean search(String input, int row, int col, int inputIndex) {
		boolean found = false;
		int quCase = 0;
		
		if (col > 3) {
			row++;
			col = 0;
		}

		char diceLetter = Character.toLowerCase(diceTray[row][col]);
		char inputLetter = Character.toLowerCase(input.charAt(inputIndex));
		List<Integer> location = Arrays.asList(row, col);

		// Input letter found in dice tray
		if (diceLetter == inputLetter && (!stored.contains(location))) {
			stored.add(location);
			
			// "Qu" in word
			if (diceLetter == 'q' && Character.toLowerCase(input.charAt(inputIndex + 1)) == 'u') {
				quCase += 1;
				found = search(input, row, col, inputIndex + 1 + quCase);
			}

			// Word is found
			if (inputIndex == input.length() - 1) {
				found = true;
			}
			// check right
			if (!found && col < 3) {
				found = search(input, row, col + 1, inputIndex + 1 + quCase);
			}
			// check down
			if (!found && row < 3) {
				found = search(input, row + 1, col, inputIndex + 1 + quCase);
			}
			// check left
			if (!found && col > 0) {
				found = search(input, row, col - 1, inputIndex + 1 + quCase);
			}
			// check up
			if (!found && row > 0) {
				found = search(input, row - 1, col, inputIndex + 1 + quCase);
			}
			// check up-right
			if (!found && (row > 0) && (col < 3)) {
				found = search(input, row - 1, col + 1, inputIndex + 1 + quCase);
			}
			// check down-right
			if (!found && (row < 3) && (col < 3)) {
				found = search(input, row + 1, col + 1, inputIndex + 1 + quCase);
			}
			// check down-left
			if (!found && (row < 3) && (col > 0)) {
				found = search(input, row + 1, col - 1, inputIndex + 1 + quCase);
			}
			// check up-left
			if (!found && (row > 0) && (col > 0)) {
				found = search(input, row - 1, col - 1, inputIndex + 1 + quCase);
			}

			if (found) {
				return true;
			} else {
				stored.remove(stored.size() -1);
				return false;
			}
		}
		return found;
	}
	
	/** 
	 * Prints the contents of the dice tray. 
	 */
	public void printBoard() {
        for (int row = 0; row < diceTray.length; row++) {
            for (int col = 0; col < diceTray[row].length; col++) {
            	if (diceTray[row][col] != 'Q')
                System.out.print(diceTray[row][col] + "  ");
            	else {
            		System.out.print("Qu ");
            	}
            }
            System.out.println();
            System.out.println();
        }
    }
	
	public char[][] getDiceTray() {
		return diceTray;
	}
}
