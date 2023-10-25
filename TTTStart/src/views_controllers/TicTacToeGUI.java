/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the Tic Tac Toe game changes:
 * 
 *  1) whenever you make a move by clicking a button or an area of either view
 *  2) whenever the computer AI makes a move
 *  3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author Rick Mercer and Christopher Reid
 */

package views_controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.IntermediateAI;
import model.OurObserver;
import model.RandomAI;
import model.TicTacToeGame;


public class TicTacToeGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private TicTacToeGame theGame;

	MenuBar menuBar = new MenuBar();
	Menu menuOptions = new Menu("Options");
	MenuItem newGame = new MenuItem("New Game");

	Menu strategies = new Menu("Strategies");
	MenuItem randomAI = new MenuItem("RandomAI");
	MenuItem intermediateAI = new MenuItem("Intermediate");

	Menu modes = new Menu("Views");
	MenuItem buttonMode = new MenuItem("Button");
	MenuItem textAreaMode = new MenuItem("TextArea");
	MenuItem drawingMode = new MenuItem("Drawing");
	private String currMode = " ";;

	private OurObserver currentView;
	private OurObserver textAreaView;
	private OurObserver buttonView;
	private OurObserver drawingView;

	private BorderPane window;
	public static final int width = 254;
	public static final int height = 360;
	

	public void start(Stage stage) {
		stage.setTitle("Tic Tac Toe");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		addMenuItems();
		addMenusRegisterListeners();
		initializeGameForTheFirstTime();
		textAreaView = new TextAreaView(theGame);
		drawingView = new DrawingView(theGame);
		buttonView = new ButtonView(theGame);
		theGame.addObserver(textAreaView);
		theGame.addObserver(buttonView);
		theGame.addObserver(drawingView);
		setViewTo(textAreaView);
		currMode = "TextArea";
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Adds menu items to GUI pane.
	 */
	private void addMenuItems() {
		menuOptions.getItems().addAll(newGame, strategies, modes);
		strategies.getItems().addAll(randomAI, intermediateAI);
		modes.getItems().addAll(buttonMode, textAreaMode, drawingMode);
		menuBar.getMenus().addAll(menuOptions);
		window.setTop(menuBar);
	}
	
	/**
	 * Attaches listener functionality to menu items.
	 */
	private void addMenusRegisterListeners() {
		// New Game Button
		newGame.setOnAction(new newGameListener());

		// Strategy buttons
		randomAI.setOnAction(new stratSwitchListener());
		intermediateAI.setOnAction(new stratSwitchListener());

		// Mode switches
		buttonMode.setOnAction(new modeSwitchListener());
		textAreaMode.setOnAction(new modeSwitchListener());
		drawingMode.setOnAction(new modeSwitchListener());
	}
	
	/**
	 * When pressed... starts a new game!
	 */
	private class newGameListener implements EventHandler<ActionEvent> {
		/**
		 * When pressed... starts a new game!
		 */
		@Override
		public void handle(ActionEvent event) {
			//System.out.println(((MenuItem) event.getSource()).getText());
			if (((MenuItem) event.getSource()).getText() == "New Game") {
				theGame = new TicTacToeGame();
				textAreaView = new TextAreaView(theGame);
				drawingView = new DrawingView(theGame);
				buttonView = new ButtonView(theGame);
				theGame.addObserver(textAreaView);
				theGame.addObserver(buttonView);
				theGame.addObserver(drawingView);

				if (currMode == "TextArea") {
					setViewTo(textAreaView);
				} else if (currMode == "Button") {
					setViewTo(buttonView);
				} else if (currMode == "Drawing") {
					setViewTo(drawingView);
				}
			}
		}
	}
	
	/**
	 * When pressed... switches AI difficulty.
	 */
	private class stratSwitchListener implements EventHandler<ActionEvent> {
		/**
		 * When pressed... switches AI difficulty.
		 */
		@Override
		public void handle(ActionEvent event) {
			if (((MenuItem) event.getSource()).getText() == "RandomAI") {
				theGame.setComputerPlayerStrategy(new RandomAI());
			} else if (((MenuItem) event.getSource()).getText() == "Intermediate") {
				theGame.setComputerPlayerStrategy(new IntermediateAI());
			}
		}
	}
	
	/**
	 * When pressed... switches game view.
	 */
	private class modeSwitchListener implements EventHandler<ActionEvent> {
		/**
		 * When pressed... switches game view.
		 */
		@Override
		public void handle(ActionEvent event) {
			if (((MenuItem) event.getSource()).getText() == "Drawing") {
				drawingView.update(theGame);
				currMode = "Drawing";
				setViewTo(drawingView);
			} else if (((MenuItem) event.getSource()).getText() == "TextArea") {
				textAreaView.update(theGame);
				currMode = "TextArea";
				setViewTo(textAreaView);
			} else if (((MenuItem) event.getSource()).getText() == "Button") {
				buttonView.update(theGame);
				currMode = "Button";
				setViewTo(buttonView);
			}
		}
	}
	
	/**
	 * Initializes the game,
	 */
	public void initializeGameForTheFirstTime() {
		theGame = new TicTacToeGame();
		// This event driven program will always have
		// a computer player who takes the second turn
		theGame.setComputerPlayerStrategy(new RandomAI());
	}
	
	/**
	 * Switches the current view of the GUI.
	 */
	public void setViewTo(OurObserver newView) {
		window.setCenter(null);
		currentView = newView;
		window.setCenter((Node) currentView);
	}
}
