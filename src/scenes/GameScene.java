package scenes;

import entity.heros.Knight;
import entity.heros.Archer;
import entity.heros.Priest;
import entity.heros.Wizard;
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

    // Create instances for each hero
    private Knight knight = new Knight();
    private Archer archer = new Archer();
    private Priest priest = new Priest();
    private Wizard wizard = new Wizard();
    private Lancer lancer = new Lancer();

    // Cooldown state for each unit
    private boolean knightOnCooldown = false;
    private boolean archerOnCooldown = false;
    private boolean priestOnCooldown = false;
    private boolean wizardOnCooldown = false;
    private boolean lancerOnCooldown = false;

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
        crystalManager.startCrystalCount();  // Start crystal generation every second

        // Button panel
        HBox buttonPanel = new HBox(10);

        // Button for Knight
        Button knightButton = new Button("Spawn Knight");
        knightButton.setOnAction(e -> {
            if (!knightOnCooldown) {
                crystalManager.spawnKnight();
                knightOnCooldown = true;
                knightButton.setDisable(true); // Disable the button
                knight.setCooldown(5); // Reset the cooldown to 5 seconds for knight
                System.out.println("Add Knight");

                // Start cooldown countdown for Knight
                new Thread(() -> {
                    while (knight.getCooldown() > 0) {
                        try {
                            Thread.sleep(1000); // Wait for 1 second
                            knight.setCooldown(knight.getCooldown() - 1); // Decrease cooldown
                            Platform.runLater(() -> {
                                knightButton.setText("Knight (" + knight.getCooldown() + "s)"); // Update button text
                            });
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    knightOnCooldown = false;
                    Platform.runLater(() -> {
                        knightButton.setDisable(false); // Enable the button
                        knightButton.setText("Spawn Knight"); // Reset button text
                    });
                }).start();
            }
        });
        buttonPanel.getChildren().add(knightButton);

        // Button for Archer
        Button archerButton = new Button("Spawn Archer");
        archerButton.setOnAction(e -> {
            if (!archerOnCooldown) {
                crystalManager.spawnArcher();
                archerOnCooldown = true;
                archerButton.setDisable(true); // Disable the button
                archer.setCooldown(6); // Reset the cooldown to 6 seconds for archer
                System.out.println("Add Archer");

                // Start cooldown countdown for Archer
                new Thread(() -> {
                    while (archer.getCooldown() > 0) {
                        try {
                            Thread.sleep(1000); // Wait for 1 second
                            archer.setCooldown(archer.getCooldown() - 1); // Decrease cooldown
                            Platform.runLater(() -> {
                                archerButton.setText("Archer (" + archer.getCooldown() + "s)"); // Update button text
                            });
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    archerOnCooldown = false;
                    Platform.runLater(() -> {
                        archerButton.setDisable(false); // Enable the button
                        archerButton.setText("Spawn Archer"); // Reset button text
                    });
                }).start();
            }
        });
        buttonPanel.getChildren().add(archerButton);

        // Button for Priest
        Button priestButton = new Button("Spawn Priest");
        priestButton.setOnAction(e -> {
            if (!priestOnCooldown) {
                crystalManager.spawnPriest();
                priestOnCooldown = true;
                priestButton.setDisable(true); // Disable the button
                priest.setCooldown(7); // Reset the cooldown to 7 seconds for priest
                System.out.println("Add Priest");

                // Start cooldown countdown for Priest
                new Thread(() -> {
                    while (priest.getCooldown() > 0) {
                        try {
                            Thread.sleep(1000); // Wait for 1 second
                            priest.setCooldown(priest.getCooldown() - 1); // Decrease cooldown
                            Platform.runLater(() -> {
                                priestButton.setText("Priest (" + priest.getCooldown() + "s)"); // Update button text
                            });
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    priestOnCooldown = false;
                    Platform.runLater(() -> {
                        priestButton.setDisable(false); // Enable the button
                        priestButton.setText("Spawn Priest"); // Reset button text
                    });
                }).start();
            }
        });
        buttonPanel.getChildren().add(priestButton);

        // Button for Wizard
        Button wizardButton = new Button("Spawn Wizard");
        wizardButton.setOnAction(e -> {
            if (!wizardOnCooldown) {
                crystalManager.spawnWizard();
                wizardOnCooldown = true;
                wizardButton.setDisable(true); // Disable the button
                wizard.setCooldown(8); // Reset the cooldown to 8 seconds for wizard
                System.out.println("Add Wizard");

                // Start cooldown countdown for Wizard
                new Thread(() -> {
                    while (wizard.getCooldown() > 0) {
                        try {
                            Thread.sleep(1000); // Wait for 1 second
                            wizard.setCooldown(wizard.getCooldown() - 1); // Decrease cooldown
                            Platform.runLater(() -> {
                                wizardButton.setText("Wizard (" + wizard.getCooldown() + "s)"); // Update button text
                            });
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    wizardOnCooldown = false;
                    Platform.runLater(() -> {
                        wizardButton.setDisable(false); // Enable the button
                        wizardButton.setText("Spawn Wizard"); // Reset button text
                    });
                }).start();
            }
        });
        buttonPanel.getChildren().add(wizardButton);

        // Button for Lancer
        Button lancerButton = new Button("Spawn Lancer");
        lancerButton.setOnAction(e -> {
            if (!lancerOnCooldown) {
                crystalManager.spawnLancer();
                lancerOnCooldown = true;
                lancerButton.setDisable(true); // Disable the button
                lancer.setCooldown(9); // Reset the cooldown to 9 seconds for lancer
                System.out.println("Add Lancer");

                // Start cooldown countdown for Lancer
                new Thread(() -> {
                    while (lancer.getCooldown() > 0) {
                        try {
                            Thread.sleep(1000); // Wait for 1 second
                            lancer.setCooldown(lancer.getCooldown() - 1); // Decrease cooldown
                            Platform.runLater(() -> {
                                lancerButton.setText("Lancer (" + lancer.getCooldown() + "s)"); // Update button text
                            });
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    lancerOnCooldown = false;
                    Platform.runLater(() -> {
                        lancerButton.setDisable(false); // Enable the button
                        lancerButton.setText("Spawn Lancer"); // Reset button text
                    });
                }).start();
            }
        });
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
