package logic;

import entity.enemies.Slime;
import entity.interfaces.Enemies;
import java.util.ArrayList;

public class EnemyManager {
    private ArrayList<Enemies> enemies;

    public EnemyManager() {
        enemies = new ArrayList<>();
    }

    public void spawnEnemy(String enemyType) {
        Enemies enemy = null;
        switch (enemyType) {
            case "Slime":
                enemy = new Slime();
                break;
            // Add more enemies here if needed
            default:
                break;
        }
        if (enemy != null) {
            enemies.add(enemy);
            System.out.println(enemy.getName() + " spawned!");
        }
    }

    public void updateEnemies() {
        // Update each enemy (e.g., move, attack, etc.)
        for (Enemies enemy : enemies) {
            enemy.walk();
            // Add any other behavior (attack, etc.) here
        }
    }

    public ArrayList<Enemies> getEnemies() {
        return enemies;
    }
}
