package com.example.javafxtest;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import static java.lang.Math.random;

public class Controller implements Initializable,EventHandler<KeyEvent>{

    Stage stage ;
    Controller(Stage stage) throws FileNotFoundException {
        this.stage = stage;
    }

    private static final double FRAMES_PER_SECOND = 10.0;
    PacmanModel pacman = new PacmanModel(9,15);
    Ghost[] ghosts = {new Ghost(Color.RED,false),new Ghost(Color.BLUE,false)
            ,new Ghost(Color.PINK,false),new Ghost(Color.ORANGE,false)};
    Timer timer;
    ImageView[] imageViews = {pacman.image,ghosts[0].image,ghosts[1].image,ghosts[2].image,ghosts[3].image};
    Circle[][] coins = new Circle[21][19];
    ImageView[][] boosters = new ImageView[21][19];
    int score = 0;

    Image cherry = new PacmanView().cherryPIC;

    boolean isGhosted = false;
    int isGhostedCD = 10000;
    boolean increaseSpeed = false;
    int increaseSpeedCD = 10000;
    boolean freezeGhosts = false;
    int freezeGhostCD = 10000;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label scoreLabel;
    @FXML
    Label ScoreText;
    @FXML
    Label ghostMode;
    @FXML
    Label endGame;

    int mapN = 1;
    int[][] Map1 = {       {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,2,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,1},
            {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
            {1,0,2,0,0,0,0,0,0,1,0,0,0,0,0,0,2,0,1},
            {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
            {1,0,1,1,0,1,0,0,0,1,0,0,0,1,0,1,1,0,1},
            {1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,1},
            {-1,-1,-1,1,0,1,0,0,0,0,0,0,0,1,0,1,-1,-1,-1},
            {1,1,1,1,0,1,0,1,-1,-1,-1,1,0,1,0,1,1,1,1},
            {1,0,0,0,0,0,0,1,-1,-1,-1,1,0,0,0,0,0,0,1},
            {1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1},
            {-1,-1,-1,1,0,1,0,0,0,0,0,0,0,1,0,1,-1,-1,-1},
            {1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1},
            {1,2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2,1},
            {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1},
            {1,2,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,2,1},
            {1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    int[][] Map2 = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,2,0,0,1,0,0,2,0,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
            {1,0,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,0,1},
            {1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,0,1,-1,1,-1,1,0,1,0,1,1,0,1},
            {1,0,0,0,0,1,0,1,-1,-1,-1,1,0,1,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
            {1,0,2,1,0,1,0,0,0,0,0,0,0,1,0,1,2,0,1},
            {1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
            {1,1,0,1,2,1,0,1,1,1,1,1,0,1,2,1,0,1,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    int[][] map = new int[21][19];

    public Controller() throws FileNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapCopy();
        anchorPane.getChildren().add(pacman.image);
        for (int i = 1; i < 5; i++) {
            anchorPane.getChildren().add(imageViews[i]);
        }

        makeMapView();
        for (int i = 0; i < 4; i++) {
            ghosts[i].image.toFront();
        }
        scoreLabel.toFront();
        ScoreText.toFront();
        ghostMode.toFront();
        endGame.toFront();

        this.startTimer();

    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        pacmanGetCoins();
                        update();
                        updateMapView();

                        isGhosted();
                        increaseSpeed();
                        freezeGhost();

                        if (gameOver() && !isGhosted){
                            endGame.setText("GAME OVER");
                            timer.cancel();
                        }
                        if (youWon()){
                            timer.cancel();
                        }



                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(10.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    public void update(){



        if (pacman.canMove(map)){
            pacman.move();
        }
        pacman.image.setLayoutX(pacman.x-12.5);
        pacman.image.setLayoutY(pacman.y-12.5);
        pacman.image.setFitWidth(25);
        pacman.image.setFitHeight(25);

        for (int i = 0; i < ghosts.length; i++) {
            if (ghosts[i].canMove(map)){
                if (ghosts[i].isInCenter() && ghosts[i].isInTaghatoo(map,pacman)){
                    ghosts[i].timer=System.currentTimeMillis();
                }
                ghosts[i].move();
            }
            else ghosts[i].reDrirect(map,pacman);
        }


        /*for (int j = 0; j < 21; j++) {
            for (int i = 0; i < 19; i++) {

                if (i==pacman.i()&&j==pacman.j()){
                    map[j][i]=9;
                }
                else if (map[j][i]==9) map[j][i]=0;


            }
        }*/

    }




    public void makeMapView(){
        for (int j = 0; j < 21; j++) {
            for (int i = 0; i < 19; i++) {
                Rectangle rectangle = new Rectangle(25,25,Color.ROYALBLUE);
                rectangle.setX(i*25);
                rectangle.setY(j*25);
                Circle coin = new Circle(i*25+12.5,j*25+12.5,3);
                coin.setFill(Color.WHITE);
                if (map[j][i]==1){
                    anchorPane.getChildren().add(rectangle);
                }
                if (map[j][i]==0){
                    coins[j][i]=coin;
                    anchorPane.getChildren().add(coins[j][i]);
                }
                if (map[j][i]==2){
                    coin.setRadius(6);
                    coins[j][i]=coin;
                    anchorPane.getChildren().add(coins[j][i]);
                }
            }
        }

    }

    public void updateMapView(){

        if (isGhosted) ghostMode.setText("Ghost mode");
        else ghostMode.setText("");


        Random random = new Random();
        int j = random.nextInt(21);
        int i = random.nextInt(19);
        if (map[j][i]==-1){
            if (random.nextInt(1500)==1){
                map[j][i]=3;
                ImageView cherry = new ImageView(this.cherry);
                cherry.setLayoutX(i*25);
                cherry.setLayoutY(j*25);
                cherry.setFitHeight(25);
                cherry.setFitWidth(25);
                boosters[j][i]=cherry;
                anchorPane.getChildren().add(cherry);
            }
        }
    }

    public void pacmanGetCoins(){
        if (map[pacman.j()][pacman.i()]==0){
            coins[pacman.j()][pacman.i()].setRadius(0);
            map[pacman.j()][pacman.i()]=-1;
            score+=100;
            scoreLabel.setText(String.valueOf(score));

        }
        else if (map[pacman.j()][pacman.i()]==2){
            coins[pacman.j()][pacman.i()].setRadius(0);
            map[pacman.j()][pacman.i()]=-1;
            score+=300;
            scoreLabel.setText(String.valueOf(score));
        }
        else if (map[pacman.j()][pacman.i()]==3){
            ImageView imageView = boosters[pacman.j()][pacman.i()];
            imageView.setLayoutX(-25);
            map[pacman.j()][pacman.i()]=-1;
            randomBoosterActivate();
        }
    }

    public boolean gameOver(){
        for (Ghost ghost:ghosts){
            if (pacman.i()==ghost.i() && pacman.j()==ghost.j()){
                return true;
            }
        }
        return false;
    }

    public boolean youWon(){
        boolean won = true;
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 19; j++) {
                if (map[i][j]==0 || map[i][j]==2){
                    won = false;
                }
            }
        }
        if (won) endGame.setText("YOU WON !!!");
        return won;
    }

    public void isGhosted(){
        if (isGhosted && isGhostedCD>0) isGhostedCD--;
        else if (isGhosted &&isGhostedCD==0) {
            isGhosted=false;
            isGhostedCD=10000;
        }
    }

    public void increaseSpeed(){
        if (increaseSpeed && increaseSpeedCD>0) increaseSpeedCD--;
        else if (increaseSpeed &&increaseSpeedCD==0) {
            increaseSpeed=false;
            increaseSpeedCD=10000;
        }
        if (increaseSpeed) pacman.speed = 0.2;
        else pacman.speed = 0.1;
    }

    public void freezeGhost(){
        if (freezeGhosts && freezeGhostCD>0) freezeGhostCD--;
        else if (freezeGhosts &&freezeGhostCD==0) {
            freezeGhosts=false;
            freezeGhostCD=10000;
        }
        if (freezeGhosts){
            for (Ghost ghost: ghosts){
                ghost.speed = 0.05;
            }
        }
        else for (Ghost ghost: ghosts){
                ghost.speed = 0.1;
            }
    }

    public void randomBoosterActivate(){
        Random random = new Random();
        int p = random.nextInt(3)+1;
        if (p==1) {isGhosted=true;
            isGhostedCD=10000;
            System.out.println(1);}
        if (p==2) {increaseSpeed=true;
            increaseSpeedCD=10000;
            System.out.println(2);}
        if (p==3) {freezeGhosts=true;
            freezeGhostCD=10000;
            System.out.println(3);}
    }

    @Override
    public void handle(KeyEvent e) {

            if (e.getCode()==KeyCode.UP && pacman.canReDirect(Direction.Up,map)){
                pacman.reDirect(Direction.Up);
            }if (e.getCode()==KeyCode.DOWN && pacman.canReDirect(Direction.Down,map)){
                pacman.reDirect(Direction.Down);
            }if (e.getCode()==KeyCode.RIGHT && pacman.canReDirect(Direction.Right,map)){
                pacman.reDirect(Direction.Right);
            }if (e.getCode()==KeyCode.LEFT && pacman.canReDirect(Direction.Left,map)){
                pacman.reDirect(Direction.Left);
            }if (e.getCode()==KeyCode.C){
                mapN++;
            }if (e.getCode()==KeyCode.SPACE){
            pacman.x = 9*25+12.5;
            pacman.y = 15*25+12.5;
            pacman.direction=Direction.Up;
            for (Ghost ghost : ghosts){
                ghost.x=9*25+12.5;
                ghost.y=9*25+12.5;
                ghost.direction=Direction.Up;
                ghost.dontGoDown=true;
            }
            score = 0;
            scoreLabel.setText("0");
            mapCopy();
            coins = new Circle[21][19];

            Rectangle blackScreen = new Rectangle(0,0,600,600);
            blackScreen.setFill(Color.BLACK);
            anchorPane.getChildren().add(blackScreen);
            makeMapView();
            pacman.image.toFront();
            for (ImageView imageView : imageViews){
                imageView.toFront();
            }
            scoreLabel.toFront();
            ScoreText.toFront();
            ghostMode.toFront();
            endGame.toFront();
            endGame.setText("");
            this.startTimer();
        }

    }

    public void mapCopy(){
        if (mapN%2==1) {for (int i = 0; i < Map1.length; i++) {
            map[i] = Map1[i].clone();
        }}
        else for (int i = 0; i < Map1.length; i++) {
            map[i] = Map2[i].clone();
        }
    }
}
