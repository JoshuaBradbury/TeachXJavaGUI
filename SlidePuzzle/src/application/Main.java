package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	public static final int DIVISIONS = 3;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, 590 + DIVISIONS * 10, 590 + DIVISIONS * 10, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sliding Puzzle 3.0");
		primaryStage.setResizable(false);
		
		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(10);

		Image main = ImageUtil.loadImageByName("KCLTech-3Yrs-Logo.png");

		new Board(pane, main, DIVISIONS);
		
		root.getChildren().add(pane);

		primaryStage.show();
	}
}
