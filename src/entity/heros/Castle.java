package entity.heros;

import entity.interfaces.Based;
import javafx.scene.image.Image;

public class Castle extends Based {
    private Image newCastle;
    private Image brokenCastle;

    public Castle(String name, int health, boolean isAlley) {
        super(name, health, isAlley);
        
    }
    public Castle() {
        this("Castle",250,true);
    }
    //ต้องสร้าง method เกี่ยวกับการทำรูปภาพ
    //อันนี้แค่ของฝั่งเรา 
    //ใส่ไว้ใน  entity.heros
}