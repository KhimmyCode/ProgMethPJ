package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Lancer extends Heros implements Attackable {
	
	private Image[] lancerFrames;
	private int currentFrame;
	private long lastFrameTime;

	public Lancer(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
	}

	public Lancer() {
		this("Lancer", 200, 20, 2, 1, true, 150, 15, 3, 200, 15);
		this.lancerFrames = new Image[8];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
            lancerFrames[i] = new Image("/lancer/lancer-walk/lancer-walk" + i + ".png");
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

		gc.drawImage(lancerFrames[currentFrame], this.getPos(), 0, 200, 300);
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
