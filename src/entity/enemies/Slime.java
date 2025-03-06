package entity.enemies;

import java.lang.reflect.Array;

import java.util.ArrayList;

import entity.heros.Castle;
import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Regenable;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;
import scenes.GameScene;

public class Slime extends Enemies implements Attackable, Regenable {
	private int regenrate = 2;
	private Image[] slimeFrames;
	private Image[] slimeAttackingFrames;
	private int currentAttackingFrame;
	private long lastAttackingFrameTime;
	private int currentFrame;
	private long lastFrameTime;
	private boolean taking ;
	private boolean isAttacking;

	public Slime(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);

	}

	public Slime() {
		super("Slime", 30, 10, 1, 1, false, 100, 5, 1);
		this.slimeFrames = new Image[6];
		this.slimeAttackingFrames = new Image[7];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		this.setTaking(false);
		isAttacking= false;
		
        for (int i = 0; i < 6; i++) {
            slimeFrames[i] = new Image("/slime/slime-walk/slime-walk" + i + ".png");
        }
        for(int i=0;i<6;i++) {
        	slimeAttackingFrames[i] = new Image("/slime/slime-attack/slime-attack"+i+".png");
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

		gc.drawImage(slimeFrames[currentFrame], this.getPos(), 147, 200, 300);
	}

	@Override
	public void regen() {
		if (this.getHealth() < this.getMaxHealth() - regenrate) {
			this.setHealth(this.getHealth() + regenrate);
		}

	}
	
	public void renderAttacking(GraphicsContext gc) {
		long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackingFrameTime > 200) { // ลดความเร็วในการเปลี่ยนเฟรมโจมตี
            currentAttackingFrame = (currentAttackingFrame + 1) % 6; // โจมตี 12 เฟรม
            lastAttackingFrameTime = currentTime;
        }
        if (currentAttackingFrame == 4&&!isAttacking ) { // โจมตีที่เฟรม 5 (เปลี่ยนเฟรม)
            attack(GameLogic.getInstance().getUnitInFiled());
            isAttacking=true;
            System.out.println("attack");
            
        }if(currentAttackingFrame ==0) {
        	isAttacking = false;
        }
        
        gc.drawImage(slimeAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
	}

	@Override
	public void attack(ArrayList<Unit> unitList) {
		for (Object e : unitList) {
		if (e instanceof Heros&&this.getPos() - this.getRange() <= ((Heros) e).getPos()+50) {
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
//				System.out.println(this.getName() + " Attack " + hero.getName() + " remain hp = " + takeDamage);
			} else {
//				System.out.println(this.getName() + " Attack Miss!");
			}
		}
		else if(e instanceof Castle&&this.getPos()-this.getRange()<=0) {
			Castle castle = (Castle) e;
			
			int takeDamage = castle.getHealth() - this.getAttackPower();
			if (takeDamage < 0) {
				castle.setHealth(0);
			} else {
				castle.setHealth(takeDamage);
			}
			System.out.println("Castle taking "+this.getAttackPower()+"damage");
			
		}
	}
	}

	public boolean isTaking() {
		return taking;
	}

	public void setTaking(boolean taking) {
		this.taking = taking;
	}



}
