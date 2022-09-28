package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }


    @Override
    public void update() {
        //x++;
        //y++;
    }
    public void moveLeft() {
        x -= 5;
    }
    public void moveRight() {
        x += 5;
    }
    public void moveUp() {
        y -= 5;
    }
    public void moveDown() {
        y += 5;
    }

}
