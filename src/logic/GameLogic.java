package logic;

import java.util.ArrayList;
import entity.interfaces.Unit;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import entity.*;
import entity.enemies.Jail;

public class GameLogic {
	
	private static GameLogic instance;
	private static VBox root;
	private static Scene scene;
	private static Canvas field;
	private static boolean isEnd;
	
	private ArrayList<Unit> unitInFiled ;
	

	private ArrayList<Unit> ourTeamUnits;
	private ArrayList<Unit> enemyTeamUnits;

	private GameLogic() {
		ourTeamUnits = new ArrayList<>();
		enemyTeamUnits = new ArrayList<>();
		unitInFiled = new ArrayList<>();
		  
		isEnd=false;
	}
	
	public void addUnitToField(Unit unit) {
		getUnitInFiled().add(unit) ;
	}

	public Unit getUnitAtPosition(double x) {
		for (Unit unit : getUnitInFiled()) {
			if (unit.getPos() == x) {
				System.out.println(unit.getName() + "is in " + x);
				return unit;
			}
		}
		return null;
	}
	
	
	public static VBox getRoot() {
		return root;
	}

	public static void setRoot(VBox root) {
		GameLogic.root = root;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		GameLogic.scene = scene;
	}

	public static Canvas getField() {
		return field;
	}

	public static void setField(Canvas field) {
		GameLogic.field = field;
	}

	public static boolean isEnd() {
		return isEnd;
	}

	public static void setEnd(boolean isEnd) {
		GameLogic.isEnd = isEnd;
	}

	public ArrayList<Unit> getOurTeamUnits() {
		return ourTeamUnits;
	}

	public void setOurTeamUnits(ArrayList<Unit> ourTeamUnits) {
		this.ourTeamUnits = ourTeamUnits;
	}

	public ArrayList<Unit> getEnemyTeamUnits() {
		return enemyTeamUnits;
	}

	public void setEnemyTeamUnits(ArrayList<Unit> enemyTeamUnits) {
		this.enemyTeamUnits = enemyTeamUnits;
	}

	public static void setInstance(GameLogic instance) {
		GameLogic.instance = instance;
	}

	public static GameLogic getInstance() {
		if(instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}

	public void addUnitToOurTeam(Unit unit) {
		ourTeamUnits.add(unit);
		unitInFiled.add(unit);
	}

	public void addUnitToEnemyTeam(Unit unit) {
		enemyTeamUnits.add(unit);
		unitInFiled.add(unit);
	}
	
	

	public ArrayList<Unit> findAllTargetInRange(Unit unit, double range) {
		ArrayList<Unit> targets = new ArrayList<>();

		
		if(unit.isAlley()) {
			for (Unit u : enemyTeamUnits) {     //maybe change this condition if right is pos = 0
				if (u.getPos()-unit.getPos() <= unit.getRange()) {
					targets.add(u);
				}
			}
		}
		
		else {
			for (Unit u : ourTeamUnits) {
				if (unit.getPos()-u.getPos() <=unit.getRange()) {
					targets.add(u);
				}
			}
		}
		

		return targets;
	}
	public void resetGameState() {
	    ourTeamUnits.clear(); // ล้างทีมของเรา
	    enemyTeamUnits.clear(); // ล้างทีมศัตรู
	    unitInFiled.clear();
	   
	    isEnd = false; // รีเซ็ตสถานะเกม
	}

	public ArrayList<Unit> getUnitInFiled() {
		return unitInFiled;
	}
	
	
}
