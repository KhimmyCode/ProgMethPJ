package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import application.SceneManager;

public class LevelSelectScene {
    private Scene scene;

    public LevelSelectScene() {
        Pane root = new Pane();
        Text title = new Text(300, 150, "Select a Level");
        title.setStyle("-fx-font-size: 24px;");

        Button easyButton = new Button("Easy");
        easyButton.setLayoutX(350);
        easyButton.setLayoutY(250);
        easyButton.setOnAction(e -> {
            // เรียกใช้ SceneManager เพื่อเริ่ม GameScene ระดับ 1
            SceneManager.loadGameScene(1);
        });

        Button mediumButton = new Button("Medium");
        mediumButton.setLayoutX(350);
        mediumButton.setLayoutY(300);
        mediumButton.setOnAction(e -> {
            // เรียกใช้ SceneManager เพื่อเริ่ม GameScene ระดับ 2
            SceneManager.loadGameScene(2);
        });

        Button hardButton = new Button("Hard");
        hardButton.setLayoutX(350);
        hardButton.setLayoutY(350);
        hardButton.setOnAction(e -> {
            // เรียกใช้ SceneManager เพื่อเริ่ม GameScene ระดับ 3
            SceneManager.loadGameScene(3);
        });

        root.getChildren().addAll(title, easyButton, mediumButton, hardButton);
        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
