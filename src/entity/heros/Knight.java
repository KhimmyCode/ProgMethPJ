package entity.heros;

import java.util.ArrayList;

import entity.enemies.Orc;
import entity.enemies.Slime;
import entity.enemies.Werebear;
import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;

public class Knight extends Heros implements Attackable {

	private Image[] knightFrames;
	private int currentFrame;
	private long lastFrameTime;
	private Image[] knightAttackingFrames ;
	private int currentAttackingFrame;
	private long lastAttackingFrameTime;
	private boolean isAttacking;

	public Knight() {
		// Name, hp , atk , spd , range , team , acc , eva , cool , cost , deploytime
		super("Knight", 100, 10, 1, 20, true, 100, 15, 1, 80, 5);
		this.knightFrames = new Image[8];
		this.knightAttackingFrames = new Image[7];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
            knightFrames[i] = new Image("/knight/knight-walk/knight-walk" + i + ".png");
        }
        for (int i = 0; i < 7; i++) {
            knightAttackingFrames[i] = new Image("/knight/knight-attack/knight-attack" + i + ".png");
        }
	}

	@Override
	public void walk() {
//		System.out.println( "Knight in " + this.getPos());
		this.setPos(this.getPos() + this.getSpeed());
		
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrameTime > 100) {
			currentFrame = (currentFrame + 1) % 8;
			lastFrameTime = currentTime;
		}

	}
	

	public void renderWalk(GraphicsContext gc) {
		
				gc.drawImage(knightFrames[currentFrame], this.getPos(), 147, 200, 300);
	}
	
	public void renderAttacking(GraphicsContext gc) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrameTime > 100) {
			currentAttackingFrame = (currentAttackingFrame + 1) % 7;
			lastAttackingFrameTime = currentTime;
		}
		if(currentAttackingFrame==5&&!isAttacking) {
			attack(GameLogic.getInstance().getUnitInFiled());
			isAttacking = true;
			System.out.println("attack");
		}
		if(currentAttackingFrame==0) {
			isAttacking = false;
		}
//		System.out.println("frame ="+currentAttackingFrame);
		gc.drawImage(knightAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
	}

	@Override
	public void attack(ArrayList<Unit> unitList) {
		
		
		for (Object e : unitList) {

				
				if (e instanceof Enemies && this.getPos() + this.getRange() >= ((Enemies) e).getPos()) {
					Enemies enemy = (Enemies) e;
				if (enemy instanceof Slime) {
					Slime s = (Slime) enemy;
					s.setTaking(true);
				} else if (enemy instanceof Orc) {
					Orc o = (Orc) enemy;
					o.setTaking(true);
				} else if (enemy instanceof Werebear) {
					Werebear wb = (Werebear) enemy;
					wb.setTaking(true);
				}
				int hitChance = this.getAccuracy() - enemy.getEvasion();
				double successRate = hitChance / 100.0;

				if (Math.random() < successRate) {

					int takeDamage = enemy.getHealth() - this.getAttackPower();
					if (takeDamage < 0) {
						enemy.setHealth(0);
					} else {
						enemy.setHealth(takeDamage);
					}
//					System.out.println(this.getName() + " Attack " + enemy.getName() + " remain hp = " + takeDamage);
				} else {
//					System.out.println(this.getName() + " Attack Miss!");
				}
			}
		}
		
	}
	


}
