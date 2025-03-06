package entity.interfaces;

import java.util.ArrayList;

public abstract class Enemies extends Unit {

	public Enemies(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
		this.setPos(640);
	}

	@Override
	public abstract void walk();

	public boolean isHeroInRange(ArrayList<Unit> unitList) {
		for (Object e : unitList) {
			if (e instanceof Heros) {
				Heros hero = (Heros) e;
				if (hero.getPos() - this.getPos() < this.getRange() && hero.getPos() > this.getPos() - 50) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDead() {
		if (getHealth() <= 0) {
			return true;
		}
		return false;
	}
}
