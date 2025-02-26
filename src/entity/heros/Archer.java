package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;

public class Archer extends Heros implements Attackable {

	public Archer(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
		// TODO Auto-generated constructor stub
	}

	public Archer() {
		super("Archer", 75, 15, 1, 3, true, 80, 5, 0.85, 90, 10);
		// super(name,health,atkPower,spd,range,isAlley,accuacy,evasion,cooldown,cost,deploytime)
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
