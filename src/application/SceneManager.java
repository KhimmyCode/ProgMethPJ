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

        // เราจะไม่สร้าง GameScene ทั้ง 3 ระดับนี้ที่นี่แล้ว แต่จะสร้างเมื่อเข้าไปในแต่ละ Level
    }

    public static void setScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            primaryStage.setScene(scenes.get(sceneName));
            primaryStage.show();
        }
    }

    public static void loadGameScene(int level) {
        // สร้าง GameScene สำหรับระดับที่เลือก
        GameScene gameScene = new GameScene(level); 
        scenes.put("game" + level, gameScene.getScene()); // เก็บ Scene ที่ระดับนั้น
        primaryStage.setScene(gameScene.getScene());
        primaryStage.show();
    }
}
