package scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import application.SceneManager;

public class StartScene {
    private Scene scene;

    public StartScene() {
        Pane root = new Pane();

        // Create BackgroundImage object
        Image bgImage = new Image(ClassLoader.getSystemResource("background/startscene.jpg").toString()); // Make sure the image is in resources
        ImageView image = new ImageView(bgImage);
        image.setFitWidth(800);
        image.setFitHeight(600);

        // "Press to Start" Text
        Text title = new Text("Press to Start");
        Text gamename = new Text(150, 270, "KingDom \nTowerDefense");
        SceneManager s = new SceneManager();
        
        Font pixelFont = s.loadFont("/fonts/pixel2.ttf", 45);  // จาก Remote
        Font pixelFont2 = s.loadFont("/fonts/pixel2.ttf", 80); // จาก Remote
        
        title.setFont(pixelFont); 
        gamename.setFont(pixelFont2);
        gamename.setFill(Color.DARKRED); // Text Color
        gamename.setStroke(Color.ROSYBROWN); // Outline color
        gamename.setTextAlignment(TextAlignment.CENTER);
        title.setFill(Color.BLACK); // Text Color
        title.setStroke(Color.WHITE); // Outline color
        title.setStrokeWidth(1);
        title.setX(230);
        title.setY(500);

        // Click Anywhere to Start
        root.setOnMouseClicked((MouseEvent e) -> SceneManager.setScene("level"));

        root.getChildren().addAll(image, title, gamename);
        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }
}