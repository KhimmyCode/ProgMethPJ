package entity.heros;

import java.util.ArrayList;

import entity.enemies.Jail;
import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;

public class Wizard extends Heros implements Attackable {

	private Image[] wizardFrames;
	private int currentFrame;
	private long lastFrameTime;
	private Image[] wizardAttackingFrames;
	private int currentAttackingFrame;
	private long lastAttackingFrameTime;
	private boolean isAttacking;

	public Wizard(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown, int cost, double deployTime) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
	}

	public Wizard() {
		this("Wizard", 75, 6, 1, 200, true, 200, 0, 0.85, 110, 15);
		this.wizardFrames = new Image[8];
		this.wizardAttackingFrames = new Image[6];
		this.currentFrame = 0;
		this.currentAttackingFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		this.lastAttackingFrameTime = System.currentTimeMillis();
		this.isAttacking = false;

		for (int i = 0; i < 8; i++) {
			wizardFrames[i] = new Image(
					ClassLoader.getSystemResource("wizard/wizard-walk/wizard-walk" + i + ".png").toString());

		}
		for (int i = 0; i < 6; i++) {
			wizardAttackingFrames[i] = new Image(
					ClassLoader.getSystemResource("wizard/wizard-attack/wizard-attack" + i + ".png").toString());

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
		gc.drawImage(wizardFrames[currentFrame], this.getPos(), 147, 200, 300);
	}

	public void renderAttacking(GraphicsContext gc) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastAttackingFrameTime > 100) {
			currentAttackingFrame = (currentAttackingFrame + 1) % 6;
			lastAttackingFrameTime = currentTime;
		}
		if (currentAttackingFrame == 3 && !isAttacking) {
			attack(GameLogic.getInstance().getUnitInFiled());
			isAttacking = true;
		}
		if (currentAttackingFrame == 0) {
			isAttacking = false;
		}
		gc.drawImage(wizardAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
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
				}
			} else if (e instanceof Jail && this.getPos() + this.getRange() >= 500) {
				Jail jail = (Jail) e;

				int takeDamage = jail.getHealth() - this.getAttackPower();
				if (takeDamage < 0) {
					jail.setHealth(0);
				} else {
					jail.setHealth(takeDamage);
				}

			}
		}
	}

	public boolean isEnemyInRange(ArrayList<Unit> unitList) {
		for (Unit u : unitList) {
			if (u instanceof Enemies && this.getPos() + this.getRange() >= ((Enemies) u).getPos()) {
				return true;
			}
		}
		return false;
	}
}