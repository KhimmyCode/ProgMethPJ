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
import javafx.scene.layout.Pane;
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
						Platform.runLater(() -> {
							button.setText(hero.getClass().getSimpleName() + " (" + timeLeft + "s)");
						});
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				Platform.runLater(() -> {
					button.setDisable(false);
					button.setText("Spawn " + hero.getClass().getSimpleName());
				});
			}).start();
		} else {
			System.out.println("Not enough crystals for " + hero.getClass().getSimpleName());
		}
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

		// Button for Knight
		Button knightButton = new Button("Spawn Knight");
		knightButton.setOnAction(e -> handleUnitSpawn(knight, knightButton));
		buttonPanel.getChildren().add(knightButton);

		// Button for Archer
		Button archerButton = new Button("Spawn Archer");
		archerButton.setOnAction(e -> handleUnitSpawn(archer, archerButton));
		buttonPanel.getChildren().add(archerButton);

		// Button for Priest
		Button priestButton = new Button("Spawn Priest");
		priestButton.setOnAction(e -> handleUnitSpawn(priest, priestButton));
		buttonPanel.getChildren().add(priestButton);

		// Button for Wizard
		Button wizardButton = new Button("Spawn Wizard");
		wizardButton.setOnAction(e -> handleUnitSpawn(wizard, wizardButton));
		buttonPanel.getChildren().add(wizardButton);

		// Button for Lancer
		Button lancerButton = new Button("Spawn Lancer");
		lancerButton.setOnAction(e -> handleUnitSpawn(lancer, lancerButton));
		buttonPanel.getChildren().add(lancerButton);

		// Upgrade Crystal button
		Button upgradeCrystalButton = new Button("Upgrade Crystal");
		upgradeCrystalButton.setOnAction(e -> crystalManager.upgradeCrystal());
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
