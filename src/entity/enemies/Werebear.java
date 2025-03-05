package entity.enemies;

import java.util.ArrayList;

import entity.interfaces.Attackable;
import entity.interfaces.Enemies;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Werebear extends Enemies implements Attackable {
    private Image[] werebearFrames;
    private int currentFrame;
    private long lastFrameTime;
    private boolean taking;

    public Werebear(String name, int health, int attackPower, int speed, double range, boolean isAlley, int accuracy,
            int evasion, double cooldown) {
        super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown);
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

    @Override
    public void attack(ArrayList<Unit> unitList) {
        for (Object e : unitList) {
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
    public boolean isTaking() {
        return taking;
    }

    public void setTaking(boolean taking) {
        this.taking = taking;
    }
}