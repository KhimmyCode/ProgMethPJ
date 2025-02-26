package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Buffable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;

public class Wizard extends Heros implements Attackable, Buffable {
	private int buffPower;
	public Wizard(String name, int health, int attackPower,int buffPower, double speed, double range, boolean isAlley, int accuracy,int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
		this.setBuffPower(buffPower);
	}
	
	public Wizard() {
		this("Wizard",75,6,1,1,5,true,200,0,0.85,110,15);
	  //super(name,health,atkPower,spd,range,isAlley,accuacy,evasion,cooldown,cost,deploytime)
	}

	@Override
	public void buff(ArrayList<Object> unitList) {
		for(Object e : unitList) {
			if(e instanceof Heros) {
				Heros hero =(Heros)e;
				hero.setAttackPower(hero.getAttackPower()+this.getBuffPower());
			}
		}

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
	
	
	//Getters & Setters

	public int getBuffPower() {
		return buffPower;
	}

	public void setBuffPower(int buffPower) {
		if(buffPower <0) buffPower = 0;
		this.buffPower = buffPower;
	}
	
	

}
