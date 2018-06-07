package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.*;

public class Login extends Application {

    Button button1;
    Stage window;
    Scene scene1, scene2;


    DBConnect database = new DBConnect();
    User currentUser = new User();

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

        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 1);

        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> login(nameInput.getText(), passInput.getText()));

        Button newUserButton = new Button("Create Account");
        GridPane.setConstraints(newUserButton, 1, 3);
        newUserButton.setOnAction(e -> createAccount(nameInput.getText(), passInput.getText()));



        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, newUserButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();


    }

    public void login(String in_name, String in_pass)
    {
        currentUser = database.loginUser(in_name, in_pass);
        if(currentUser != null)
        {
            window.close();
            try {
                String address = InetAddress.getLocalHost().getHostAddress();
                System.out.println(address);
                Client client = new Client(address, 5000, currentUser);
            }catch(UnknownHostException x)
            {
                System.out.println(x);
            }
            //Client client = new Client("127.0.0.1", 5000, currentUser);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Credentials Entered");
            alert.showAndWait();
            return;
        }

    }

    public void createAccount(String in_name, String in_pass)
    {
        if(in_name.isEmpty() == true || in_pass.isEmpty() == true)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Credentials Entered");
            alert.showAndWait();
            return;
        }
        else {
            currentUser.setUser(in_name, in_pass);
            database.addUser(in_name, in_pass);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome!");
            alert.setHeaderText("Account Created For " + in_name);
            alert.showAndWait();
            window.close();
            login(in_name, in_pass);


        }
    }



    public static void main(String[] args) {
        launch(args);
    }

}
