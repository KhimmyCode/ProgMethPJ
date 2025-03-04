package entity.interfaces;

public abstract class Based {
	private String name;
	private int healthBased;
	private int maxHealth;
	private double pos;
	private boolean isAlley;
	
	
	public Based(String name , int health , boolean isAlley) {
		this.setName(name);
		this.setHealthBased(health);
		this.setMaxHealth(this.getHealthBased());
		this.setAlley(isAlley);
		this.setPosition();
	}

	
	public void setPosition() {    // set position ตาม isAlley 
	    this.editPos(this.isAlley() ? 0 : 100);
	}

	
	//Getters & Setters
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getHealthBased() {
		return healthBased;
	}


	public void setHealthBased(int healthBased) {
		if(healthBased <0) healthBased = 0;
		this.healthBased = healthBased;
	}


	public int getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(int maxHealth) {
		if(maxHealth < 0) maxHealth = 0;
		this.maxHealth = maxHealth;
	}


	public double getPos() {
		return pos;
	}


	public void editPos(double pos) {    // pos เรามีแค่ 0->100 ใช่ไหม ????
		if(pos < 0) pos = 0;
		else if(pos >100) pos = 100;
		this.pos = pos;
	}



	public boolean isAlley() {
		return isAlley;
	}



	public void setAlley(boolean isAlley) {
		this.isAlley = isAlley;
	}
	
	
	
}
