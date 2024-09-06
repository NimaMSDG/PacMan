package com.example.javafxtest;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import static com.example.javafxtest.HunterGhostController.huntingMove;
import static com.example.javafxtest.PacmanModel.round;

public class Ghost {
    Paint color;
    ImageView image;

    Image imageUpRed = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\redghost.gif"));
    Image imageDownRed = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\redghost.gif"));
    Image imageRightRed = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\redghost.gif"));
    Image imageLeftRed = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\redghost.gif"));
    Image imageUpPink = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pink-left.png"));
    Image imageDownPink = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pink-left.png"));
    Image imageRightPink = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pink-left.png"));
    Image imageLeftPink = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pink-left.png"));
    Image imageUpBlue = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\download.png"));
    Image imageDownBlue = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\download.png"));
    Image imageRightBlue = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\download.png"));
    Image imageLeftBlue = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\download.png"));
    Image imageUpOrange = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\orangeghost.gif"));
    Image imageDownOrange = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\orangeghost.gif"));
    Image imageRightOrange = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\orangeghost.gif"));
    Image imageLeftOrange = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\orangeghost.gif"));

    boolean isHunter;
    double x,y;
    Direction direction;
    Direction[] directions = {Direction.Up,Direction.Right,
            Direction.Down,Direction.Left};
    Direction[] directions1 = {Direction.Up,Direction.Right,
            Direction.Down,Direction.Left,Direction.Left,Direction.Down};
    Direction[] directions2 = {Direction.Up,Direction.Right,
            Direction.Down,Direction.Left,Direction.Left,Direction.Up};
    Direction[] directions3 = {Direction.Up,Direction.Right,
            Direction.Down,Direction.Left,Direction.Right,Direction.Down};
    Direction[] directions4 = {Direction.Up,Direction.Right,
            Direction.Down,Direction.Left,Direction.Right,Direction.Up};
    double speed=0.1;
    boolean dontGoDown=true;
    long timer;
    long deltaT(){
        return (System.currentTimeMillis()-timer);
    }

    int i() {return (int)Math.floor(x/Map.BlockSize);};
    int j() {return (int)Math.floor(y/Map.BlockSize);};
    public double dx(){
        if (direction.equals(Direction.Right)) return speed;
        if (direction.equals(Direction.Left)) return -speed;
        return 0;
    }
    public double dy(){
        if (direction.equals(Direction.Up)) return -speed;
        if (direction.equals(Direction.Down)) return speed;
        return 0;
    }
    Ghost(Paint paint,boolean hunter) throws FileNotFoundException {
        color=paint;
        isHunter=hunter;
        x=9*25+12.5;
        y=9*25+12.5;
        direction=Direction.Up;
        setImage();
        timer=System.currentTimeMillis()+100;
    }

    public void setImage(){
        Image[] images = new Image[4];
        if (color==Color.RED) images = new Image[]{imageUpRed, imageDownRed, imageRightRed, imageLeftRed};
        if (color==Color.PINK) images = new Image[]{imageUpPink, imageDownPink, imageRightPink, imageLeftPink};
        if (color==Color.BLUE) images = new Image[]{imageUpBlue, imageDownBlue, imageRightBlue, imageLeftBlue};
        if (color==Color.ORANGE) images = new Image[]{imageUpOrange, imageDownOrange, imageRightOrange, imageLeftOrange};

        if (direction.equals(Direction.Up))  image = new ImageView(images[0]);
        else if (direction.equals(Direction.Down)) image = new ImageView(images[1]);
        else if (direction.equals(Direction.Right)) image = new ImageView(images[2]);
        else if (direction.equals(Direction.Left)) image = new ImageView(images[3]);

        image.setLayoutX(x-12.5);
        image.setLayoutY(y-12.5);
        image.setFitWidth(25);
        image.setFitHeight(25);
    }


    public boolean canMove(int[][] map){
        int dx=0,dy=0;
        if (x!=i()*25+Map.BlockSize/2.0 || y!=j()*25+Map.BlockSize/2.0){
            return true;
        }
        if (direction.equals(Direction.Up)) dy=-1;
        if (direction.equals(Direction.Down)) dy=1;
        if (direction.equals(Direction.Right)) dx=1;
        if (direction.equals(Direction.Left)) dx=-1;

        return map[this.j()+dy][this.i()+dx]!=1 ;
    }

    public void move(){
        x=round(x+this.dx(),3);
        y=round(y+this.dy(),3);
        image.setLayoutX(x-12.5);
        image.setLayoutY(y-12.5);
    }

    public boolean isInTaghatoo(int[][] map,PacmanModel pacmanModel){
        if (deltaT()/2000<1) {
            return false;
        }
        int possibleWays=0;
        for (int i = 0; i < 4; i++) {
            if (canReDirect(directions[i],map)) possibleWays++;
        }
        if (possibleWays<2) return false;
        if (!canMove(map)) return false;
        Random random = new Random();
        int whichOne = random.nextInt(8);
        if (whichOne==0) return false;
        else {
            if (this.isHunter){
                reDrirect(map,pacmanModel);
                return true;
            }
            whichOne = random.nextInt(2);
            if (direction==directions[0]||direction==directions[2]){
                if (canReDirect(directions[whichOne*2+1],map)) {
                    direction=directions[whichOne*2+1];
                    x = (double) i()*25.0+Map.BlockSize/2.0;
                    y = (double) j()*25.0+Map.BlockSize/2.0;
                    image.setLayoutX(x-12.5);
                    image.setLayoutY(y-12.5);
                }
            }
            else if (direction==directions[1]||direction==directions[3]){
                if (canReDirect(directions[whichOne*2],map)) {
                    direction=directions[whichOne*2];
                    x = (double) i()*25.0+Map.BlockSize/2.0;
                    y = (double) j()*25.0+Map.BlockSize/2.0;
                    image.setLayoutX(x-12.5);
                    image.setLayoutY(y-12.5);
                }
            }
        }
        return true;
    }

    public boolean canReDirect(Direction direction,int[][] map){
        if (this.direction.equals(direction)) return false;
        int dx=0,dy=0;
        if (direction.equals(Direction.Up)) dy=-1;
        if (direction.equals(Direction.Down)) dy=1;
        if (direction.equals(Direction.Right)) dx=1;
        if (direction.equals(Direction.Left)) dx=-1;

        return map[this.j()+dy][this.i()+dx]!=1;
    }

    public void reDrirect(int[][] map,PacmanModel pacmanModel){
        if (this.isHunter){
            direction=huntingMove(this,map,pacmanModel.i(),pacmanModel.j());
        }
        else {
            Random random = new Random();
            Direction newDirection;
            Direction[] directions5 = new Direction[6];
            if (color==Color.RED) directions5 = directions1;
            if (color==Color.BLUE) directions5 = directions2;
            if (color==Color.ORANGE) directions5 = directions3;
            if (color==Color.PINK) directions5 = directions4;

            while (true){
                newDirection=directions5[random.nextInt(6)];
                if (newDirection==Direction.Down&&dontGoDown)continue;
                if (canReDirect(newDirection,map)) break;
            }
            dontGoDown=false;
            direction=newDirection;
        }

    }

    public boolean isInCenter(){
        return x==i()*25+12.5 && y==j()*25+12.5;
    }



}
