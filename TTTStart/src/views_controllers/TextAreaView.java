/**
 * This is the beginning of one view of a Tic Tac Toe game using
 * two TextField objects and one TextArea. The other two views
 * of ButtonView and DrawingView follow the same structure as this.
 * 
 * @author Rick Mercer and Christopher Reid 
 */

package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

public class TextAreaView extends BorderPane implements OurObserver {

	private TicTacToeGame theGame;
	private TextField rowInp;
	private Label rowLabel;
	private TextField columnInp;
	private Label columnLabel;
	private Button submitButton;
	private GridPane gridPane;
	private TextArea currentGame;

	public TextAreaView(TicTacToeGame theModel) {
		theGame = theModel;
		initializePanel();
		registerListeners();
	} 

	/**
	 * Initiates GUI.
	 */
	private void initializePanel() {
		
		rowInp = new TextField();
		rowLabel = new Label(" row");
		columnInp = new TextField();
		columnLabel = new Label(" column");
		submitButton = new Button("Make Move");
		gridPane = new GridPane();
		currentGame = new TextArea();
		
		gridPane.add(rowInp, 1, 1);
		gridPane.add(rowLabel, 2, 1);
		gridPane.add(columnInp, 1, 3);
		gridPane.add(columnLabel, 2, 3);
		gridPane.add(submitButton, 1, 4);
		gridPane.setVgap(5);
		gridPane.setHgap(15);
		this.setTop(gridPane);

		currentGame.setStyle("-fx-border-color: blue; border-width: 10;");
		Font font = new Font("Courier", 55);
		currentGame.setFont(font);
		currentGame.setText(theGame.toString());
		currentGame.setEditable(false);
		this.setBottom(currentGame);
	}

	/**
	 * Controls submitButton listening functionality.
	 */
	private void registerListeners() {
		submitButton.setOnAction(new SubmitButtonListener());
	}

	/**
	 * Controls submitButton functionality. Runs 
	 * Tic Tac Toe game engine.
	 */
	public class SubmitButtonListener implements EventHandler<ActionEvent> {
		public void handle(ActionEvent arg0) {
			try {
				String rowInput = rowInp.getText().trim();
				String columnInput = columnInp.getText().trim();
				int row = Integer.parseInt(rowInput);
				int column = Integer.parseInt(columnInput);
				boolean openSpace = theGame.available(row, column);
				if (!openSpace) {
					submitButton.setText("Invalid Choice");
				} else {
					theGame.humanMove(row, column, false);
					submitButton.setText("Make Move");
					rowInp.setText("");
					columnInp.setText("");
					update(theGame);
					checkForWin();
				}

			} catch (Exception e) {
				submitButton.setText("Invalid Choice");
			}
		}
	}
	
	/**
	 * Checks for winning move.
	 */
	private void checkForWin() {
		for (int x = 0; x < theGame.getTicTacToeBoard().length; x++) {
			for (int y = 0; y < theGame.getTicTacToeBoard()[x].length; y++) {
				char playerChar = theGame.getTicTacToeBoard()[x][y];
					// Winning Move
					if ((playerChar != '_') && (playerChar != ' ') && (theGame.didWin(playerChar))) {
						submitButton.setText(playerChar + " won!");
						submitButton.setDisable(true);
						rowInp.setEditable(false);
						columnInp.setEditable(false);
					}
			}
		}
		// Tie Game
		if (theGame.tied()) {
			submitButton.setText("Tie Game!");
			submitButton.setDisable(true);
			rowInp.setEditable(false);
			columnInp.setEditable(false);
		}
	}

	// This method is called by Observable's notifyObservers()
	@Override
	public void update(Object observable) {
		currentGame.setText(theGame.toString());
	} 
}