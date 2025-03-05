package entity.enemies;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Buffable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Werebear extends Enemies implements Attackable, Buffable {
	private int buffPower;
	private Image[] werebearFrames;
	private int currentFrame;
	private long lastFrameTime;
	private boolean taking ;
	

	public Werebear(String name, int health, int attackPower, int buffPower, int speed, double range,
			boolean isAlley, int accuracy, int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
		this.setBuffPower(buffPower);
	}

	public Werebear() {
		super("WereBear", 150, 25, 2, 1, false, 100, 20, 1);
		this.werebearFrames = new Image[6];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		this.setTaking(false);
		
        for (int i = 0; i < 6; i++) {
            werebearFrames[i] = new Image("file:res/werebear/werebear-walk/werebear-walk" + i + ".png");
        }
	}
	
	@Override
	public void walk() {
//		System.out.println("Slime in " + this.getPos());
		
		if(!this.isTaking()) {
		this.setPos(this.getPos() - this.getSpeed());

		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrameTime > 100) {
			currentFrame = (currentFrame + 1) % 6;
			lastFrameTime = currentTime;
		}}

	}
	
	public void render(GraphicsContext gc) {
//		System.out.println("Rendering Knight at position: (" + this.getPos() + ")");

		gc.drawImage(werebearFrames[currentFrame], this.getPos(), 0, 200, 300);
	}


	@Override
	public void buff(ArrayList<Object> unitList) {

		for (Object unit : unitList) {
			if (unit instanceof Enemies) {
				Enemies enemy = (Enemies) unit;
				enemy.setAttackPower(enemy.getAttackPower() + this.getBuffPower());
			}
		}

	}

//	@Override
//	public void attack(ArrayList<Object> unitList) {
//		// TODO Auto-generated method stub
//		for (Object e : unitList) { // ไล่แต่ละตัวใน ArrayList--------|
//			if (e instanceof Heros) {
//				Heros hero = (Heros) e;
//
//				int hitChance = this.getAccuracy() - hero.getEvasion();
//				double successRate = hitChance / 100.0;
//
//				if (Math.random() < successRate) {
//					int takeDamage = hero.getHealth() - this.getAttackPower();
//					if (takeDamage < 0) {
//						hero.setHealth(0);
//					} else {
//						hero.setHealth(takeDamage);
//					}
//					System.out.println(this.getName() + " Attack " + hero.getName() + " remain hp = " + takeDamage);
//				} else {
//					System.out.println(this.getName() + " Attack Miss!");
//				}
//			}
//		}
//
//	}
	@Override
	public void attack(ArrayList<Unit> unitList) {
		for (Object e : unitList) { // ไล่แต่ละตัวใน ArrayList--------|
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
	// Getters Setters
	public int getBuffPower() {
		return buffPower;
	}

	public void setBuffPower(int buffPower) {
		if (buffPower < 0)
			buffPower = 0;
		this.buffPower = buffPower;
	}

	public boolean isTaking() {
		return taking;
	}

	public void setTaking(boolean taking) {
		this.taking = taking;
	}


}
