package difficulty;

import java.util.ArrayList;
import java.util.List;

import entity.enemies.Orc;
import entity.enemies.Slime;
import entity.interfaces.Enemies;

public class MediumDifficulty extends BaseDifficulty {
	Enemies slime;
	Enemies orc;

    public MediumDifficulty() {
        this.baseHealth = 150;
        this.enemyCount = 75; // ศัตรูจำนวนปานกลาง
    }

	@Override
	public void applyDifficultySettings() {
		System.out.println("Difficulty set to Medium.");
	}

	@Override
	public List<Enemies> spawnEnemies() {
		List<Enemies> enemies = new ArrayList<>();
		for(int i = 0; i < 25; ++i) {
			orc = new Orc();
			enemies.add(orc);
		}
		for(int i = 25; i < this.enemyCount;++i) {
			slime = new Slime();
			enemies.add(slime);
		}
		for(Enemies tar : enemies) {	//Debug
			System.out.println(tar);
		}
		System.out.println("----------------------------");   //Debug
		System.out.println("All enemies total : "+enemies.size());   //Debug
		return enemies;
	}


}
