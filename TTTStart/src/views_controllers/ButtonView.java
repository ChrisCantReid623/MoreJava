/**
 * One view of a Tic Tac Toe game using interactive Button interface.
 * 
 * @author Rick Mercer and Christopher Reid 
 */

package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.OurObserver;
import model.TicTacToeGame;

public class ButtonView extends BorderPane implements OurObserver {
	
	private TicTacToeGame theGame;
	
	private Button[][] buttonList = new Button[3][3];
	private GridPane gridPane = new GridPane();
	
	private Button topLeft = new Button("_");
	private Button topCenter = new Button("_");
	private Button topRight = new Button("_");
	
	private Button middleLeft = new Button("_");
	private Button middleCenter = new Button("_"); 
	private Button middleRight = new Button("_");
	
	private Button bottomLeft = new Button("_");
	private Button bottomCenter = new Button("_");
	private Button bottomRight = new Button("_");
	
	private Text message = new Text("Click to make a move");
	
	public ButtonView(TicTacToeGame theModel) {
		theGame = theModel;
		initializePanel();
		registerListeners();
	} 

	/**
	 * Controls visual button layout.
	 */
	private void initializePanel() {
		// Add buttons to ArrayList container
		buttonList[0][0] = topLeft;
		buttonList[0][1] = topCenter;
		buttonList[0][2] = topRight;
		
		buttonList[1][0] = middleLeft;
		buttonList[1][1] = middleCenter;
		buttonList[1][2] = middleRight;
		
		buttonList[2][0] = bottomLeft;
		buttonList[2][1] = bottomCenter;
		buttonList[2][2] = bottomRight;
		
		
		message.setFont(Font.font("verdana", 20));
		message.setStroke(Color.BLUE);
		for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
            	buttonList[row][col].setFont(Font.font("verdana", 35));
            	buttonList[row][col].setPrefSize(75, 75);
            }
        }
		
		// Add Buttons to Grid pane
		gridPane.add(topLeft, 1, 0);
		gridPane.add(middleLeft, 1, 1);
		gridPane.add(bottomLeft, 1, 2);
		
		gridPane.add(topCenter, 2, 0);
		gridPane.add(middleCenter, 2, 1);
		gridPane.add(bottomCenter, 2, 2);
		
		gridPane.add(topRight, 3, 0);
		gridPane.add(middleRight, 3, 1);
		gridPane.add(bottomRight, 3, 2);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(5);
		gridPane.setHgap(5);
				
		BorderPane.setAlignment(message, Pos.CENTER);
		this.setBottom(message);
		this.setCenter(gridPane);
	}
	
	/**
	 * Controls button listening functionality.
	 */
	private void registerListeners() {
		for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
            	buttonList[row][col].setOnAction(new ButtonListener());
            }
        }
	}
	
	/**
	 * Controls button listener functionality. 
	 */
	public class ButtonListener implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Button clicked = (Button) event.getSource();
			// Iterate over 2D array of Bottoms
			
			for (int row = 0; row < 3; row++) {
	            for (int col = 0; col < 3; col++) {
	                if (clicked == buttonList[row][col]) {
	                	boolean openSpace = theGame.available(row, col);
	    				if (!openSpace) {
	    					message.setText("Invalid Choice");
	    				} else {
	    					message.setText("Click to make a move");
	    					clicked.setText("X");
	    					theGame.humanMove(row, col, false);
	    					checkForWin();
	    				}
	                }
	                
	            }
	        }
			update(theGame);
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
				// Winning Move
				if ((playerChar != '_') && (playerChar != ' ') && (theGame.didWin(playerChar))) {
					message.setText(playerChar + " won!");
					for (int row = 0; row < 3; row++) {
				        for (int col = 0; col < 3; col++) {
				            buttonList[row][col].setDisable(true);
				        }
				    }
				}
			}
		}
		// Tie Game
		if (theGame.tied()) {
			message.setText("Tie Game!");
			for (int row = 0; row < 3; row++) {
		        for (int col = 0; col < 3; col++) {
		            buttonList[row][col].setDisable(true);
		        }
		    }
		}
	}
	
	@Override
	public void update(Object observable) { 
			char[][] board = theGame.getTicTacToeBoard();
		    for (int row = 0; row < 3; row++) {
		        for (int col = 0; col < 3; col++) {
		            if (board[row][col] == 'X') {
		            	buttonList[row][col].setText("X");
		            }
		            else if (board[row][col] == 'O') {
		            	buttonList[row][col].setText("O");
		            }
		            else {
		            	buttonList[row][col].setText("_");
		            }
		        }
		    }
	}
}