/**
 * @author Christopher Reid
 * 
 * 			Runs a console game of Boggle.  
 */

package view;
import java.io.*;
import java.util.*;
import model.*;

public class BoggleConsole {
	private static Boggle game = new Boggle();
	private static ArrayList<String> dictionary = new ArrayList<>();
	private static HashSet<String> possibleWords = new HashSet<>();
	private static HashSet<String> allAttempts = new HashSet<>();
	private static HashSet<String> goodAttempts = new HashSet<>();
	private static HashSet<String> badAttempts = new HashSet<>();
	
	public static void main(String[] args){
		buildDictionary();
		gameEngine();
		printReport();
	}
	
	/**
	 * Reads the dictionary.txt file and builds the list containing all possible words
	 * worth points.
	 */
	public static void buildDictionary() {
		// Creates Dictionary
		try {
		      Scanner myReader = new Scanner(new File("BoggleWords.txt"));
		      while (myReader.hasNextLine()) {
		    	  String word = myReader.nextLine();
		    	  dictionary.add(word);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    }
		
		// Finds all possible words in Dice Tray
		for (String word: dictionary) {
			if (game.foundInTray(word)) {
				possibleWords.add(word);
			}
		}
	}
	
	/**
	 * Prompts user for input, performs dice tray checks and prints the game report. 
	 */
	public static void gameEngine() {
		System.out.println("Play one game of Boggle!");
		System.out.println();
		game.printBoard();
		System.out.println();
		System.out.println("Enter words or 'ZZ' to finish inputting: ");
		
		// User Input
		Scanner userInput = new Scanner(System.in);
		while (userInput.hasNext("ZZ") == false) {
			String word = userInput.next();
			String[] split_words = word.split(" ");
			for (String str: split_words) {
				allAttempts.add(str.toLowerCase());
			}
		}
		userInput.close();
		
		// Check attempts
		for (String word: allAttempts) {
			if (game.foundInTray(word)) {
				goodAttempts.add(word);
			} else {
				badAttempts.add(word);
			}
		}
	}
	
	
	/**
	 * Prints the score, correct attempts, incorrect attempts, and all possible but missed words.
	 */
	public static void printReport() {
		//Print Score
		System.out.println();
		System.out.println("Your score: " + game.getScore(goodAttempts));
		System.out.println();
		
		//Print Found Words
		System.out.println("Words you found:");
		System.out.println("================");
		System.out.println(game.wordsToString(goodAttempts));
		System.out.println();
		
		//Print Incorrect Words
		System.out.println("Incorrect Words:");
		System.out.println("================");
		System.out.println(game.wordsToString(badAttempts));
		System.out.println();
		
		//Print Incorrect Words
		HashSet<String> missedWords = game.missedWords(goodAttempts, possibleWords);	
		System.out.println("You could have found these " + missedWords.size() + " more words: ");
		System.out.println("=================================================================");
		Set<String> missedSorted = new TreeSet<>(missedWords);
		System.out.println(game.wordsToString(missedSorted));
		System.out.println();
	}
}