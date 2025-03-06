package difficulty;

import java.util.ArrayList;
import java.util.List;

import entity.enemies.Orc;
import entity.enemies.Slime;
import entity.enemies.Werebear;
import entity.interfaces.Enemies;
import logic.GameLogic;

public class HardDifficulty extends BaseDifficulty {
	Enemies orc ;
	Enemies slime ;
	Enemies werebear ;

    public HardDifficulty() {
        this.baseHealth = 300;
        this.enemyCount = 100;
    }

	@Override
	public void applyDifficultySettings() {
		// TODO Auto-generated method stub
		System.out.println("Difficulty set to Hard (Prepare to LOSE!!!!!!).");
		
	}

	@Override
	public List<Enemies> spawnEnemies() {
		List<Enemies> enemies = new ArrayList<>();
		for (int i = 0; i < 30; ++i) {
	        Slime slime = new Slime();
	        enemies.add(slime);
	        Orc o = new Orc();
	        enemies.add(o);
	        
	        Orc o1 = new Orc();
	        enemies.add(o1);
	        Werebear wb = new Werebear();
	        enemies.add(wb);
	    }
	    
	    Thread sp = new Thread(()->{
	    	for (Enemies tar : enemies) {
		        if(GameLogic.getInstance().isEnd()) break;
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
		        try {
					Thread.sleep(1000*5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	    });
	    
	    sp.setDaemon(true);
	    
	    sp.start();
		return enemies;
		
	}


}
