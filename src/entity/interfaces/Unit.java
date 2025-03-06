package entity.interfaces;

public abstract class Unit {
	private String name;
	private int health;
	private int maxHealth;
	private int attackPower;
	private int speed;
	private double range;
	private boolean isAlley;
	private int accuracy;
	private int evasion;
	private int pos;
	private double cooldown;
	private double initialDeployTime;
	private Action status;

	public Unit(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		this.setName(name);
		this.setHealth(health);
		this.setMaxHealth(health);
		this.setAttackPower(attackPower);
		this.setSpeed(speed);
		this.setRange(range);
		this.setAlley(isAlley);
		this.setAccuracy(accuracy);
		this.setEvasion(evasion);
		this.setCooldown(cooldown);
		this.setStatus(Action.WALK);
		
	}

	public abstract void walk();

	public void Dead() {
		this.status = Action.DEATH;
	}

	public String getName() {
		return name;
	}

	public Action getStatus() {
		return status;
	}

	public void setStatus(Action status) {
		this.status = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health < 0)
			this.health = 0;
		else
			this.health = health;
		if (this.health <= 0) {
			this.Dead();
		}
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		if (attackPower < 0)
			this.attackPower = 0;
		else
			this.attackPower = attackPower;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (speed < 0)
			this.speed = 0;
		else
			this.speed = speed;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		if (range < 0)
			this.range = 0;
		else
			this.range = range;
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
		if (maxHealth < 0)
			this.maxHealth = 0;
		else
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

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	public double getInitialDeployTime() {
		return initialDeployTime;
	}

	public void setInitialDeployTime(double initialDeployTime) {
		this.initialDeployTime = initialDeployTime;
	}


	
	
	
	

}
