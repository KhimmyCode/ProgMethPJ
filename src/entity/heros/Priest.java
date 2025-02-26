package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Buffable;
import entity.interfaces.Heros;

public class Priest extends Heros implements Buffable {
	private int buffHealth;

	public Priest(String name, int health, int attackPower, int buffHealth, double speed, double range, boolean isAlley,
			int accuracy, int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
		this.setBuffHealth(buffHealth);
	}

	public Priest() {
		this("Priest", 50, 0, 2, 1, 3, true, 0, 0, 3, 100, 10);   //call Constructor above this code
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
