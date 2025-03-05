package scenes;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class WinScene {
	private Scene scene;
	
	public WinScene() {
		Pane root = new Pane();
		Image bg = new Image("background/startscene");
		ImageView bgimg= new ImageView(bg);
		bgimg.setFitWidth(800);
		bgimg.setFitHeight(600);
		
		
		
		
		
		
		root.getChildren().add(bgimg);
		scene = new Scene(root,800,600);
		
	}

	public Scene getScene() {
		return scene;
	}
}
