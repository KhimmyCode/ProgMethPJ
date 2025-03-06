package entity.interfaces;

public abstract class Based {
	private int healthBased;

	public Based(int health) {

		this.setHealthBased(health);

	}

	public int getHealthBased() {
		return healthBased;
	}

	public void setHealthBased(int healthBased) {
		if (healthBased < 0)
			healthBased = 0;
		this.healthBased = healthBased;
	}

}
