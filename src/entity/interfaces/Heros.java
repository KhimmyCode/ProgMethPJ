package entity.interfaces;

import java.util.ArrayList;

public abstract class Heros extends Unit {

	private int cost;
	private double deployTime;

	public Heros(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy, int evasion ,double cooldown ,int cost , double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion , cooldown);
		this.setCost(cost);
		this.setDeployTime(deployTime);
		this.setInitialDeployTime(deployTime);
	}

	@Override
	public void walk() {
		//YOU MUST ADD THIS COMMAND
//		this.setPos(this.getPos() + this.getSpeed()); //Maybe
//		if(this.getPos() + this.getRange() ) {
//			
//		}
	}
	
	public boolean isEnemyInRange(ArrayList<Unit> unitList) {
	    for (Object e : unitList) {
	        if (e instanceof Enemies) {
	            Enemies enemy = (Enemies) e;
	            if (enemy.getPos()-this.getPos()< this.getRange()&&enemy.getPos()>this.getPos()-50) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	public int getCost() {
		return cost;
	} 

	public void setCost(int cost) {
		if(cost < 0) {
			cost = 0 ;
		}
		this.cost = cost;
	}

	public double getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(double deployTime) {
		if(deployTime < 0) {
			deployTime = 0 ;
		}
		this.deployTime = deployTime;
	}
	
	

}
