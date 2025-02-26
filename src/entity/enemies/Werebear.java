package entity.enemies;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Buffable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;

public class Werebear extends Enemies implements Attackable, Buffable {
	private int buffPower;

	public Werebear(String name, int health, int attackPower, int buffPower, double speed, double range,
			boolean isAlley, int accuracy, int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
		this.setBuffPower(buffPower);
	}

	public Werebear() {
		super("WereBear", 150, 25, 2, 1, false, 100, 20, 1);
	}

	@Override
	public void buff(ArrayList<Object> unitList) {

		for (Object unit : unitList) {
			if (unit instanceof Enemies) {
				Enemies enemy = (Enemies) unit;
				enemy.setAttackPower(enemy.getAttackPower() + this.getBuffPower());
			}
		}

	}

	@Override
	public void attack(ArrayList<Object> unitList) {
		// TODO Auto-generated method stub
		for (Object e : unitList) { // ไล่แต่ละตัวใน ArrayList--------|
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

	// Getters Setters
	public int getBuffPower() {
		return buffPower;
	}

	public void setBuffPower(int buffPower) {
		if (buffPower < 0)
			buffPower = 0;
		this.buffPower = buffPower;
	}
}
