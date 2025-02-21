package entity.heros;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;

public class Knight extends Heros implements Attackable {

	public Knight(String name, int health, int attackPower, float speed, float range, boolean isAlley, int accuracy,
			int evasion, float cooldown, int cost, float deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
	}

	public Knight() {
		super("Knight", 100, 10, 1, 1, true, 100, 15, 1, 80, 5);
	}

	@Override
	public void attack(Object e) {
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
