package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) {
        SceneManager.initialize(primaryStage);
        SceneManager.setScene("start");  // Start with "Press to Start" scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}