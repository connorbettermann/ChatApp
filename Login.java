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

    //create a new user to hold current user and database connection
    DBConnect database = new DBConnect();
    User currentUser = new User();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create login screen
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

        Button newUserButton = new Button("Create Account");
        GridPane.setConstraints(newUserButton, 1, 3);

        //when user presses login button, data is sent to login method
        loginButton.setOnAction(e -> login(nameInput.getText(), passInput.getText()));

        //when user presses create account button, data is sent to createAccount method
        newUserButton.setOnAction(e -> createAccount(nameInput.getText(), passInput.getText()));

        //fill the grid with all elements
        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, newUserButton);

        //display the window
        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();


    }

    public void login(String in_name, String in_pass)
    {
        //sends query to database to check if credentials valid
        //fills currentUser object with user information if valid
        currentUser = database.loginUser(in_name, in_pass);
        if(currentUser != null)
        {
            window.close();
            try {
                //gets ip address of local machine
                String address = InetAddress.getLocalHost().getHostAddress();
                System.out.println(address);

                //creates new client object with address of current machine
                Client client = new Client(address, 5000, currentUser);
            }catch(UnknownHostException x)
            {
                System.out.println(x);
            }
        }
        else
        {
            //display alert message if credentials are invalid
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
            //display alert message if no credntials are entered for account creation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Credentials Entered");
            alert.showAndWait();
            return;
        }
        else {

            //create a new user and send update request to database
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


    //launch the login window
    public static void main(String[] args) {
        launch(args);
    }

}
