package sample;

import java.net.*;
import java.io.*;

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

public class Server// extends Application
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
    private DataOutputStream out     = null;

    public JFrame sFrame = new JFrame("Server");
    public JTextField sDataField = new JTextField(40);
    public JTextArea sMessageArea = new JTextArea(8, 60);


    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            sFrame.getContentPane().add(sDataField, "North");
            sFrame.getContentPane().add(new JScrollPane(sMessageArea), "Center");

            sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sFrame.pack();
            sFrame.setVisible(true);
            sMessageArea.setEditable(false);

            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");
            sMessageArea.setText("Client accepted\n");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {


                    line = in.readUTF();
                    sMessageArea.append(line);
                    //System.out.println("Client says:" + line);
                    /*if(line != "\n") {
                        sMessageArea.append("Client says: " + line + "\n");
                    }*/
                    out.writeUTF(line);
                    out.flush();

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
            out.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        //need to put gui for server in this class and launch from separate main

        Server server = new Server(5000);


    }
/*
    @Override
    public void start(Stage primaryStage) throws Exception {
        sFrame.getContentPane().add(sDataField, "North");
        sFrame.getContentPane().add(new JScrollPane(sMessageArea), "Center");

        sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sFrame.pack();
        sFrame.setVisible(true);
        sMessageArea.setEditable(false);

        //Server server = new Server(5000);
    }*/
}

