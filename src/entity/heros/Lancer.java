package entity.heros;

import java.util.ArrayList;

import entity.enemies.Jail;
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

public class Lancer extends Heros implements Attackable {
    
    private Image[] lancerFrames;
    private int currentFrame;
    private long lastFrameTime;
    private Image[] lancerAttackingFrames;
    private int currentAttackingFrame;
    private long lastAttackingFrameTime;
    private boolean isAttacking;

    public Lancer(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
            int evasion, double cooldown, int cost, double deployTime) {
        super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
    }

    public Lancer() {
        // Name, hp, atk, spd, range, team, acc, eva, cool, cost, deploytime
        this("Lancer", 200, 20, 2, 20, true, 150, 15, 3, 200, 15);
        this.lancerFrames = new Image[8];
        this.lancerAttackingFrames = new Image[6]; // ปรับเป็น 6 เฟรม
        this.currentFrame = 0;
        this.currentAttackingFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();
        this.lastAttackingFrameTime = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
            lancerFrames[i] =  new Image(ClassLoader.getSystemResource("lancer/lancer-walk/lancer-walk" + i + ".png").toString());
           
        }
        for (int i = 0; i < 6; i++) { // ปรับเป็น 6 เฟรม
            lancerAttackingFrames[i] =  new Image(ClassLoader.getSystemResource("lancer/lancer-attack/lancer-attack" + i + ".png").toString());
           
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


    public void renderWalk(GraphicsContext gc) {
        gc.drawImage(lancerFrames[currentFrame], this.getPos(), 147, 200, 300);
    }


    public void renderAttacking(GraphicsContext gc) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackingFrameTime > 200) {
            currentAttackingFrame = (currentAttackingFrame + 1) % 6; // ปรับเป็น 6 เฟรม
            lastAttackingFrameTime = currentTime;
        }
        if (currentAttackingFrame == 5&& !isAttacking) { // คงเฟรมที่ 2 เป็นจุดโจมตีเหมือนเดิม
            attack(GameLogic.getInstance().getUnitInFiled());
            isAttacking=true;
            System.out.println("attack");
        }
        
        if(currentAttackingFrame ==0) {
        	isAttacking = false;
        }
        System.out.println("frame =" + currentAttackingFrame);
        gc.drawImage(lancerAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
    }

    @Override
    public void attack(ArrayList<Unit> unitList) {
        for (Object e : unitList) {
            if (e instanceof Enemies && this.getPos() + this.getRange() >= ((Enemies) e).getPos()) {
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
                    System.out.println(this.getName() + " Attack " + enemy.getName() );
                } else {
                    System.out.println(this.getName() + " Attack Miss!");
                }
            }
            else if(e instanceof Jail&&this.getPos()+this.getRange()>=600) {
				Jail jail = (Jail) e;
				
				int takeDamage = jail.getHealth() - this.getAttackPower();
				if (takeDamage < 0) {
					jail.setHealth(0);
				} else {
					jail.setHealth(takeDamage);
				}
				System.out.println("Jail taking "+this.getAttackPower()+"damage"+ " remain hp = " + takeDamage);
				
			}
        }
    }
}