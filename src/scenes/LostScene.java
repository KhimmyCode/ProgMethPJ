package scenes;

import application.SceneManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LostScene {
	private Scene scene;

	public LostScene() {
		Pane root = new Pane();
		Image bg = new Image(ClassLoader.getSystemResource("background/startscene.jpg").toString());
		
		ImageView bgimg = new ImageView(bg);
		bgimg.setFitWidth(800);
		bgimg.setFitHeight(600);
		Text title = new Text(180, 180, "You Lose!!");
		SceneManager s = new SceneManager();
		Font pixelFont = s.loadFont("fonts/pixel2.ttf", 88);
		
		title.setFont(pixelFont);
		title.setFill(Color.WHITE);
		title.setStroke(Color.BLACK);
		title.setStrokeWidth(2);
		
		
		Image btnImage = new Image(ClassLoader.getSystemResource("background/levelbutton.jpg").toString());
		ImageView btnImg = new ImageView(btnImage);
        btnImg.setFitWidth(250*1.4);
        btnImg.setFitHeight(60*1.4);

        Text buttonText = new Text("Back to Menu");
        Font pixelFont2 = s.loadFont("fonts/pixel2.ttf", 37);
       
        buttonText.setFont(pixelFont2);
        buttonText.setFill(Color.WHITE);
        buttonText.setStroke(Color.BLACK);
        buttonText.setStrokeWidth(1);

        StackPane btnContent = new StackPane(btnImg, buttonText);
        Button button = new Button();
        button.setGraphic(btnContent);
        button.setLayoutX(235);
        button.setLayoutY(270);
        button.setStyle("-fx-background-color: transparent;"); // Remove default button styling

        button.setOnAction(e -> {
        	SceneManager.setScene("level");
        });

		root.getChildren().addAll(bgimg,title,button);
		scene = new Scene(root, 800, 600);

	}

	public Scene getScene() {
		return scene;
	}
}
