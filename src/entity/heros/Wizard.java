package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Buffable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wizard extends Heros implements Attackable, Buffable {
	
	private Image[] wizardFrames;
	private int currentFrame;
	private long lastFrameTime;
	private int buffPower;
	public Wizard(String name, int health, int attackPower,int buffPower, int speed, double range, boolean isAlley, int accuracy,int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
		this.setBuffPower(buffPower);
	}
	
	public Wizard() {
		this("Wizard",75,6,1,1,5,true,200,0,0.85,110,15);
	  //super(name,health,atkPower,spd,range,isAlley,accuacy,evasion,cooldown,cost,deploytime)
		this.wizardFrames = new Image[8];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
        	wizardFrames[i] = new Image("/wizard/wizard-walk/wizard-walk" + i + ".png");
        }
	}
	
	
	
	
	
	@Override
	public void walk() {
		this.setPos(this.getPos() + this.getSpeed());

		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrameTime > 100) {
			currentFrame = (currentFrame + 1) % 8;
			lastFrameTime = currentTime;
		}

	}
	
	public void render(GraphicsContext gc) {
//		System.out.println("Rendering Knight at position: (" + this.getPos() + ")");

		gc.drawImage(wizardFrames[currentFrame], this.getPos(), 147, 200, 300);
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
	public void attack(ArrayList<Unit> unitList) {
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
