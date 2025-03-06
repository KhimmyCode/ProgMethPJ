package application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private static MediaPlayer media;
    private static MediaPlayer medialevel = new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/menu.mp3").toString()));
    private static MediaPlayer mediagame = new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/game.mp3").toString()));
    private static MediaPlayer mediawin = new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/win.mp3").toString()));
    private static MediaPlayer medialost = new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/lost.mp3").toString()));

    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Game Project");
        
        media = medialevel;
        System.out.println("Initializing Scene Manager...");
        
        // Load all scenes
        scenes.put("start", new StartScene().getScene());
        scenes.put("level", new LevelSelectScene().getScene());
        scenes.put("win", new WinScene().getScene());
        scenes.put("lost", new LostScene().getScene());
        
        System.out.println("Scenes loaded.");
    }

    public static void setScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            if (media != null) {
                media.stop();
            }

            // ตรวจสอบว่า path ของไฟล์เสียงถูกต้องหรือไม่
            System.out.println("Audio Path for " + sceneName + ": " + ClassLoader.getSystemResource("audio/" + getAudioFileName(sceneName)));

            if (sceneName.equals("level")) {
                media = medialevel;
            } else if (sceneName.equals("lost")) {
                media = medialost;
            } else if (sceneName.equals("win")) {
                media = mediawin;
            }

            media.setVolume(0.08);
            media.setCycleCount(MediaPlayer.INDEFINITE);
            
            // ตรวจสอบว่าเสียงเริ่มเล่นได้หรือไม่
            media.setOnError(() -> {
                System.out.println("Error playing audio: " + media.getError().getMessage());
            });

            media.play();
            
            System.out.println("Audio started playing for " + sceneName);

            primaryStage.setScene(scenes.get(sceneName));
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                GameLogic.getInstance().setEnd(true);
            });
        }
    }

    public static void loadGameScene(int level) {
        media.stop();
        media = mediagame;
        System.out.println("Loading Game Scene, Level: " + level);

        media.setVolume(0.08);
        media.setCycleCount(MediaPlayer.INDEFINITE);
        media.play();

        // ตรวจสอบว่าเสียงเริ่มเล่นได้หรือไม่
        media.setOnError(() -> {
            System.out.println("Error playing audio: " + media.getError().getMessage());
        });

        System.out.println("Audio started playing for game level " + level);

        GameLogic.getInstance().resetGameState();
        // สร้าง GameScene สำหรับระดับที่เลือก
        GameScene gameScene = new GameScene(level);
        scenes.put("game" + level, gameScene.getScene()); // เก็บ Scene ที่ระดับนั้น
        primaryStage.setScene(gameScene.getScene());
        primaryStage.show();
    }

    private static String getAudioFileName(String sceneName) {
        switch (sceneName) {
            case "level":
                return "menu.mp3";
            case "lost":
                return "lost.mp3";
            case "win":
                return "win.mp3";
            default:
                return "game.mp3"; // fallback to game audio
        }
    }

    public Font loadFont(String path, double size) {
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
