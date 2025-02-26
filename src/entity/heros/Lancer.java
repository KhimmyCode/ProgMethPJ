package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;

public class Lancer extends Heros implements Attackable {

	public Lancer(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
	}

	public Lancer() {
		this("Lancer", 200, 20, 2, 1, true, 150, 15, 3, 200, 15);
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
