/******************************************* 
 * ToDoList.java
 * Author: Christopher Reid
 * CSC 335
 * 
 * Simulates a To Do List. 
 * Data can be saved and continued. 
 ********************************************/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ToDoList extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private BorderPane mainPane;
	private VBox topPane;
	private HBox bottomPane;

	private Label label = new Label("Enter a new ToDo");
	private TextField reminderInput = new TextField();
	private Button saveButton = new Button("Save current list");

	private Button topButton = new Button("Top");
	private Button bottomButton = new Button("Bottom");
	private Button raiseButton = new Button("Raise");
	private Button lowerButton = new Button("Lower");
	private Button removeButton = new Button("Remove");
	private ArrayList<Button> buttonList;

	private ListView<String> listView;
	private ObservableList<String> observableList;
	private ArrayList<String> list;

	@Override
	public void start(Stage stage) throws Exception {

		// Application startup alert
		Alert openAlert = new Alert(AlertType.CONFIRMATION);
		openAlert.setHeaderText("Click cancel to start with zero todos");
		openAlert.setContentText("Click OK to from the persistent ToDoList");
		Optional<ButtonType> result = openAlert.showAndWait();

		// clicked OK
		if (result.get() == ButtonType.OK) {
			try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("reminders.ser"))) {
				list = (ArrayList<String>) inFile.readObject();
				inFile.close();
				generateList(stage);
			}
		// clicked Cancel
		} else {
			list = new ArrayList<>();
			generateList(stage);
		}

		// Application Closed
		stage.setOnCloseRequest(event -> {
			
			// Application shutdown alert
			Alert closeAlert = new Alert(AlertType.CONFIRMATION);
			closeAlert.setHeaderText("Click cancel to not save any changes");
			closeAlert.setContentText("To Save the current ToDoList, click OK");
			Optional<ButtonType> save = closeAlert.showAndWait();

			// clicked OK -> changes saved
			if (save.get() == ButtonType.OK) {
				try (ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("reminders.ser"))) {
					outFile.writeObject(list);
					outFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			// clicked Cancel -> changes unsaved
			} else {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	/************************************************************************
	 * Creates the observable objects, list appearing in the GUI and assigns
	 * functionality to buttons.
	 ************************************************************************/
	public void generateList(Stage stage) {
		observableList = FXCollections.observableArrayList(list);
		listView = new ListView<>();
		listView.setItems(observableList);
		layoutGUI();
		registerHandlers();
		// Put the pane in a sized Scene and show the GUI
		Scene scene = new Scene(mainPane, 500, 500);
		stage.setScene(scene);
		// Don't forget to show the running app:
		stage.show();
	}

	/*********************
	 * Controls GUI Layout
	 *********************/
	private void layoutGUI() {
		mainPane = new BorderPane();

		topPane = new VBox();
		topPane.setSpacing(9);
		topPane.setPadding(new Insets(10, 10, 10, 10));
		topPane.getChildren().addAll(label, reminderInput, saveButton);

		configButtonList();
		bottomPane = new HBox();
		bottomPane.setSpacing(9);
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.getChildren().addAll(topButton, bottomButton, raiseButton, lowerButton, removeButton);

		mainPane.setTop(topPane);
		mainPane.setCenter(listView);
		mainPane.setBottom(bottomPane);
	}

	/************************
	 * Controls Button Sizing
	 ************************/
	public void configButtonList() {
		buttonList = new ArrayList<>();
		buttonList.add(topButton);
		buttonList.add(bottomButton);
		buttonList.add(raiseButton);
		buttonList.add(lowerButton);
		buttonList.add(removeButton);
		for (Button button : buttonList) {
			button.setPrefWidth(90);
		}
	}

	/*****************************************************
	 * Sets action handling for text field and all buttons.
	 *****************************************************/
	private void registerHandlers() {
		
		// Text field functionality
		reminderInput.setOnAction(event -> {
			String newReminder = reminderInput.getText();
			if (!newReminder.isEmpty()) {
				list.add(0, newReminder);
				updateObservableList();
				reminderInput.clear();
			}
		});
		
		// Save button functionality
		saveButton.setOnAction(event -> {
			try (ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("reminders.ser"))) {
				outFile.writeObject(list);
				outFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		// Top button functionality
		topButton.setOnAction(event -> {
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				String selectedItem = list.remove(selectedIndex); 
				list.add(0, selectedItem); 
				updateObservableList(); 
				listView.getSelectionModel().select(0);
			}
		});
		
		// Bottom button functionality
		bottomButton.setOnAction(event -> {
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			int lastIndex = list.size() - 1;
			if (selectedIndex >= 0 && selectedIndex < lastIndex) {
				String selectedItem = list.remove(selectedIndex);
				list.add(selectedItem);
				updateObservableList();
				listView.getSelectionModel().select(lastIndex);
			}
		});
		
		// Raise button functionality
		raiseButton.setOnAction(event -> {
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			if (selectedIndex > 0) {
				String selectedItem = list.remove(selectedIndex); // Modify the private ArrayList list
				list.add(selectedIndex - 1, selectedItem); // Modify the private ArrayList list
				updateObservableList(); // Update observableList
				listView.getSelectionModel().select(selectedIndex - 1);
			}
		});
		
		// Lower button functionality
		lowerButton.setOnAction(event -> {
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			int lastIndex = list.size() - 1;
			if (selectedIndex >= 0 && selectedIndex < lastIndex) {
				String selectedItem = list.remove(selectedIndex); // Modify the private ArrayList list
				list.add(selectedIndex + 1, selectedItem); // Modify the private ArrayList list
				updateObservableList(); // Update observableList
				listView.getSelectionModel().select(selectedIndex + 1);
			}
		});
		
		// Remove button functionality
		removeButton.setOnAction(event -> {
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				list.remove(selectedIndex); // Modify the private ArrayList list
				updateObservableList(); // Update observableList
			}
		});

	}

	/***************************************************
	 * Updates observable list object with list contents.
	 ***************************************************/
	private void updateObservableList() {
		observableList = FXCollections.observableArrayList(list);
		listView.setItems(observableList);

	}
}