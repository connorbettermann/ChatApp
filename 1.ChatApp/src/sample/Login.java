package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {
    Button button1;
    Stage window;
    Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ChatApp");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        window = primaryStage;
        window.setTitle("The ChatApp - Log In");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);
        nameInput.setPromptText("Username");

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        TextField passInput = new TextField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 1);

        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> window.setScene(scene2));

        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();



        // window = primaryStage;

        Label label1 = new Label("First Scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 400, 500);

        TextField msg = new TextField();
        msg.setPromptText("Message Here");
        GridPane.setConstraints(msg,0,9);



        Button button2 = new Button("Send");
        GridPane.setConstraints(button2, 10, 9);
        window.setTitle("TheChatApp - Client Server");
        //button2.setOnAction(e -> window.setScene(scene1));
        button2.setOnAction(e -> System.out.println("Message sent"));
        grid.getChildren().addAll(msg, button2);
        GridPane layout2 = new GridPane();
        layout2.setHgap(20);
        layout2.setVgap(20);
        layout2.setGridLinesVisible(true);
        layout2.getChildren().addAll(button2, msg);
        scene2 = new Scene(layout2, 400, 210);



        /*window.setScene(scene1);
        window.setTitle("Title here");
        window.show();*/
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/
}
