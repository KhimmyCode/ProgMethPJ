package application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.GameLogic;
import scenes.GameScene;
import scenes.LevelSelectScene;
import scenes.LostScene;
import scenes.StartScene;
import scenes.WinScene;

import java.io.InputStream;
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
        scenes.put("win", new WinScene().getScene());
        scenes.put("lost", new LostScene().getScene());

       
    }

    public static void setScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            primaryStage.setScene(scenes.get(sceneName));
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
            	Platform.exit();
            	GameLogic.getInstance().setEnd(true);
            });
        }
    }

    public static void loadGameScene(int level) {
    	GameLogic.getInstance().resetGameState();
        // สร้าง GameScene สำหรับระดับที่เลือก
        GameScene gameScene = new GameScene(level); 
        scenes.put("game" + level, gameScene.getScene()); // เก็บ Scene ที่ระดับนั้น
        primaryStage.setScene(gameScene.getScene());
        primaryStage.show();
    }
    
    public   Font loadFont(String path, double size) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                return Font.loadFont(is, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Font.font("Arial", size); // Fallback font
    }
}
