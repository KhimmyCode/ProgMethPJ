package entity.interfaces;

public abstract class Unit {
	//fields & variables
	private String name;
	private int health;
	private int maxHealth;
	private int attackPower;
	private int speed;
	private int range;
	private boolean isAlley;
	private int accuracy;
	private int evasion;
	private int pos;
	private int cooldown;
	//pos -> this field tell us that where the unit located : if unit is alley -> start at 0 , else start at 100
	//alley walk from o to 100 , enemy walk from 100 to 0 ; they will found each other at the same position(logic will think later)
	
	// Constructors
	public Unit(String name, int health, int attackPower, int speed, int range, boolean isAlley , int accuracy , int evasion) {
		this.setName(name);
		this.setHealth(health);
		this.setMaxHealth(health);
		this.setAttackPower(attackPower);
		this.setSpeed(speed);
		this.setRange(range);
		this.setAlley(isAlley);
		this.setAccuracy(accuracy);
		this.setEvasion(evasion);
	}
	
	public abstract void walk(); // abstract method 
	

	// Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if(health <0) this.health = 0;
		else this.health = health;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		if(attackPower <0) this.attackPower = 0;
		else this.attackPower = attackPower;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if(speed <0) this.speed = 0;
		else this.speed = speed;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		if(range < 0) this.range = 0; 
		else this.range = range;
	}

	public boolean isAlley() {
		return isAlley;
	}

	public void setAlley(boolean isAlley) {
		this.isAlley = isAlley;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		if(maxHealth <0) this.maxHealth = 0;
		this.maxHealth = maxHealth;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getEvasion() {
		return evasion;
	}

	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	

}
