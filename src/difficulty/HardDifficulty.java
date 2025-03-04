package difficulty;

import java.util.ArrayList;
import java.util.List;

import entity.enemies.Orc;
import entity.enemies.Slime;
import entity.enemies.Werebear;
import entity.interfaces.Enemies;

public class HardDifficulty extends BaseDifficulty {
	Enemies orc ;
	Enemies slime ;
	Enemies werebear ;

    public HardDifficulty() {
        this.baseHealth = 100;
        this.enemyCount = 100; // ศัตรูจำนวนมาก
    }

	@Override
	public void applyDifficultySettings() {
		// TODO Auto-generated method stub
		System.out.println("Difficulty set to Hard (Prepare to LOSE!!!!!!).");
		
	}

	@Override
	public List<Enemies> spawnEnemies() {
		List<Enemies> enemies = new ArrayList<>();
		for(int i =0; i < 25;++i) {
			slime = new Slime();
			enemies.add(slime);
		}
		for(int i =0; i < this.enemyCount-50;++i) {
			orc = new Orc();
			enemies.add(orc);
		}
		for(int i =0; i <this.enemyCount-(25+50);++i) {
			werebear = new Werebear();
			enemies.add(werebear);
		}
		for(Enemies tar : enemies) {  //debugg
			System.out.println(tar);
		}
		System.out.println(enemies.size());
		return enemies;
		
	}


}
