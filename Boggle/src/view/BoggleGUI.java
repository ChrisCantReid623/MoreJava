/**
 * @author Christopher Reid
 * 
 * Models Boggle game with interactive graphical user interface.
 * 
 */

package view;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.*;
import model.Boggle;

public class BoggleGUI extends Application {

	// ----------- Boggle Game Instance Variables-------------
	private static Boggle boggle;
	private static ArrayList<String> dictionary;
	private static HashSet<String> possibleWords;
	private static HashSet<String> allAttempts;
	private static HashSet<String> goodAttempts;
	private static HashSet<String> badAttempts;

	// ------------------ GUI Instances ----------------------

	// Main Canvas
	private BorderPane buttonPane = new BorderPane();
	private GridPane gridPane = new GridPane();

	// Interactive Buttons
	private Button newGameButton = new Button("New Game");
	private Button endGameButton = new Button("End Game");

	private Label attemptLBL = new Label("Enter your attempts here:");
	private Label gameReportLBL = new Label("Game Report:");

	// Text Fields
	private TextArea gameBoardField = new TextArea("Click 'New Game'\n" + "for a fresh Dice Tray");
	private TextArea attemptsField = new TextArea("Click 'End Game'\n" + "to submit");
	private TextArea reportField = new TextArea("");

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		layoutGUI();
		registerListeners();

		Scene scene = new Scene(gridPane, 1000, 400);
		stage.setTitle("Boggle");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Maintains graphical structure.
	 */
	private void layoutGUI() {

		// Column 1: Buttons and Game Board
		buttonPane.setLeft(newGameButton);
		buttonPane.setRight(endGameButton);
		gameBoardField.setMaxSize(300, 300);
		gameBoardField.setPrefHeight(250);
		gameBoardField.setPrefWidth(250);
		gameBoardField.setEditable(false);
		gridPane.add(buttonPane, 1, 1);
		gridPane.add(gameBoardField, 1, 2);

		// Column 2: Attempts
		attemptsField.setMaxSize(300, 300);
		attemptsField.setEditable(false);
		attemptsField.setWrapText(true);
		gridPane.add(attemptLBL, 2, 1);
		gridPane.add(attemptsField, 2, 2);

		// Column 3: Game Report
		reportField.setMaxSize(300, 300);
		reportField.setEditable(false);
		reportField.setWrapText(true);
		gridPane.add(gameReportLBL, 3, 1);
		gridPane.add(reportField, 3, 2);

		// gridPane.setGridLinesVisible(true);
		gridPane.setHgap(15);
		gridPane.setVgap(15);
	}

	/**
	 * Controls button listening functionality.
	 */
	private void registerListeners() {
		newGameButton.setOnAction(new StartButtonListener());
		endGameButton.setOnAction(new StopButtonListener());
	}

	/**
	 * Controls newGameButton functionality.
	 */
	public class StartButtonListener implements EventHandler<ActionEvent> {
		public void handle(ActionEvent arg0) {
			boggle = new Boggle();
			buildDictionary();
			newDiceTray();
		}
	}

	/**
	 * Controls endGameButton functionality.
	 */
	public class StopButtonListener implements EventHandler<ActionEvent> {
		public void handle(ActionEvent arg0) {

			allAttempts = new HashSet<String>();
			goodAttempts = new HashSet<>();
			badAttempts = new HashSet<>();

			// Get input from attempts Text Field
			String input = attemptsField.getText();
			String[] split_words = input.split(" ");
			for (String str : split_words) {
				allAttempts.add(str.toLowerCase());
			}

			for (String word : allAttempts) {
				if (boggle.foundInTray(word)) {
					goodAttempts.add(word);
				} else {
					badAttempts.add(word);
				}
			}

			attemptsField.setEditable(false);
			reportField.setText(generateReport());
			// reportField.setMouseTransparent(true);
		}
	}

	/**
	 * Constructs the game dictionary from the BoggleWords.txt.
	 */
	private void buildDictionary() {
		dictionary = new ArrayList<>();
		possibleWords = new HashSet<>();

		// Builds Dictionary
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
		for (String word : dictionary) {
			if (boggle.foundInTray(word)) {
				possibleWords.add(word);
			}
		}
	}

	/**
	 * Generates the dice tray displayed on gameBoardField.
	 */
	private void newDiceTray() {
		char[][] newBoard = boggle.getDiceTrayString();
		StringBuilder boardString = new StringBuilder();

		for (char[] row : newBoard) {
			for (int col = 0; col < row.length; col++) {
				if (row[col] == 'Q') {
					boardString.append("Qu");
				} else {
					boardString.append(row[col]);
				}
				if (col < row.length - 1) {
					boardString.append("  "); // Add a space between characters
				}
			}
			boardString.append('\n'); // Add a newline between rows
		}

		// Reset GUI Faces
		gameBoardField.setText(boardString.toString());
		Font font = Font.font("Courier New", FontWeight.BOLD, 25);
		gameBoardField.setFont(font);
		attemptsField.setText("");
		attemptsField.setEditable(true);
		Font inputFont = Font.font("Chalkboard", 12);
		attemptsField.setFont(inputFont);
		reportField.setText("");

	}

	/**
	 * Generates the game report. Displayed in reportField.
	 */
	public static String generateReport() {
		StringBuilder report = new StringBuilder();

		// Print Score
		report.append("\nYour score: ").append(boggle.getScore(goodAttempts)).append("\n\n");

		// Print Found Words
		report.append("Words you found:\n");
		report.append("================\n");
		report.append(boggle.wordsToString(goodAttempts)).append("\n\n");

		// Print Incorrect Words
		report.append("Incorrect Words:\n");
		report.append("================\n");
		report.append(boggle.wordsToString(badAttempts)).append("\n\n");

		// Print Missed Words
		HashSet<String> missedWords = boggle.missedWords(goodAttempts, possibleWords);
		report.append("You could have found these ").append(missedWords.size()).append(" more words:\n");
		report.append("============================================\n");

		Set<String> missedSorted = new TreeSet<>(missedWords);
		report.append(boggle.wordsToString(missedSorted)).append("\n\n");

		return report.toString();
	}

}