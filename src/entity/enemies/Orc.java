package entity.enemies;

import java.util.ArrayList;

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
	private boolean taking ;
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
            orcFrames[i] = new Image("file:res/orc/orc-walk/orc-walk" + i + ".png");
        }
        for(int i =0 ;i<6;i++) {
        	orcAttackingFrames[i] = new Image("/orc/orc-attack/orc-attack"+i+".png");
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

		gc.drawImage(orcFrames[currentFrame], this.getPos(), 147, 200, 300);
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
            
        }
        if(currentAttackingFrame ==0) {
        	isAttacking = false;
        }
//        System.out.println("frame =" + currentAttackingFrame);
        gc.drawImage(orcAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
	}

	@Override
	public void attack(ArrayList<Unit> unitList) {
		for(Object e : unitList ) {
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

	public boolean isTaking() {
		return taking;
	}

	public void setTaking(boolean taking) {
		this.taking = taking;
	}


}
