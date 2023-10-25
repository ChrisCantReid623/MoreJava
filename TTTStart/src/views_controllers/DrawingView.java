/**
 * One view of a Tic Tac Toe game using images to represent, mouse
 * selected moves.
 * 
 * @author Rick Mercer and Christopher Reid 
 */


package views_controllers;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.OurObserver;
import model.TicTacToeGame;

public class DrawingView extends BorderPane implements OurObserver {
	
	private TicTacToeGame theGame;
	private Image X = new Image("file:images/X.png", false);
	private Image O = new Image("file:images/O.png", false);
	private Canvas canvas = new Canvas (210, 210);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Text message = new Text("Click a Square");
	

	public DrawingView(TicTacToeGame theModel) {
		theGame = theModel;	
		layoutGui();
		registerHanders();
	} 
	
	/**
	 * Initiates GUI.
	 */
	private void layoutGui() {
		this.setCenter(canvas);		
		gc.fillText("Click to make a move", 60, 300);
		drawSquares(gc);
	}
	
	/**
	 * Controls grid lines.
	 */
	private void drawSquares(GraphicsContext gc) {
		gc.strokeLine(0, 0 , 0 ,210);
		gc.strokeLine(210, 0 , 210 ,210);
		gc.strokeLine(0, 0 , 210 ,0);
		gc.strokeLine(0, 210 , 210 ,210);
		
		gc.strokeLine(0, 70 , 210 ,70);
		gc.strokeLine(0, 140 , 210 , 140);
		
		gc.strokeLine(70, 0 , 70 ,210);
		gc.strokeLine(140, 0 , 140 ,210);

		message.setFont(Font.font("verdana", 20));
		BorderPane.setAlignment(message, Pos.CENTER);
		this.setBottom(message);
	}
	
	/**
	 * Controls button listening functionality.
	 */
	private void registerHanders() {
		canvas.setOnMouseClicked(new MousePressed());
	}
	
	/**
	 * Controls placement of images upon mouse click.
	 */
	private class MousePressed implements EventHandler<MouseEvent> {
		double x;
		double y;
		double row;
		double column;
		
		/**
		 * Controls game engine functionality.
		 */
		@Override
		public void handle(MouseEvent event) {
			x = event.getSceneX();
			y = event.getSceneY();
			//System.out.println("X: " + x + " Y: " + y);
			getClickCoordinates(x, y);
			boolean openSpace = theGame.available((int)row, (int)column);
			if (!openSpace) {
				message.setText("Invalid choice");
			} else {
				theGame.humanMove((int)row, (int)column, false);
				message.setText("Click a Square");
				update(theGame);
				checkForWin();
			}
		}
		
		/**
		 * Translates mouse click x and y coordinates into game row and column.
		 */
		private void getClickCoordinates(double x, double y) {
			//Get column
			if ((x >= 22) && (x <= 91)) {
				column = 0;
			} else if ((x > 91) && (x <= 161)) {
				column = 1;
			} else if ((x > 161) && (x <= 231)) {
				column = 2;
			}
			
			//Get row
			if ((y >= 75) && (y <= 143)) {
				row = 0;
			} else if ((y > 143) && (y <= 213)) {
				row = 1;
			} else if ((y > 213) && (y <= 283)) {
				row = 2;
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
						message.setText(playerChar + " won!");
						canvas.setDisable(true);
					}
			}
		}
		// Tie Game
		if (theGame.tied()) {
			message.setText("Tie Game!");
			canvas.setDisable(true);
		}
	}
	
	/**
	 * Updates the image.
	 */
	@Override
	public void update(Object theObserved) {
		char[][] board = theGame.getTicTacToeBoard();
	    for (int row = 0; row < 3; row++) {
	        for (int col = 0; col < 3; col++) {
	            if (board[row][col] == 'X') {
	            	insertImage(row, col , 'X');
	            }
	            if (board[row][col] == 'O') {
	            	insertImage(row, col, 'O');
	            }
	        }
	    }
	}
	
	/**
	 * Inserts player character images into graphic.
	 */
	private void insertImage(int row, int col, char player) {
		int xCor = 0;
		int yCor = 0;
		// Determine column
		if (row == 0) {
			//yCor = 55; 
			yCor = 20;
		} else if (row == 1) {
			//yCor = 126;
			yCor = 88;
		} else if (row == 2) {
			//yCor = 238;
			yCor = 160;
		}
		// Determine column
		if (col == 0) {
			//xCor = 55;
			xCor = 20;
		} else if (col == 1) {
			//xCor = 126;
			xCor = 88;
		} else if (col == 2) {
			//xCor = 196;
			xCor = 160;
		}
		
		if (player == 'X') {
			gc.drawImage(X, xCor, yCor);
		} else if (player == 'O') {
			gc.drawImage(O, xCor, yCor);
		}
	}
}