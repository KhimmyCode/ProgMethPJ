package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Buffable;
import entity.interfaces.Heros;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Priest extends Heros implements Buffable {
	private int buffHealth;
	
	private Image[] priestFrames;
	private int currentFrame;
	private long lastFrameTime;

	public Priest(String name, int health, int attackPower, int buffHealth, double speed, double range, boolean isAlley,
			int accuracy, int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
		this.setBuffHealth(buffHealth);
	}

	public Priest() {
		this("Priest", 50, 0, 2, 1, 3, true, 0, 0, 3, 100, 10);   //call Constructor above this code
		this.priestFrames = new Image[8];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
        	priestFrames[i] = new Image("/priest/priest-walk/priest-walk" + i + ".png");
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

		gc.drawImage(priestFrames[currentFrame], this.getPos(), 0, 200, 300);
	}

	@Override
	public void buff(ArrayList<Object> unitList) {
		for (Object e : unitList) {
			if (e instanceof Heros) {
				Heros hero = (Heros) e;
				hero.setHealth(hero.getHealth() + this.getBuffHealth());
			}
		}

	}

	public int getBuffHealth() {
		return buffHealth;
	}

	public void setBuffHealth(int buffHealth) {
		if (buffHealth < 0)
			buffHealth = 0;
		this.buffHealth = buffHealth;
	}

}
