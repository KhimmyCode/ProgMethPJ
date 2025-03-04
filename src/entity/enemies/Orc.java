package entity.enemies;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Orc extends Enemies implements Attackable {
	private Image[] orcFrames;
	private int currentFrame;
	private long lastFrameTime;

	public Orc(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
	}
	
	public Orc() {
		super("Orc", 100, 15, 1.2, 2, false, 110, 15, 1);
		this.orcFrames = new Image[6];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		
        for (int i = 0; i < 6; i++) {
            orcFrames[i] = new Image("file:res/orc/orc-walk/orc-walk" + i + ".png");
        }
	}
	
	@Override
	public void walk() {
		this.setPos(this.getPos() - this.getSpeed());

		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrameTime > 100) {
			currentFrame = (currentFrame + 1) % 6;
			lastFrameTime = currentTime;
		}

	}
	
	public void render(GraphicsContext gc) {
//		System.out.println("Rendering Knight at position: (" + this.getPos() + ")");

		gc.drawImage(orcFrames[currentFrame], this.getPos(), 0, 200, 300);
	}


	@Override
	public void attack(ArrayList<Object> unitList) {
		for(Object e : unitList ) {
			if (e instanceof Heros) {
				Heros hero = (Heros) e;

				int hitChance = this.getAccuracy() - hero.getEvasion();
				double successRate = hitChance / 100.0;

				if (Math.random() < successRate) {
					int takeDamage = hero.getHealth() - this.getAttackPower();
					if (takeDamage < 0) {
						hero.setHealth(0);
					} else {
						hero.setHealth(takeDamage);
					}
					System.out.println(this.getName() + " Attack " + hero.getName() + " remain hp = " + takeDamage);
				} else {
					System.out.println(this.getName() + " Attack Miss!");
				}
			}
		}

		
	}

}
