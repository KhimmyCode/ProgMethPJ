package scenes;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import application.SceneManager;

public class LevelSelectScene {
    private Scene scene;

    public LevelSelectScene() {
        Pane root = new Pane();
        
        // Background Image
        Image bgImage = new Image("/background/startscene.jpg");
        ImageView image = new ImageView(bgImage);
        image.setFitWidth(800);
        image.setFitHeight(600);
        
        // Title Text
        Text title = new Text(150, 165, "Select a Level");
        SceneManager s = new SceneManager();
        Font pixelFont65 = s.loadFont("/fonts/pixel2.ttf", 65);
        title.setFont(pixelFont65);
        title.setFill(Color.WHITE);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(2);

        // Load button background image
        Image btnImage = new Image("/background/levelbutton.jpg");

        // Load font
        Font pixelFont = s.loadFont("/fonts/pixel2.ttf", 35);

        // Create Buttons
        Button easyButton = createButton("Easy", btnImage, pixelFont, 270, 240, 1);
        Button mediumButton = createButton("Medium", btnImage, pixelFont, 270, 315, 2);
        Button hardButton = createButton("Hard", btnImage, pixelFont, 270, 390, 3);
        Button exitButton = createButton("Exit", btnImage, pixelFont, 270, 465,0);
        exitButton.setOnAction(e -> Platform.exit()); 


        // Add elements to root
        root.getChildren().addAll(image, title, easyButton, mediumButton, hardButton,exitButton);
        scene = new Scene(root, 800, 600);
    }

    private Button createButton(String text, Image img, Font font, double x, double y, int level) {
        ImageView btnImg = new ImageView(img);
        btnImg.setFitWidth(250);
        btnImg.setFitHeight(60);

        Text buttonText = new Text(text);
        buttonText.setFont(font);
        buttonText.setFill(Color.WHITE);
        buttonText.setStroke(Color.BLACK);
        buttonText.setStrokeWidth(1);

        StackPane btnContent = new StackPane(btnImg, buttonText);
        Button button = new Button();
        button.setGraphic(btnContent);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle("-fx-background-color: transparent;"); // Remove default button styling

        button.setOnAction(e -> SceneManager.loadGameScene(level));

        return button;
    }

    public Scene getScene() {
        return scene;
    }
}
