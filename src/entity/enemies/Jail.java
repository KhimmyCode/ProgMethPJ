package entity.enemies;

import entity.interfaces.Based;
import javafx.scene.image.Image;

public class Jail extends Based {
	private Image newJail;
	private Image brokenJail;
	private Image currentFrame;

	
	public Jail(String name, int health, boolean isAlley) {
		super(name, health, isAlley);
		this.currentFrame = newJail;
	}

	
	public boolean checkDead() {
		if (this.getHealthBased() <= 0) {
			this.currentFrame = brokenJail;
			return true;
		} else
			return false;
	}

}
