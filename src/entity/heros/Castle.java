package entity.heros;

import entity.interfaces.Based;
import javafx.scene.image.Image;

public class Castle extends Based {
    private Image newCastle;
    private Image brokenCastle;
    private Image currentFrame;

    public Castle(int health) {
        super(health);
        
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