package difficulty;

import java.util.ArrayList;
import java.util.List;

import entity.enemies.*;
import entity.heros.Knight;
import entity.interfaces.Enemies;
import logic.GameLogic;

public class EasyDifficulty extends BaseDifficulty {
	Enemies slime;

	public EasyDifficulty() {
		this.baseHealth = 200;
		this.enemyCount = 50;

		
	}

	@Override
	public List<Enemies> spawnEnemies() {
	    List<Enemies> enemies = new ArrayList<>();
//	    for (int i = 0; i < this.enemyCount; ++i) {
//	        Slime slime = new Slime();
//	        enemies.add(slime);
//	    }
	    for (int i = 0; i < 1; ++i) {
	    	Werebear wb = new Werebear();
	        enemies.add(wb);
	    }
	    for (int i = 0; i < 1; ++i) {
	    	Slime slime = new Slime();
	        enemies.add(slime);
	    }
	    for (int i = 0; i < 1; ++i) {
	    	Orc o = new Orc();
	        enemies.add(o);
	    }

	    // ปล่อย enemies ทีละตัว
	    for (Enemies tar : enemies) {
	        System.out.println(tar); // debug
	        if(tar instanceof Slime) {
	        	Slime slime = (Slime) tar ;
	    		GameLogic.getInstance().addUnitToEnemyTeam(slime);
	        } else if(tar instanceof Werebear) {
	        	Werebear wb = (Werebear) tar ;
	    		GameLogic.getInstance().addUnitToEnemyTeam(wb);
	        } else if(tar instanceof Orc) {
	        	Orc o = (Orc) tar ;
	    		GameLogic.getInstance().addUnitToEnemyTeam(o);
	        }
	    }

	    System.out.println(enemies.size()); // debug
	    return enemies;
	}


	@Override
	public void applyDifficultySettings() {
		System.out.println("Difficulty set to Easy.");
	}
	
	
	
}
