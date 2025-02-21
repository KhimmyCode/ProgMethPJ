package entity.interfaces;

public abstract class Enemies extends Unit{
	
	public Enemies(String name, int health, int attackPower, double speed, double range, boolean isAlley, int accuracy,int evasion , double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion , cooldown);
	}
	
	@Override
	public void walk() {
		
		//YOU MUST ADD COMMAND
		
//		this.setPos(); //Maybe
//		if(this.getPos() + this.getRange() ) {
//			
//		}
	}
	
}
