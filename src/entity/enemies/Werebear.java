package entity.enemies;

import java.util.ArrayList;

import entity.heros.Castle;
import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;

public class Werebear extends Enemies implements Attackable {
    private Image[] werebearFrames;
    private Image[] werebearAttackingFrames;
    private int currentAttackingFrame;
    private long lastAttackingFrameTime;
    private int currentFrame;
    private long lastFrameTime;
    private boolean taking;
    private boolean isAttacking;
    

    public Werebear(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
            int evasion, double cooldown) {
        super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
    }

    public Werebear() {
        super("WereBear", 150, 25, 2, 1, false, 100, 20, 1);
        this.werebearFrames = new Image[6];
        this.werebearAttackingFrames= new Image[12];
        this.currentFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();
        this.setTaking(false);
        isAttacking = false;

        for (int i = 0; i < 6; i++) {
            werebearFrames[i] = new Image(ClassLoader.getSystemResource("werebear/werebear-walk/werebear-walk" + i + ".png").toString());
            
        }
        for (int i = 0; i < 12; i++) {
        	werebearAttackingFrames[i] = new Image(ClassLoader.getSystemResource("werebear/werebear-attack/werebear-attack" + i + ".png").toString());
        	
        }
    }

    @Override
    public void walk() {
        if (!this.isTaking()) {
            this.setPos(this.getPos() - this.getSpeed());

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameTime > 100) {
                currentFrame = (currentFrame + 1) % 6;
                lastFrameTime = currentTime;
            }
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(werebearFrames[currentFrame], this.getPos(), 147, 200, 300);
    }
    
    public void renderAttacking(GraphicsContext gc) {
    	long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackingFrameTime > 200) {
            currentAttackingFrame = (currentAttackingFrame + 1) % 12;
            lastAttackingFrameTime = currentTime;
        }
        if (currentAttackingFrame == 9&&!isAttacking ) {
            attack(GameLogic.getInstance().getUnitInFiled());
            isAttacking=true;
            System.out.println("attack");
            
        }
        if(currentAttackingFrame ==0) {
        	isAttacking = false;
        }
        gc.drawImage(werebearAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
    	
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

    // Getters Setters
    public boolean isTaking() {
        return taking;
    }

    public void setTaking(boolean taking) {
        this.taking = taking;
    }
}