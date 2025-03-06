package entity.interfaces;

public abstract class Based {
//	private String name;
	private int healthBased;
//	private int maxHealth;
//	private double pos;
//	private boolean isAlley;
	
	
	public Based( int health ) {
		
		this.setHealthBased(health);
		
	}

	


	public int getHealthBased() {
		return healthBased;
	}


	public void setHealthBased(int healthBased) {
		if(healthBased <0) healthBased = 0;
		this.healthBased = healthBased;
	}


	


}
