package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Archer extends Heros implements Attackable {
	
	private Image[] archerFrames;
	private int currentFrame;
	private long lastFrameTime;

	public Archer() {
		super("Archer", 75, 15, 1, 3, true, 80, 5, 0.85, 90, 10);
		this.archerFrames = new Image[8];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
            archerFrames[i] = new Image("/archer/archer-walk/archer-walk" + i + ".png");
        }
		
	}
	
	
	
	@Override
	public void walk() {
		this.setPos(this.getPos() + this.getSpeed());

		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrameTime > 100) {
			currentFrame = (currentFrame + 1) % 8;
			lastFrameTime = currentTime;
		}

	}
	
	public void render(GraphicsContext gc) {
//		System.out.println("Rendering Knight at position: (" + this.getPos() + ")");

		gc.drawImage(archerFrames[currentFrame], this.getPos(), 0, 200, 300);
	}

	@Override
	public void attack(ArrayList<Object> unitList) {
		for (Object e : unitList) {
			if (e instanceof Enemies) {
				Enemies enemy = (Enemies) e;

				int hitChance = this.getAccuracy() - enemy.getEvasion();
				double successRate = hitChance / 100.0;

				if (Math.random() < successRate) {

					int takeDamage = enemy.getHealth() - this.getAttackPower();
					if (takeDamage < 0) {
						enemy.setHealth(0);
					} else {
						enemy.setHealth(takeDamage);
					}
					System.out.println(this.getName() + " Attack " + enemy.getName() + " remain hp = " + takeDamage);
				} else {
					System.out.println(this.getName() + " Attack Miss!");
				}
			}
		}

	}

}
