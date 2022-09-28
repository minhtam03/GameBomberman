package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Entity{
    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

//    public void explore() {
//        Entity object1 = new Flame(super.x, super.y, Sprite.powerup_flames.getFxImage());
//        stillObjects.add(object1);
//
//    }

    @Override
    public void update() {

    }
}
