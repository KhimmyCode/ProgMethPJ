package entity.heros;

import entity.interfaces.Based;
import entity.interfaces.Unit;
import javafx.scene.image.Image;

public class Castle extends Unit {


    public Castle(String name,int health) {
    	super(name, health, 0, 0, 0, false,0,0, 0);
        
    }

	@Override
	public void walk() {
		// TODO Auto-generated method stub
		
	}
    
   
    
    //หาภาพ newCastle , brokenCastle 
}