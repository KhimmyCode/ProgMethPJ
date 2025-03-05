package entity.heros;

import java.util.ArrayList;

import entity.interfaces.Buffable;
import entity.interfaces.Heros;
import entity.interfaces.Unit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;

public class Priest extends Heros implements Buffable {
    private int buffHealth;

    private Image[] priestFrames;
    private int currentFrame;
    private long lastFrameTime;
    private Image[] priestBuffFrames;
    private int currentBuffFrame;
    private long lastBuffFrameTime;
    private boolean isBuffing;

    public Priest(String name, int health, int attackPower, int buffHealth, int speed, double range, boolean isAlley,
            int accuracy, int evasion, double cooldown, int cost, double deployTime) {
        super(name, health, attackPower, speed, range, isAlley, accuracy, evasion, cooldown, cost, deployTime);
        this.setBuffHealth(buffHealth);
    }

    public Priest() {
        this("Priest", 50, 0, 2, 1, 3, true, 0, 0, 3, 100, 10);
        this.priestFrames = new Image[8];
        this.priestBuffFrames = new Image[6];
        this.currentFrame = 0;
        this.currentBuffFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();
        this.lastBuffFrameTime = System.currentTimeMillis();
        this.isBuffing = false;

        for (int i = 0; i < 8; i++) {
            priestFrames[i] = new Image("file:res/priest/priest-walk/priest-walk" + i + ".png");
        }
        for (int i = 0; i < 6; i++) {
            priestBuffFrames[i] = new Image("file:res/priest/priest-heal/priest-heal" + i + ".png");
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
        gc.drawImage(priestFrames[currentFrame], this.getPos(), 0, 200, 300);
    }

    public void renderBuff(GraphicsContext gc) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBuffFrameTime > 100) {
            currentBuffFrame = (currentBuffFrame + 1) % 6;
            lastBuffFrameTime = currentTime;
        }
        if (currentBuffFrame == 3 && !isBuffing) {
            buff(GameLogic.getInstance().getOurTeamUnits());
            System.out.println("Priest buff");
            isBuffing = true;
        }
        if (currentBuffFrame == 0) {
            isBuffing = false;
        }
        gc.drawImage(priestBuffFrames[currentBuffFrame], this.getPos(), 0, 200, 300);
    }

    @Override
    public void buff(ArrayList<Unit> unitList) {
        for (Unit e : unitList) {
            if (e instanceof Heros) {
                Heros hero = (Heros) e;
                hero.setHealth(hero.getHealth() + this.getBuffHealth());
            }
        }
    }

    public int getBuffHealth() {
        return buffHealth;
    }

    public void setBuffHealth(int buffHealth) {
        if (buffHealth < 0)
            buffHealth = 0;
        this.buffHealth = buffHealth;
    }

    public boolean isAllyInRange(ArrayList<Unit> unitList) {
        for (Unit e : unitList) {
            if (e instanceof Heros && this.getPos() + this.getRange() >= ((Heros) e).getPos()) {
                return true;
            }
        }
        return false;
    }
}