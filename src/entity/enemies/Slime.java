package entity.enemies;

import java.lang.reflect.Array;
import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Regenable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Slime extends Enemies implements Attackable, Regenable {
	private int regenrate = 2;
	private Image[] slimeFrames;
	private int currentFrame;
	private long lastFrameTime;

	public Slime(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);

	}

	public Slime() {
		super("Slime", 30, 10, 1, 1, false, 100, 5, 1);
		this.slimeFrames = new Image[6];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		
        for (int i = 0; i < 6; i++) {
            slimeFrames[i] = new Image("file:res/slime/slime-walk/slime-walk" + i + ".png");
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

		gc.drawImage(slimeFrames[currentFrame], this.getPos(), 0, 200, 300);
	}

	@Override
	public void regen() {
		if (this.getHealth() < this.getMaxHealth() - regenrate) {
			this.setHealth(this.getHealth() + regenrate);
		}

	}

	@Override
	public void attack(ArrayList<Object> unitList) {
		for (Object e : unitList) {
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
