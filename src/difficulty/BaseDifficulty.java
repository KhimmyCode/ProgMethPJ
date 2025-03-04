package difficulty;

import java.util.ArrayList;
import java.util.List;

import entity.interfaces.Enemies;

public abstract class BaseDifficulty {
    protected int baseHealth;
    protected int enemyCount;

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getEnemyCount() {
        return enemyCount;
    }


    public abstract void applyDifficultySettings();

    public abstract List<Enemies> spawnEnemies() ;
    
}
