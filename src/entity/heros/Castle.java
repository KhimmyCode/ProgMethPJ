package entity.heros;

import entity.interfaces.Unit;
import javafx.scene.image.Image;

public class Castle extends Unit {


    public Castle(String name,int health) {
    	super(name, health, 0, 0, 0, true,0,0, 0);
        
    }

	@Override
	public void walk() {}
    
}