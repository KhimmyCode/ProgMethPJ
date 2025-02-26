package scenes;

import java.util.ArrayList;

import entity.interfaces.Unit;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.GameLogic;

public class FieldCanvas extends Canvas {
	
    
    public FieldCanvas(double width, double height) {
        super(width, height);
        updateCanvas(getGraphicsContext2D());
    }

    public void updateCanvas(GraphicsContext gc) {
        Thread t = new Thread(() -> {
            while (!GameLogic.isEnd()) {
                try {
                    Platform.runLater(() -> {
                        // Clear the canvas
                        gc.clearRect(0, 0, getWidth(), getHeight());

                        // Draw background placeholder
                        gc.setFill(Color.LIGHTGREEN);  // Replace with an image later
                        gc.fillRect(0, 0, getWidth(), getHeight());

                        // Draw left base
                        gc.setFill(Color.BLUE);
                        gc.fillRect(20, getHeight() / 2 - 50, 50, 100);

                        // Draw right base
                        gc.setFill(Color.RED);
                        gc.fillRect(getWidth() - 70, getHeight() / 2 - 50, 50, 100);

                        
                        for(Unit u :GameLogic.getInstance().getOurTeamUnits()) {
                        	u.walk();
                        	//u.render;
                        }
                        for(Unit u :GameLogic.getInstance().getEnemyTeamUnits()) {
                        	u.walk();
                        	//u.render;
                        }
                        
                        
                    });

                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
