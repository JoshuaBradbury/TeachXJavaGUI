package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World");
		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setScene(scene);

		Text text = new Text("Username: ");
		Text text2 = new Text("Password: ");

		pane.add(text, 0, 0);
		pane.add(text2, 0, 1);

		TextField textField = new TextField();
		pane.add(textField, 1, 0);

		PasswordField password = new PasswordField();
		pane.add(password, 1, 1);

		Button button = new Button("Sign in");
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (password.getText().equals("banana")) {
					if (textField.getText().equalsIgnoreCase("apple")) {
						System.out.println("Successfully logged in");
						return;
					}
				}
				System.out.println("Failed log in");
			}
		});

		HBox box = new HBox();
		box.setAlignment(Pos.BOTTOM_RIGHT);
		box.getChildren().add(button);

		pane.add(box, 1, 2);


		primaryStage.show();
	}
}
