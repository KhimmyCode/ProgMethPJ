package application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.GameScene;
import scenes.LevelSelectScene;
import scenes.StartScene;

import java.util.HashMap;

public class SceneManager {
    private static Stage primaryStage;
    private static HashMap<String, Scene> scenes = new HashMap<>();

    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Game Project");

        // Load all scenes
        scenes.put("start", new StartScene().getScene());
        scenes.put("level", new LevelSelectScene().getScene());
        scenes.put("game1", new GameScene(1).getScene());
        scenes.put("game2", new GameScene(2).getScene());
        scenes.put("game3", new GameScene(3).getScene());
    }

    public static void setScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            primaryStage.setScene(scenes.get(sceneName));
            primaryStage.show();
        }
    }
}
