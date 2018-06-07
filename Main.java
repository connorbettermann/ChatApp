package sample;
/*
ChatApp
Written By: Connor Bettermann
CS300

This application creates an instant messenger that allows users to connect with each other and communicate.
The application is run on a local host server and utilizes a MySQL database to hold account information.
*/
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception{

        //launches the log in window
        Application.launch(Login.class, args);

    }
}
