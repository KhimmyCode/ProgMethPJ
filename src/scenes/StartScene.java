package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import application.SceneManager;

public class StartScene {
    private Scene scene;

    public StartScene() {
        Pane root = new Pane();
        Text title = new Text(300, 250, "Press to Start");
        title.setStyle("-fx-font-size: 24px;");

        Button startButton = new Button("Start");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);
        startButton.setOnAction(e -> SceneManager.setScene("level"));

        root.getChildren().addAll(title, startButton);
        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
