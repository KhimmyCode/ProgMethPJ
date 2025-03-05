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

public class Archer extends Heros implements Attackable {

    private Image[] archerFrames;
    private int currentFrame;
    private long lastFrameTime;
    private Image[] archerAttackingFrames;
    private int currentAttackingFrame;
    private long lastAttackingFrameTime;
    private boolean isAttacking; // เพิ่มตัวแปรเพื่อตรวจสอบว่ากำลังโจมตีหรือไม่

    public Archer() {
        // Name, hp, atk, spd, range, team, acc, eva, cool, cost, deploytime
        super("Archer", 75, 15, 1, 100, true, 80, 5, 0.85, 90, 10);
        this.archerFrames = new Image[8]; // เดิน 8 เฟรม
        this.archerAttackingFrames = new Image[12]; // โจมตี 12 เฟรม
        this.currentFrame = 0;
        this.currentAttackingFrame = 0;
        this.lastFrameTime = System.currentTimeMillis();
        this.lastAttackingFrameTime = System.currentTimeMillis();
        this.isAttacking = false;

        for (int i = 0; i < 8; i++) {
            archerFrames[i] = new Image("/archer/archer-walk/archer-walk" + i + ".png");
        }
        for (int i = 0; i < 12; i++) {
            archerAttackingFrames[i] = new Image("/archer/archer-attack/archer-attack" + i + ".png");
        }
    }

    @Override
    public void walk() {
        System.out.println("Archer in " + this.getPos());
        this.setPos(this.getPos() + this.getSpeed());

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > 100) {
            currentFrame = (currentFrame + 1) % 8; // เดิน 8 เฟรม
            lastFrameTime = currentTime;
        }
    }


    public void renderWalk(GraphicsContext gc) {
        gc.drawImage(archerFrames[currentFrame], this.getPos(), 147, 200, 300);
    }



    public void renderAttacking(GraphicsContext gc) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackingFrameTime > 100) { // ลดความเร็วในการเปลี่ยนเฟรมโจมตี
            currentAttackingFrame = (currentAttackingFrame + 1) % 12; // โจมตี 12 เฟรม
            lastAttackingFrameTime = currentTime;
        }
        if (currentAttackingFrame == 9 && !isAttacking) { // โจมตีที่เฟรม 5 (เปลี่ยนเฟรม)
            attack(GameLogic.getInstance().getUnitInFiled());
            System.out.println("attack");
            isAttacking = true;
        }
        if (currentAttackingFrame == 0){
            isAttacking = false;
        }
        System.out.println("frame =" + currentAttackingFrame);
        gc.drawImage(archerAttackingFrames[currentAttackingFrame], this.getPos(), 147, 200, 300);
    }

    @Override
    public void attack(ArrayList<Unit> unitList) {
        for (Object e : unitList) {
            if (e instanceof Enemies && this.getPos() + this.getRange() >= ((Enemies) e).getPos()) {
                Enemies enemy = (Enemies) e;

//                if (enemy instanceof Slime) {
//                    Slime s = (Slime) enemy;
//                    s.setTaking(true);
//                } else if (enemy instanceof Orc) {
//                    Orc o = (Orc) enemy;
//                    o.setTaking(true);
//                } else if (enemy instanceof Werebear) {
//                    Werebear wb = (Werebear) enemy;
//                    wb.setTaking(true);
//                }

                int hitChance = this.getAccuracy() - enemy.getEvasion();
                double successRate = hitChance / 100.0;

                if (Math.random() < successRate) {
                    int takeDamage = enemy.getHealth() - this.getAttackPower();
                    if (takeDamage < 0) {
                        enemy.setHealth(0);
                    } else {
                        enemy.setHealth(takeDamage);
                    }
                    System.out.println(this.getName() + " Attack " + enemy.getName() + " remain hp = " + takeDamage);
                } else {
                    System.out.println(this.getName() + " Attack Miss!");
                }
                break; // โจมตีแค่ศัตรูตัวเดียวในระยะ
            }
        }
    }
}