package entity.heros;

import entity.interfaces.Based;
import javafx.scene.image.Image;

public class Castle extends Based {
    private Image newCastle;
    private Image brokenCastle;
    private Image currentFrame;

    public Castle(String name, int health, boolean isAlley) {
        super(name, health, isAlley); 
        this.currentFrame = newCastle; //เดี๋ยวค่อยมาแก้
        
    }
    
    public boolean checkDead(){
    	if(this.getHealthBased() <= 0) {
    		this.currentFrame = brokenCastle;
    		return true;
    	}else 
    		return false;
    	
    }
    
    //หาภาพ newCastle , brokenCastle 
}