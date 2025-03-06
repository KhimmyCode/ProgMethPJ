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
		this.baseHealth = 100;
		this.enemyCount = 50;

	}

	@Override
	public List<Enemies> spawnEnemies() {
		List<Enemies> enemies = new ArrayList<>();

		for (int i = 0; i < 20; ++i) {
			Slime slime = new Slime();
			enemies.add(slime);
			Slime slime1 = new Slime();
			enemies.add(slime1);
			Orc o = new Orc();
			enemies.add(o);
		}

		Thread sp = new Thread(() -> {
			for (Enemies tar : enemies) {
//				System.out.println(tar);
				if (GameLogic.getInstance().isEnd()) {
					System.out.println("end");
					break;
				}
				if (tar instanceof Slime) {
					Slime slime = (Slime) tar;
					GameLogic.getInstance().addUnitToEnemyTeam(slime);
				} else if (tar instanceof Werebear) {
					Werebear wb = (Werebear) tar;
					GameLogic.getInstance().addUnitToEnemyTeam(wb);
				} else if (tar instanceof Orc) {
					Orc o = (Orc) tar;
					GameLogic.getInstance().addUnitToEnemyTeam(o);
				}
				try {
					Thread.sleep(1000 * 8);
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

	@Override
	public void applyDifficultySettings() {
		System.out.println("Difficulty set to Easy.");
	}

}
