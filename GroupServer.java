package sample;
/*
This class is not yet finished. This class would potentially replace the server class and work
with the ClientHandler class to connect any number of clients together dynamically. his would utilize
the thread class.
*/
import java.net.*;
import java.io.*;
import java.text.*;

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

public class GroupServer
{
    //initialize socket and input stream

    public JFrame sFrame = new JFrame("GroupServer");
    public JTextField sDataField = new JTextField(40);
    public JTextArea sMessageArea = new JTextArea(8, 60);


    // constructor with port
    public static void main(String[] args) throws IOException
    {

            // server is listening on port 5000
            ServerSocket ss = new ServerSocket(5000);

            // running infinite loop for getting
            // client request
            while (true) {
                Socket s = null;

                try {
                    // socket object to receive incoming client requests
                    System.out.println("Attempting to connect...");
                    s = ss.accept();

                    System.out.println("A new client is connected : " + s);

                    // obtaining input and out streams
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));

                    System.out.println("Assigning new thread for this client");

                    // create a new thread object
                    Thread t = new ClientHandler(s, dis, dos);

                    // Invoking the run() method
                    t.run();

                } catch (IOException e) {
                    s.close();
                    e.printStackTrace();
                }
            }
    }
}



