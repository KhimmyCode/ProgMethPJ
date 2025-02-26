package scenes;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import logic.GameLogic;
import application.SceneManager;

public class GameScene {
    private Scene scene;
    private	FieldCanvas canvas;
    private GraphicsContext gc;
    private int crystalCount = 100;
    private Text crystalText;

    public GameScene(int level) {
        Pane root = new VBox();

        // Title
        Text title = new Text("Level " + level);
        title.setStyle("-fx-font-size: 24px;");

        // Canvas for game rendering
        canvas = new FieldCanvas(800, 400);
        gc = canvas.getGraphicsContext2D();

        // Crystal counter
        crystalText = new Text("Crystals: " + crystalCount);
        
        // Button panel (5 Hero buttons + 1 Crystal Upgrade)
        HBox buttonPanel = new HBox(10);
        
        for (int i = 0; i < 5; i++) {
        	Button heroButton = new Button("Hero " + (i + 1));
            int finalI = i;
            heroButton.setOnAction(e -> spawnHero(finalI));
            buttonPanel.getChildren().add(heroButton);
        }

        Button upgradeCrystalButton = new Button("Upgrade Crystal");
        upgradeCrystalButton.setOnAction(e -> upgradeCrystal());
        buttonPanel.getChildren().add(upgradeCrystalButton);

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> SceneManager.setScene("level"));

        // Add everything to root
        root.getChildren().addAll(title, crystalText, canvas, buttonPanel, exitButton);

        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    private void spawnHero(int heroIndex) {
        if (crystalCount >= 20) {
            crystalCount -= 20;
            updateCrystalText();
            //GameLogic.getInstance().addUnitToEnemyTeam(heroIndex);
        }
    }
    
    private void startCrystalCount() {
        Thread crystalThread = new Thread(() -> {
            while (GameLogic.isEnd()) {
                try {
                    Thread.sleep(1000); // Wait 1 second
                    crystalCount += 5;
                    updateCrystalText();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        crystalThread.setDaemon(true);
        crystalThread.start();
    }

    private void upgradeCrystal() {
        if (crystalCount >= 50) {
            crystalCount -= 50;
            updateCrystalText();
            System.out.println("Crystal rate upgraded!");
        }
    }

    private void updateCrystalText() {
        Platform.runLater(() -> crystalText.setText("Crystals: " + crystalCount));
    }
}
