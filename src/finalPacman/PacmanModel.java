package com.example.javafxtest;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.Math.round;

public class PacmanModel {
    public double x,y;
    private final int size = 25;
    public double speed = 0.1;

    ImageView image = new ImageView();

    Image imageUp = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pacmanUp.gif"));
    Image imageDown = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pacmanDown.gif"));
    Image imageRight = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pacmanRight.gif"));
    Image imageLeft = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\pacmanLeft.gif"));



    public void setImage(){
        if (direction.equals(Direction.Up))  image.setImage(imageUp);
        else if (direction.equals(Direction.Down)) image.setImage(imageDown);
        else if (direction.equals(Direction.Right)) image.setImage(imageRight);
        else if (direction.equals(Direction.Left)) image.setImage(imageLeft);
        image.setFitHeight(25);
        image.setFitWidth(25);
        image.setLayoutX(x-12.5);
        image.setLayoutY(y-12.5);
    }

    Direction direction;

    public PacmanModel(int i,int j) throws FileNotFoundException {
        x=i*size+12.5;
        y=j*size+12.5;
        direction=Direction.None;
        image.setImage(imageLeft);
    }

    public int i(){return (int)Math.floor(x/Map.BlockSize); }
    public int j(){ return (int)Math.floor(y/Map.BlockSize); }

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

    public void setDirection(Direction direction){
        this.direction=direction;
    }
    public void move(){
        x=round(x+this.dx(),3);
        y=round(y+this.dy(),3);
        image.setLayoutX(x-12.5);
        image.setLayoutY(y-12.5);
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

    public boolean canReDirect(Direction direction,int[][] map){
        if (this.direction.equals(direction)) return false;
        int dx=0,dy=0;
        if (direction.equals(Direction.Up)) dy=-1;
        if (direction.equals(Direction.Down)) dy=1;
        if (direction.equals(Direction.Right)) dx=1;
        if (direction.equals(Direction.Left)) dx=-1;

        return map[this.j()+dy][this.i()+dx]!=1;
    }

    public void reDirect(Direction newDirection){
        direction = newDirection;
        x = (double) i()*25.0+Map.BlockSize/2.0;
        y = (double) j()*25.0+Map.BlockSize/2.0;
        setImage();
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }




}
