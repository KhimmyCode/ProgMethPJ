package scenes;

import java.util.ArrayList;

import entity.enemies.Orc;
import entity.enemies.Slime;
import entity.enemies.Werebear;
import entity.heros.Archer;
import entity.heros.Knight;
import entity.heros.Lancer;
import entity.heros.Priest;
import entity.heros.Wizard;
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

	                    // Draw and update our team's units
	                    Knight k = null;
	                    Archer a = null;
	                    Lancer l = null;
	                    Priest p = null;
	                    Wizard w = null;
	                    Slime s = null ;
	                    Orc o = null ;
	                    Werebear wb = null ;

	                    for (Unit u : GameLogic.getInstance().getOurTeamUnits()) {
	                        if (u instanceof Knight) {
	                            k = (Knight) u;
	                            if(k.isEnemyInRange((GameLogic.getInstance().getUnitInFiled()))) {
	                            	k.attack(GameLogic.getInstance().getUnitInFiled()) ;
	                            	k.renderAttacking(gc);
	                            } else {
	                            	k.walk();
	                            	k.renderWalk(gc); }
	                        } else if (u instanceof Archer) {
	                            a = (Archer) u;
		                        a.walk();
		                        a.render(gc);
	                        } else if (u instanceof Lancer) {
	                            l = (Lancer) u;
		                        l.walk();
		                        l.render(gc);
	                        } else if (u instanceof Priest) {
	                            p = (Priest) u;
		                        p.walk();
		                        p.render(gc);
	                        } else if (u instanceof Wizard) {
	                            w = (Wizard) u;
		                        w.walk();
		                        w.render(gc);
	                        }

	                        

	                    }

	                    // Draw and update enemy team's units
	                    for (Unit e : GameLogic.getInstance().getEnemyTeamUnits()) {
//	                    	if(e instanceof Slime) {
//	                            s = (Slime) e;
//		                        s.walk();
//		                        s.render(gc);
//	                    	}else if(e instanceof Orc) {
//	                            o = (Orc) e;
//		                        o.walk();
//		                        o.render(gc);                    		
//	                    	}else if(e instanceof Werebear) {
//	                    		wb = (Werebear) e;
//	                    		wb.walk();
//	                    		wb.render(gc);
//	                    	}
	                    	if(e instanceof Werebear) {
	                            wb = (Werebear) e;
		                        wb.walk();
		                        wb.render(gc);
	                    	}else if(e instanceof Orc) {
	                            o = (Orc) e;
		                        o.walk();
		                        o.render(gc);                    		
	                    	}else if(e instanceof Slime) {
	                    		s = (Slime) e;
	                    		s.walk();
	                    		s.render(gc);
	                    	}
	                    }

	                });

	                Thread.sleep(50);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	    t.start();
	}
}
