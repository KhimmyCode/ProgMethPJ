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

public class Orc extends Enemies implements Attackable {
	private Image[] orcFrames;
	private Image[] orcAttackingFrames;
	private int currentAttackingFrame;
	private long lastAttackingFrameTime;
	private int currentFrame;
	private long lastFrameTime;
	private boolean taking;
	private boolean isAttacking;

	public Orc(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
			int evasion, double cooldown) {
		super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
	}

	public Orc() {
		super("Orc", 100, 15, 1, 2, false, 110, 15, 1);
		this.orcFrames = new Image[6];
		this.orcAttackingFrames = new Image[6];
		this.currentFrame = 0;
		this.lastFrameTime = System.currentTimeMillis();
		this.setTaking(false);
		isAttacking = false;

		for (int i = 0; i < 6; i++) {
			orcFrames[i] = new Image(ClassLoader.getSystemResource("orc/orc-walk/orc-walk" + i + ".png").toString());
		}
		for (int i = 0; i < 6; i++) {
			orcAttackingFrames[i] = new Image(
					ClassLoader.getSystemResource("orc/orc-attack/orc-attack" + i + ".png").toString());
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

		gc.drawImage(orcFrames[currentFrame], this.getPos(), 147, 200, 300);
	}

	public void renderAttacking(GraphicsContext gc) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastAttackingFrameTime > 200) {
			currentAttackingFrame = (currentAttackingFrame + 1) % 6;
			lastAttackingFrameTime = currentTime;
		}
		if (currentAttackingFrame == 4 && !isAttacking) {
			attack(GameLogic.getInstance().getUnitInFiled());
			isAttacking = true;

		}
		if (currentAttackingFrame == 0) {
			isAttacking = false;
		}
		gc.drawImage(orcAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
	}

	@Override
	public void attack(ArrayList<Unit> unitList) {
		for (Object e : unitList) {
			if (e instanceof Heros && this.getPos() - this.getRange() <= ((Heros) e).getPos() + 50) {
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
			} else if (e instanceof Castle && this.getPos() - this.getRange() <= 0) {
				Castle castle = (Castle) e;

				int takeDamage = castle.getHealth() - this.getAttackPower();
				if (takeDamage < 0) {
					castle.setHealth(0);
				} else {
					castle.setHealth(takeDamage);
				}
				System.out.println("Castle taking " + this.getAttackPower() + "damage");

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
