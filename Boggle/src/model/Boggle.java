/**
 * @author Christopher Reid
 * 
 *         Models the a game iteration of the game Boggle. Generates a random
 *         dice tray of 16 letter characters. Takes a string input from user
 *         and determines if any of the given words exist in the dice tray. 
 */

package model;
import java.util.*;

public class Boggle {
	private DiceTray theBoard;
	private int score;
	
	/**
	 * Constructs a new game iteration of Boggle using a random Dice Tray.
	 */
	public Boggle() {
		theBoard = new DiceTray();
		score = 0;
	}
	
	/**
	 * Constructs a new game iteration of Boggle with a hard-coded Dice Tray. 
	 */
	public Boggle(char[][] testBoard) {
		theBoard = new DiceTray(testBoard);
		score = 0;
	}
	
	/**
	 * Searches the Dice Tray for a word attempted.
	 */
	public boolean foundInTray(String word) {
		return theBoard.found(word);
	}
	
	/**
	 * Determines the missed words out of the possible attempts.
	 */
	public HashSet<String> missedWords(HashSet<String> correctAttempts, HashSet<String> possibleWords) {
		HashSet<String> missedWords = new HashSet<>();
		for (String word: possibleWords) {
			if(!correctAttempts.contains(word)) {
				missedWords.add(word);
			}
		}
		return missedWords;
	}
	
	/**
	 * Returns the score of the current game.
	 */
	public int getScore(HashSet<String> correctAttempts) {
		for (String word: correctAttempts) {
			if (word.length() <= 4) {
				score = score + 1;
			} else {
				if (word.length() == 5) {
					score = score + 2;
				} else {
					if (word.length() == 6) {
						score = score + 3;
					} else {
						if (word.length() == 7) {
							score = score + 5;
						} else {
							if (word.length() > 7) {
								score = score + 11;
							}
						}
					}
				}
			}
		}
		return score;
	}
		
	/**
	 * Prints the current Dice Tray to the console. 
	 */
	public void printBoard() {
		theBoard.printBoard();
	}
	

	/**
	 * Prints the argument set as a String.
	 */
	public String wordsToString(Set<String> wordSet) {
		StringBuilder retStr = new StringBuilder();
		boolean isFirst = true;
		for (String word: wordSet) {
			if (!isFirst) {
				retStr.append(" ");
            }
			retStr.append(word);
            isFirst = false;
        }
		return retStr.toString();
	}

	public char[][] getDiceTrayString() {
		return theBoard.getDiceTray();
	}
}

	
