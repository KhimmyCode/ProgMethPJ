package logic;

import javafx.application.Platform;
import javafx.scene.text.Text;
import java.util.ArrayList;

import entity.heros.Archer;
import entity.heros.Knight;
import entity.heros.Lancer;
import entity.heros.Priest;
import entity.heros.Wizard;
import entity.interfaces.Heros;
import logic.GameLogic;
import scenes.GameScene;

public class CrystalManager {
	private int crystalCount;
	private Text crystalText;
	private ArrayList<Heros> heroList;

	private int upgradeLevel = 0;
	private int upgradeCost = 50;
	private int upgradeRate = 100;

	public CrystalManager(int initialCrystals, Text crystalText) {
		this.crystalCount = initialCrystals;
		this.crystalText = crystalText;
		this.heroList = new ArrayList<>();
		updateCrystalText();
	}

	public void spawnKnight() {
		Knight knight = new Knight();
		int knightCost = knight.getCost();

		if (crystalCount >= knightCost) {
			crystalCount -= knightCost;
			heroList.add(knight);
			GameLogic.getInstance().addUnitToOurTeam(knight);
			updateCrystalText();
			System.out.println("Spawned a Knight! Remaining crystals: " + crystalCount);
		} else {
			System.out.println("Not enough crystals to spawn a Knight.");
		}
	}

	public void spawnArcher() {
		Archer archer = new Archer();
		int archerCost = archer.getCost();

		if (crystalCount >= archerCost) {
			crystalCount -= archerCost;
			heroList.add(archer);
			GameLogic.getInstance().addUnitToOurTeam(archer);
			updateCrystalText();
			System.out.println("Spawned an Archer! Remaining crystals: " + crystalCount);
		} else {
			System.out.println("Not enough crystals to spawn an Archer.");
		}
	}

	public void spawnPriest() {
		Priest priest = new Priest();
		int priestCost = priest.getCost();

		if (crystalCount >= priestCost) {
			crystalCount -= priestCost;
			heroList.add(priest);
			GameLogic.getInstance().addUnitToOurTeam(priest);
			updateCrystalText();
			System.out.println("Spawned a Priest! Remaining crystals: " + crystalCount);
		} else {
			System.out.println("Not enough crystals to spawn a Priest.");
		}
	}

	public void spawnWizard() {
		Wizard wizard = new Wizard();
		int wizardCost = wizard.getCost();

		if (crystalCount >= wizardCost) {
			crystalCount -= wizardCost;
			heroList.add(wizard);
			GameLogic.getInstance().addUnitToOurTeam(wizard);
			updateCrystalText();
			System.out.println("Spawned a Wizard! Remaining crystals: " + crystalCount);
		} else {
			System.out.println("Not enough crystals to spawn a Wizard.");
		}
	}

	public void spawnLancer() {
		Lancer lancer = new Lancer();
		int lancerCost = lancer.getCost();

		if (crystalCount >= lancerCost) {
			crystalCount -= lancerCost;
			heroList.add(lancer);
			GameLogic.getInstance().addUnitToOurTeam(lancer);
			updateCrystalText();
			System.out.println("Spawned a Lancer! Remaining crystals: " + crystalCount);
		} else {
			System.out.println("Not enough crystals to spawn a Lancer.");
		}
	}

	public void startCrystalCount() {
		Thread crystalThread = new Thread(() -> {
			while (!GameLogic.isEnd()) {
				try {
					Thread.sleep(upgradeRate);
//					Thread.sleep(1);
					crystalCount += 1;
					updateCrystalText();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		crystalThread.setDaemon(true);
		crystalThread.start();
	}

	public void upgradeCrystal() {
		if (crystalCount >= upgradeCost && upgradeLevel < 5) {
			crystalCount -= upgradeCost;
			upgradeLevel++;
			upgradeCost += 50;

			upgradeRate = Math.max(10, upgradeRate - 10);

			updateCrystalText();
			System.out.println("Crystal rate upgraded to level " + upgradeLevel + "!");
		} else if (upgradeLevel >= 5) {
			System.out.println("Max upgrade level reached!");
		} else {
			System.out.println("Not enough crystals to upgrade.");
		}
	}

	private void updateCrystalText() {
		Platform.runLater(() -> {
			crystalText.setText("Crystals: " + crystalCount + " | Upgrade Level: " + upgradeLevel + " | Upgrade Cost: "
					+ upgradeCost);
		});
	}

	public int getCrystalCount() {
		// TODO Auto-generated method stub
		
		return crystalCount;
	}

}
