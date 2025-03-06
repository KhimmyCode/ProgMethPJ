package scenes;

import entity.heros.Knight;
import entity.enemies.Jail;
import entity.heros.Archer;
import entity.heros.Priest;
import entity.heros.Wizard;
import entity.heros.Lancer;
import entity.heros.Castle;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import difficulty.BaseDifficulty;
import difficulty.EasyDifficulty;
import difficulty.MediumDifficulty;
import difficulty.HardDifficulty;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.CrystalManager;
import logic.GameLogic;

import java.util.ArrayList;
import java.util.List;

import application.SceneManager;

public class GameScene {
	private Scene scene;
	private FieldCanvas canvas;
	private GraphicsContext gc;
	private CrystalManager crystalManager;
	private static GameScene instance;
	private Castle castle; // ตัวแปร Castle
	private int enemyCount; // จำนวนศัตรูในด่านนี้
	private BaseDifficulty difficulty;

	private Knight knight = new Knight();
	private Archer archer = new Archer();
	private Priest priest = new Priest();
	private Wizard wizard = new Wizard();
	private Lancer lancer = new Lancer();

	// สร้างปุ่มฮีโร่
	private Button createHeroButton(Heros hero, String imagePath) {
        Image img = new Image(ClassLoader.getSystemResource(imagePath).toString());
        ImageView imgView = new ImageView(img);
        Image b = new Image(ClassLoader.getSystemResource("background/border.png").toString());
//        new Image(ClassLoader.getSystemResource("background/border.png").toString());
        ImageView bimg = new ImageView(b);
        imgView.setFitWidth(85);
        imgView.setFitHeight(85);
        bimg.setFitHeight(120);
        bimg.setFitWidth(120);
        Rectangle bgRect = new Rectangle(80, 80);
        bgRect.setFill(Color.SADDLEBROWN); // Change color as neede
        
        
        Text text = new Text(String.valueOf(hero.getCost()));
        text.setFill(Color.YELLOW); // Set the color of the text (white here)
        text.setStyle(" -fx-font-weight: bold; -fx-stroke: black; -fx-stroke-width: 1;");
        SceneManager smanager = new SceneManager();
        Font pixelFont = smanager.loadFont("/fonts/pixel2.ttf", 25);
        
        text.setFont(pixelFont);

        text.setTextAlignment(TextAlignment.CENTER);
        
        StackPane btnContent = new StackPane(bgRect,imgView,bimg ,text);
        btnContent.setAlignment(text, Pos.BASELINE_CENTER); 
        

        Button button = new Button();
        button.setGraphic(btnContent);
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
        button.setPrefSize(100, 100);
        button.setOnAction(e -> handleUnitSpawn(hero, button));

        return button;
    }

	// จัดการสปาวน์ยูนิต
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
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				Platform.runLater(() -> button.setDisable(false));
			}).start();
		} else {
			System.out.println("Not enough crystals for " + hero.getClass().getSimpleName());
		}
	}

	// ฟังก์ชันสร้าง Castle และตั้งค่า Enemy Count ตามระดับความยาก
	public GameScene(int level) {
		Pane root = new VBox();

		// Title
		BorderPane header = new BorderPane();
		Image btn = new Image(ClassLoader.getSystemResource("background/levelbutton.jpg").toString());
		ImageView btnImg = new ImageView(btn);
        btnImg.setFitWidth(250*0.8);
        btnImg.setFitHeight(60*0.8);
        SceneManager smanager = new SceneManager();
        Font pixelFont = smanager.loadFont("/fonts/pixel2.ttf", 20);

        Text buttonText = new Text("Back to Menu");
        buttonText.setFont(pixelFont);
        buttonText.setFill(Color.YELLOW);
        buttonText.setStroke(Color.BLACK);
        buttonText.setStrokeWidth(0.8);
        StackPane btnContent = new StackPane(btnImg, buttonText);
        Button exitButton = new Button();
        exitButton.setGraphic(btnContent);
        exitButton.setStyle("-fx-background-color: transparent;");

		Text title = new Text("Level " + level);
		title.setStyle("-fx-font-size: 24px;");
		header.setLeft(title);
		header.setRight(exitButton);
		Text crystalText = new Text();
		header.setCenter(crystalText);
		header.setStyle("-fx-background-color: #7EC4C1;");

		// Canvas for game rendering
		canvas = new FieldCanvas(800, 400 ,this);
		gc = canvas.getGraphicsContext2D();

		// Crystal manager
		crystalManager = new CrystalManager(500, crystalText);
		crystalManager.startCrystalCount();

		// Set Castle and enemy count based on difficulty
		switch (level) {
		case 1:
			difficulty = new EasyDifficulty();
			difficulty.applyDifficultySettings();
			break;
		case 2:
			difficulty = new MediumDifficulty();
			difficulty.applyDifficultySettings();
			break;
		case 3:
			difficulty = new HardDifficulty();
			difficulty.applyDifficultySettings();
			break;
		default:
			throw new IllegalArgumentException("Invalid level: " + level);
		}
		exitButton.setOnAction(e -> {SceneManager.setScene("level");
		GameLogic.getInstance().setEnd(true) ;
		
		});

	
		// Button panel
		HBox buttonPanel = new HBox(10);
		Button knightButton = createHeroButton(knight, "knight/knightbtn.png");
		Button archerButton = createHeroButton(archer, "archer/archerbtn.png");
		Button priestButton = createHeroButton(priest, "priest/priestbtn.png");
		Button wizardButton = createHeroButton(wizard, "wizard/wizardbtn.png");
		Button lancerButton = createHeroButton(lancer, "lancer/lancerbtn.png");

		buttonPanel.getChildren().addAll(knightButton, archerButton, priestButton, wizardButton, lancerButton);

		// Upgrade Crystal button
		Button upgradeCrystalButton = new Button();
        upgradeCrystalButton.setOnAction(e -> crystalManager.upgradeCrystal());
        upgradeCrystalButton.setPrefSize(100, 100);
        upgradeCrystalButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
        Image b = new Image(ClassLoader.getSystemResource("background/border.png").toString());
        
        ImageView bimg = new ImageView(b);
        bimg.setFitHeight(120);
        bimg.setFitWidth(120);
        Image c = new Image(ClassLoader.getSystemResource("background/coin.jpg").toString());
        ImageView cimg= new ImageView(c);
        cimg.setFitHeight(75);
        cimg.setFitWidth(75);
        StackPane s = new StackPane(cimg,bimg);
        upgradeCrystalButton.setGraphic(s);
        
        
        buttonPanel.getChildren().addAll(upgradeCrystalButton);
        buttonPanel.setStyle("-fx-background-image: url('background/panelbg.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-repeat: no-repeat;");
        buttonPanel.setPrefHeight(300);


        // Exit button
        

        // Add everything to root
        root.getChildren().addAll(header, canvas, buttonPanel);
		scene = new Scene(root, 800, 600);

		Jail j = new Jail("jail",difficulty.getBaseHealth());
		GameLogic.getInstance().addUnitToEnemyTeam(j);
		Castle castle = new Castle("castle",100);
		GameLogic.getInstance().addUnitToOurTeam(castle);
		
		
		difficulty.spawnEnemies().forEach(enemy -> {
			System.out.println("Spawned enemy: " + enemy.getName());

		});
		

	}

	public BaseDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(BaseDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Scene getScene() {
		return scene;
	}


}
