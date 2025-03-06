package entity.enemies;

import entity.interfaces.Unit;
import javafx.scene.image.Image;

public class Jail extends Unit {

	public Jail(String name, int health) {
		super(name, health, 0, 0, 0, false, 0, 0, 0);
		this.setPos(650);

	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub

	}

}
