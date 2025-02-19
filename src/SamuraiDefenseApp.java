import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class SamuraiDefenseApp extends Application {
    private static final int COLS = 9; // 9 Columns
    private static final int ROWS = 5; // 5 Rows
    private static final int CELL_SIZE = 80; // Cell size in pixels
    private static final String[] UNITS = {"Samurai", "Archer", "Monk"}; // Available Units
    private String selectedUnit = "Samurai"; // Default unit selection
    private Map<String, Image> unitImages = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Samurai Defense");
        showTitleScreen(primaryStage);
    }

    /** TITLE SCREEN **/
    private void showTitleScreen(Stage primaryStage) {
        VBox menu = new VBox(10);
        menu.setAlignment(Pos.CENTER);
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> showStageSelect(primaryStage));

        menu.getChildren().add(startButton);
        Scene scene = new Scene(menu, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** STAGE SELECTION **/
    private void showStageSelect(Stage primaryStage) {
        VBox levelMenu = new VBox(10);
        levelMenu.setAlignment(Pos.CENTER);
        
        for (int i = 1; i <= 3; i++) {
            int level = i;
            Button levelButton = new Button("Stage " + level);
            levelButton.setOnAction(e -> startGame(primaryStage, level));
            levelMenu.getChildren().add(levelButton);
        }

        Scene scene = new Scene(levelMenu, 720, 480);
        primaryStage.setScene(scene);
    }

    /** START GAME: GRID + UI **/
    private void startGame(Stage primaryStage, int stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // Load unit images
        loadUnitImages();

        // Create Grid Cells
        Rectangle[][] cells = new Rectangle[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.BLACK);

                int finalRow = row;
                int finalCol = col;
                cell.setOnMouseClicked(e -> placeUnit(grid, cells, finalRow, finalCol));

                cells[row][col] = cell;
                grid.add(cell, col, row);
            }
        }

        // Create UI for selecting units
        HBox unitSelection = new HBox(10);
        unitSelection.setAlignment(Pos.CENTER);

        for (String unit : UNITS) {
            Button unitButton = new Button(unit);
            unitButton.setOnAction(e -> selectedUnit = unit);
            unitSelection.getChildren().add(unitButton);
        }

        // Create layout combining UI and game board
        VBox gameLayout = new VBox(10, unitSelection, grid);
        gameLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gameLayout, COLS * CELL_SIZE, ROWS * CELL_SIZE + 50);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start enemy movement
        spawnEnemy(grid);
    }

    /** PLACE UNIT ON GRID **/
    private void placeUnit(GridPane grid, Rectangle[][] cells, int row, int col) {
        System.out.println("Placed " + selectedUnit + " at: " + row + ", " + col);

        // Replace rectangle with unit image
        ImageView unitView = new ImageView(unitImages.get(selectedUnit));
        unitView.setFitWidth(CELL_SIZE);
        unitView.setFitHeight(CELL_SIZE);
        grid.add(unitView, col, row);
    }

    /** LOAD UNIT IMAGES **/
    private void loadUnitImages() {
        unitImages.put("Samurai", new Image("https://upload.wikimedia.org/wikipedia/commons/6/6e/Samurai_in_armor.jpg"));
        unitImages.put("Archer", new Image("https://upload.wikimedia.org/wikipedia/commons/8/8e/Yabusame_archer_2.jpg"));
        unitImages.put("Monk", new Image("https://upload.wikimedia.org/wikipedia/commons/1/1c/Japanese_Monk.jpg"));
    }

    /** SPAWN ENEMY (MOVEMENT) **/
    private void spawnEnemy(GridPane grid) {
        Image enemyImage = new Image("https://upload.wikimedia.org/wikipedia/commons/3/3e/Oni_illustration.jpg");
        ImageView enemyView = new ImageView(enemyImage);
        enemyView.setFitWidth(CELL_SIZE);
        enemyView.setFitHeight(CELL_SIZE);

        int row = (int) (Math.random() * ROWS); // Random row
        grid.add(enemyView, COLS - 1, row);

        // Move enemy from right to left
        TranslateTransition enemyMove = new TranslateTransition(Duration.seconds(5), enemyView);
        enemyMove.setByX(-CELL_SIZE * (COLS - 1));
        enemyMove.setOnFinished(e -> grid.getChildren().remove(enemyView)); // Remove when done
        enemyMove.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
