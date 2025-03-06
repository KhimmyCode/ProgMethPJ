package entity.heros;

import java.util.ArrayList;
import java.util.Comparator;

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

public class Archer extends Heros implements Attackable {

	private Image[] archerFrames;
	private int currentFrame;
	private long lastFrameTime;
	private Image[] archerAttackingFrames;
	private int currentAttackingFrame;
	private long lastAttackingFrameTime;
	private boolean isAttacking;
	
	public Archer(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
            int evasion, double cooldown, int cost, double deployTime) {
        super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
    }

	public Archer() {
		// Name, hp, atk, spd, range, team, acc, eva, cool, cost, deploytime
		super("Archer", 75, 15, 1, 100, true, 80, 5, 0.85, 90, 10);
		this.archerFrames = new Image[8];
		this.archerAttackingFrames = new Image[12];
		this.currentFrame = 0;
		this.currentAttackingFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		this.lastAttackingFrameTime = System.currentTimeMillis();
		this.isAttacking = false;

		for (int i = 0; i < 8; i++) {
			archerFrames[i] = new Image(
					ClassLoader.getSystemResource("archer/archer-walk/archer-walk" + i + ".png").toString());
		}
		for (int i = 0; i < 12; i++) {
			archerAttackingFrames[i] = new Image(
					ClassLoader.getSystemResource("archer/archer-attack/archer-attack" + i + ".png").toString());
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
		gc.drawImage(archerFrames[currentFrame], this.getPos(), 147, 200, 300);
	}

	public void renderAttacking(GraphicsContext gc) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastAttackingFrameTime > 100) {
			currentAttackingFrame = (currentAttackingFrame + 1) % 12;
			lastAttackingFrameTime = currentTime;
		}
		if (currentAttackingFrame == 9 && !isAttacking) {
			attack(GameLogic.getInstance().getUnitInFiled());
			isAttacking = true;
		}
		if (currentAttackingFrame == 0) {
			isAttacking = false;
		}
		gc.drawImage(archerAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
	}

	@Override
	public void attack(ArrayList<Unit> unitList) {
		unitList.sort(Comparator.comparingInt(u -> u.getPos()));

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
				}
				break;
			} else if (e instanceof Jail && this.getPos() + this.getRange() >= 650) {
				Jail jail = (Jail) e;

				int takeDamage = jail.getHealth() - this.getAttackPower();
				if (takeDamage < 0) {
					jail.setHealth(0);
				} else {
					jail.setHealth(takeDamage);
				}
				break;
			}
		}
	}
}