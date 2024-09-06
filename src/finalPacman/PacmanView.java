package com.example.javafxtest;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PacmanView extends Group {

    //cherry
    Image cherryPIC = new Image(new FileInputStream("C:\\Users\\ASUS\\IdeaProjects\\JavaFXtest\\cherry.png"));

    public PacmanView() throws FileNotFoundException {
    }
}
