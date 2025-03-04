package scenes;

import entity.heros.Knight;
import entity.heros.Archer;
import entity.heros.Priest;
import entity.heros.Wizard;
import entity.interfaces.Heros;
import entity.heros.Lancer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import logic.CrystalManager;
import application.SceneManager;

public class GameScene {
    private Scene scene;
    private FieldCanvas canvas;
    private GraphicsContext gc;
    private CrystalManager crystalManager;

    private Knight knight = new Knight();
    private Archer archer = new Archer();
    private Priest priest = new Priest();
    private Wizard wizard = new Wizard();
    private Lancer lancer = new Lancer();

    private Button createHeroButton(Heros hero, String imagePath) {
        Image img = new Image(imagePath);
        ImageView imgView = new ImageView(img);
        Image b = new Image("background/border.png");
        ImageView bimg = new ImageView(b);
        imgView.setFitWidth(80);
        imgView.setFitHeight(80);
        bimg.setFitHeight(100);
        bimg.setFitWidth(100);
        StackPane btnContent = new StackPane(bimg,imgView);

        Button button = new Button();
        button.setGraphic(btnContent);
        button.setStyle("-fx-padding: 0;");
        button.setPrefSize(100, 100);
        button.setOnAction(e -> handleUnitSpawn(hero, button));

        return button;
    }

    private void handleUnitSpawn(Heros hero, Button button) {
        if (crystalManager.getCrystalCount() >= hero.getCost()) {
            if (hero instanceof Knight) {
                crystalManager.spawnKnight();
            } else if (hero instanceof Archer) {
                crystalManager.spawnArcher();
            } else if (hero instanceof Priest) {
                crystalManager.spawnPriest();
            } else if (hero instanceof Wizard) {
                crystalManager.spawnWizard();
            } else if (hero instanceof Lancer) {
                crystalManager.spawnLancer();
            }
            hero.setDeployTime(hero.getInitialDeployTime());
            button.setDisable(true);
            System.out.println("Add " + hero.getClass().getSimpleName());

            new Thread(() -> {
                double deployTime = hero.getDeployTime();
                while (deployTime > 0) {
                    try {
                        Thread.sleep(1000);
                        deployTime--;
                        final double timeLeft = deployTime;
//                        Platform.runLater(() -> {
//                            button.setText(hero.getClass().getSimpleName() + " (" + timeLeft + "s)");
//                        });
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                Platform.runLater(() -> {
                    button.setDisable(false);
//                    button.setText("");
//                    Image img = new Image(getHeroImagePath(hero));
//                    ((ImageView) button.getGraphic()).setImage(img);
                });
            }).start();
        } else {
            System.out.println("Not enough crystals for " + hero.getClass().getSimpleName());
        }
    }

    private String getHeroImagePath(Heros hero) {
        if (hero instanceof Knight) return "knight/knightbtn.png";
        if (hero instanceof Archer) return "archer/archerbtn.png";
        if (hero instanceof Priest) return "priest/priestbtn.png";
        if (hero instanceof Wizard) return "wizard/wizardbtn.png";
        if (hero instanceof Lancer) return "lancer/lancerbtn.png";
        return "";
    }

    public GameScene(int level) {
        Pane root = new VBox();

        // Title
        Text title = new Text("Level " + level);
        title.setStyle("-fx-font-size: 24px;");

        // Canvas for game rendering
        canvas = new FieldCanvas(800, 400);
        gc = canvas.getGraphicsContext2D();

        // Crystal manager
        Text crystalText = new Text();
        crystalManager = new CrystalManager(100, crystalText);
        crystalManager.startCrystalCount(); // Start crystal generation every second

        // Button panel
        HBox buttonPanel = new HBox(10);

        Button knightButton = createHeroButton(knight, "knight/knightbtn.png");
        Button archerButton = createHeroButton(archer, "archer/archerbtn.png");
        Button priestButton = createHeroButton(priest, "priest/priestbtn.png");
        Button wizardButton = createHeroButton(wizard, "wizard/wizardbtn.png");
        Button lancerButton = createHeroButton(lancer, "lancer/lancerbtn.png");

        buttonPanel.getChildren().addAll(knightButton, archerButton, priestButton, wizardButton, lancerButton);

        // Upgrade Crystal button
        Button upgradeCrystalButton = new Button("Upgrade Crystal");
        upgradeCrystalButton.setOnAction(e -> crystalManager.upgradeCrystal());
        upgradeCrystalButton.setPrefSize(100, 100);
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
}
