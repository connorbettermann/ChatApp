package sample;

import javafx.application.Application;

import java.awt.*;
import java.awt.event.ActionListener;
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
import java.io.ByteArrayInputStream;
import java.util.Scanner;


import javax.swing.*;

public class Client
{
    // initialize socket and input output streams
    private Socket socket		 = null;
    private DataInputStream input = null;
    private DataOutputStream out	 = null;
    private DataInputStream serverIn = null;

    //Objects to fill the Client GUI
    public JTextField cDataField = new JTextField(40);
    public JTextArea cMessageArea = new JTextArea(8, 60);
    public JButton cButton = new JButton("Send");

    // constructor to put ip address and port
    public Client(String address, int port, User in_user)
    {
        //create new user to hold current user information
        User currentUser = in_user;

        //Fill the Client GUI
        JFrame cFrame = new JFrame("Client - " + currentUser.userName);
        cFrame.getContentPane().add(cDataField, "North");
        cFrame.getContentPane().add(new JScrollPane(cMessageArea), "Center");
        cFrame.getContentPane().add(cButton, "South");
        cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cFrame.pack();
        cFrame.setVisible(true);
        cMessageArea.setEditable(false);

        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal

            input = new DataInputStream(System.in);
            serverIn = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
                //this allows the data stream to be empty until the user presses the send button
                //This may be un-needed, but I was having difficulty with the server reading and this fixed it.
                cButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        try
                        {
                            String finalLine = cDataField.getText();
                            if(finalLine.isEmpty() == false) {
                                out.writeUTF(currentUser.userName + ": " + finalLine + "\n");
                                //cMessageArea.append(currentUser.userName + ": " + finalLine + "\n");
                            }
                            out.flush();
                            cDataField.setText("");
                        }catch(IOException i)
                        {
                            System.out.println(i);
                        }
                    }
                });

                //Reads input from the server
                String serverLine = serverIn.readUTF(serverIn);
                if(serverLine.isEmpty() == false) {
                    cMessageArea.append(serverLine);
                }
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        //create a test user object for testing purposes
        User user = new User("testuser1", "password");
        //create a client object and fill with the test user object created
        Client client = new Client("127.0.0.1", 5000, user);
    }
}


