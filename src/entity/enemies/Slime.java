package entity.enemies;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Regenable;

public class Slime extends Enemies implements Attackable, Regenable {
	private int regenrate = 2;

	public Slime(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);

	}

	public Slime() {
		super("Slime", 30, 10, 1, 1, false, 100, 5, 1);
	}

	@Override
	public void regen() {
		if (this.getHealth() < this.getMaxHealth() - regenrate) {
			this.setHealth(this.getHealth() + regenrate);
		}

	}

	@Override
	public void attack(Object e) {
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
