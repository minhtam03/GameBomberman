/**
 * hiệu ứng di chuyển bản đồ
 * di chuyển nhân vật không được chạm vào tường, quái vật tự di chuyển theo nhiều hướng
 * hiệu ứng bom nổ
 */
package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import javax.print.DocFlavor;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();    // mảng đối tượng tĩnh


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(keyEvent -> {
                    KeyCode code = keyEvent.getCode();
                    switch (code) {
                        case LEFT : {
                            bomberman.moveLeft();
                            bomberman.setImg(Sprite.player_left.getFxImage());
                            bomberman.setImg(Sprite.player_left_1.getFxImage());
                            bomberman.setImg(Sprite.player_left_2.getFxImage());
                            break;
                        }
                        case RIGHT: {
                            bomberman.moveRight();
                            bomberman.setImg(Sprite.player_right.getFxImage());
                            break;
                        }
                        case UP: {
                            bomberman.moveUp();
                            bomberman.setImg(Sprite.player_up.getFxImage());
                            break;
                        }
                        case DOWN: {
                            bomberman.moveDown();
                            bomberman.setImg(Sprite.player_down.getFxImage());
                            break;
                        }
                        case SPACE: {
                            Entity object = new BombItem(bomberman.getX()/Sprite.SCALED_SIZE, bomberman.getY()/Sprite.SCALED_SIZE,Sprite.bomb.getFxImage());
                            stillObjects.add(object);

                            Flame flame_center = new Flame(bomberman.getX()/Sprite.SCALED_SIZE, bomberman.getY()/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
                            entities.add(flame_center);
                            stillObjects.add(flame_center);

                            Flame flame1 = new Flame((bomberman.getX())/Sprite.SCALED_SIZE, (bomberman.getY()+1)/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
                            entities.add(flame1);
                            stillObjects.add(flame1);
                        }
                    }
                }
                );

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        //createMap();
        createMapFromFile();

        //Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        //entities.add(bomberman);
    }

//    public void createMap() {
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object;
//                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                }
//                else if (j == 5) {
//                    object = new Bomber(i, j, Sprite.player_down.getFxImage()) {
//                    };
//                }
//                else {
//                    object = new Grass(i, j, Sprite.grass.getFxImage());
//                }
//                stillObjects.add(object);
//            }
//        }
//    }

    public void createMapFromFile() {
        BufferedReader bufferedReader = null;
        try {
            Reader reader = new FileReader("res/levels/level1.txt");
            bufferedReader = new BufferedReader(reader);
            String firstLine = bufferedReader.readLine();
            //System.out.println(firstLine);

            int level = 0;
            int row = 0;
            int column = 0;

            String[] tokens = firstLine.split(" ");
            level = Integer.parseInt(tokens[0]);
            row = Integer.parseInt(tokens[1]);
            column = Integer.parseInt(tokens[2]);

            System.out.println(level + " " + row + " " + column);

            char[][] mapMatrix = new char[row][column];

            for (int i = 0; i < row; i++) {
                String line = bufferedReader.readLine();
                for (int j = 0; j < column; j++) {
                    char character = line.charAt(j);
                    mapMatrix[i][j] = character;
                }
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    char character = mapMatrix[i][j];
                    switch (character) {
                        case '#' : {
                            //Wall
                            Entity object = new Wall(j, i, Sprite.wall.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        case '*' : {
                            //Brick
                            Entity object = new Brick(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        case 'x' : {
                            Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object1);
                            //Portal
                            Entity object = new Portal(j, i, Sprite.portal.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
//                        case 'p' : {
//                            Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
//                            stillObjects.add(object1);
//                            //Bomber
//                            Entity object = new Bomber(j, i, Sprite.player_right.getFxImage());
//                            stillObjects.add(object);
//                            break;
//                        }
                        case '1' : {
                            Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object1);
                            //Balloon
                            Entity object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                            entities.add(object);
                            break;
                        }
                        case '2' : {
                            Entity object1 = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object1);
                            //Oneal
                            Entity object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        case 'b' : {
                            Entity object1 = new Brick(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(object1);
                            //Bomb Item
                            Entity object = new Bomb(j, i, Sprite.bomb.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        case 'f' : {
                            //Flame Item
                            Entity object1 = new Brick(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(object1);
                            Entity object = new Flame(j, i, Sprite.powerup_flames.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        case 's' : {
                            //Speed Item
                            Entity object1 = new Brick(j, i, Sprite.brick.getFxImage());
                            stillObjects.add(object1);
                            Entity object = new Speed(j, i, Sprite.powerup_speed.getFxImage());
                            stillObjects.add(object);
                            break;
                        }
                        default: {
                            Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
