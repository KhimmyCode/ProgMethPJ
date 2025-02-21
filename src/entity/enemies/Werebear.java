package entity.enemies;

import entity.interfaces.Attackable;
import entity.interfaces.Buffable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;

public class Werebear extends Enemies implements Attackable , Buffable {

	public Werebear(String name, int health, int attackPower, float speed, float range, boolean isAlley, int accuracy,
			int evasion, float cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
	}
	
	public Werebear() {
		super("Were Bear", 150, 25, 1.2, 1, false, 100, 20, 1);
	}

	@Override
	public void buff() {
//		double inbuffradius = this.getRange() ;
		//YOU MUST ADD COMMAND
		
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
